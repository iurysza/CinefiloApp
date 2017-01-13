package site.iurysouza.cinefilo.model.data.movies;

import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import org.joda.time.DateTime;
import rx.Observable;
import rx.subjects.BehaviorSubject;
import site.iurysouza.cinefilo.domain.WatchMediaRepository;
import site.iurysouza.cinefilo.model.data.entity.WatchMedia;
import site.iurysouza.cinefilo.model.data.movies.storage.CloudMovieDataSource;
import site.iurysouza.cinefilo.model.data.movies.storage.LocalMovieDataSource;
import site.iurysouza.cinefilo.model.entities.realm.RealmGenre;
import site.iurysouza.cinefilo.util.Constants;
import timber.log.Timber;

import static site.iurysouza.cinefilo.model.data.entity.WatchMedia.valueOfRealmMovie;
import static site.iurysouza.cinefilo.model.entities.realm.RealmCurrentPage.MOST_POPULAR_LIST;
import static site.iurysouza.cinefilo.model.entities.realm.RealmCurrentPage.NOW_PLAYING_LIST;
import static site.iurysouza.cinefilo.model.entities.realm.RealmCurrentPage.TOP_RATED_LIST;
import static site.iurysouza.cinefilo.presentation.movies.MovieListFragment.INVALID_PAGE;

/**
 * Created by Iury Souza on 12/10/2016.
 */

@UiThread
public class MoviesRepository implements WatchMediaRepository {

  private final LocalMovieDataSource localDataStore;
  private final CloudMovieDataSource cloudDataStore;
  private final InputStream genresFromJson;

  private BehaviorSubject<List<WatchMedia>> nowPlayingSubject = BehaviorSubject.create();
  private BehaviorSubject<List<WatchMedia>> topRatedSubject = BehaviorSubject.create();
  private BehaviorSubject<List<WatchMedia>> mostPopularSubject = BehaviorSubject.create();

  @Inject
  public MoviesRepository(LocalMovieDataSource localDataStore,
      CloudMovieDataSource cloudDataStore, InputStream genresFromJson) {
    this.localDataStore = localDataStore;
    this.cloudDataStore = cloudDataStore;
    this.genresFromJson = genresFromJson;
  }

  @Override
  public void getMostPopular(int page, boolean forceRemote) {
    boolean firstPageWasLoadedFromLocalStorage = page == INVALID_PAGE && forceRemote;

    if (firstPageWasLoadedFromLocalStorage) {
      int nextPageSize = getNextPageFor(MOST_POPULAR_LIST);
      getMostPopular(nextPageSize, true);
      return;
    }

    queryLocalAndRemoteData(getMostPopularFromRealm(forceRemote),
        getMostPopularFromApi(page, MOST_POPULAR_LIST), mostPopularSubject);
  }

  @Override
  public void getTopRated(int page, boolean forceRemote) {
    boolean firstPageWasLoadedFromLocalStorage = page == INVALID_PAGE && forceRemote;

    if (firstPageWasLoadedFromLocalStorage) {
      int nextPageSize = getNextPageFor(TOP_RATED_LIST);
      getTopRated(nextPageSize, true);
      return;
    }

    queryLocalAndRemoteData(getTopRatedFromRealm(forceRemote),
        getTopRatedFromApi(page, TOP_RATED_LIST),
        topRatedSubject);
  }

  @Override
  public void getNowPlaying(int page, boolean forceRemote) {
    boolean firstPageWasLoadedFromLocalStorage = page == INVALID_PAGE && forceRemote;

    if (firstPageWasLoadedFromLocalStorage) {
      int nextPageSize = getNextPageFor(NOW_PLAYING_LIST);
      getNowPlaying(nextPageSize, true);
      return;
    }

    queryLocalAndRemoteData(getNowPlayingFromRealm(forceRemote),
        getNowPlayingFromApi(page, NOW_PLAYING_LIST),
        nowPlayingSubject);
  }

  private int getNextPageFor(int listId) {
    int lastPageLoaded = localDataStore.getCurrentPageFor(listId);
    return ++lastPageLoaded;
  }

  private void queryLocalAndRemoteData(Observable<List<WatchMedia>> localObservable,
      Observable<List<WatchMedia>> cloudObservable,
      BehaviorSubject<List<WatchMedia>> mediaPublisher) {

    Observable.concat(localObservable, cloudObservable)
        .takeFirst(watchMedias -> !watchMedias.isEmpty())
        .subscribe(
            mediaPublisher::onNext
            , throwable -> {
              Timber.e("Failed to load nowplaying data. Reason: %s", throwable.getMessage());
              mediaPublisher.onNext(Collections.emptyList());
            });
  }

  @NonNull
  private Observable<List<WatchMedia>> getNowPlayingFromApi(int page, int pageId) {
    return cloudDataStore
        .getNowPlayingMovies(page)
        .map(realmMoviesResults -> {
          List<WatchMedia> watchMedias = valueOfRealmMovie(realmMoviesResults.getMovieList());
          localDataStore.storeMoviesAndCurrentPageInRealm(realmMoviesResults, pageId);
          return watchMedias;
        });
  }

  @NonNull
  private Observable<List<WatchMedia>> getTopRatedFromApi(int page, int pageId) {
    return cloudDataStore
        .getTopRatedMovies(page)
        .map(realmMoviesResults -> {
          List<WatchMedia> watchMedias = valueOfRealmMovie(realmMoviesResults.getMovieList());
          localDataStore.storeMoviesAndCurrentPageInRealm(realmMoviesResults, pageId);
          return watchMedias;
        });
  }

  @NonNull
  private Observable<List<WatchMedia>> getMostPopularFromApi(int page, int pageId) {
    return cloudDataStore
        .getMostPopularMovies(page)
        .map(realmMoviesResults -> {
          List<WatchMedia> watchMedias = valueOfRealmMovie(realmMoviesResults.getMovieList());
          localDataStore.storeMoviesAndCurrentPageInRealm(realmMoviesResults, pageId);
          return watchMedias;
        });
  }

  @NonNull
  private Observable<List<WatchMedia>> getNowPlayingFromRealm(boolean forceRemote) {
    return forceRemote ? Observable.just(Collections.emptyList())
        : Observable
            .fromCallable(localDataStore::getNowPlayingMovies)
            .map(WatchMedia::valueOfRealmMovie)
            .onErrorResumeNext(e -> {
              Timber.e("Failed to get NowPlaying movies from realm :", e.getMessage());
              return Observable.just(Collections.emptyList());
            })
            .doOnNext(watchMedias -> Timber.i("Loaded NowPlaying movies from realm: %s",
                watchMedias.size()));
  }

  @NonNull
  private Observable<List<WatchMedia>> getTopRatedFromRealm(boolean forceRemote) {
    return forceRemote ? Observable.just(Collections.emptyList())
        : Observable
            .fromCallable(localDataStore::getTopRatedMovies)
            .map(WatchMedia::valueOfRealmMovie)
            .onErrorResumeNext(e -> {
              Timber.e("Failed to get topRated movies from realm :", e.getMessage());
              return Observable.just(Collections.emptyList());
            })
            .doOnNext(watchMedias -> Timber.i("Loaded topRated movies from realm: %s",
                watchMedias.size()));
  }

  @NonNull
  private Observable<List<WatchMedia>> getMostPopularFromRealm(boolean forceRemote) {
    return forceRemote ? Observable.just(Collections.emptyList())
        : Observable
            .fromCallable(localDataStore::getMostPopularMovies)
            .map(WatchMedia::valueOfRealmMovie)
            .onErrorResumeNext(e -> {
              Timber.e("Failed to get MostPopular movies from realm :", e.getMessage());
              return Observable.just(Collections.emptyList());
            })
            .doOnNext(watchMedias -> Timber.i("Loaded MostPopular movies from realm: %s",
                watchMedias.size()));
  }

  @Override
  public void getGenreList() {
    //RealmGenre first = realm
    //    .where(RealmGenre.class)
    //    .isNotNull(RealmGenre.QUERY_DATE)
    //    .findFirstAsync();
    //
    //RealmObject.asObservable(first)
    //    .filter(RealmObject::isLoaded)
    //    .subscribe(realmGenre -> {
    //      try {
    //        //if (!RealmObject.isValid(realmGenre)) {
    //          insertGenresToRealm(readJsonStream(genresFromJson));
    //        //} else {
    //        //  if (isDataStaled(realmGenre.getQueryDate())) {
    //            //cloudDataStore.getUpdateGenreList();
    //          //}
    //        //}
    //      } catch (IOException e) {
    //        e.printStackTrace();
    //      }
    //    }, Throwable::printStackTrace);
  }

  private List<RealmGenre> readJsonStream(InputStream in) throws IOException {
    JsonReader reader = new JsonReader(new InputStreamReader(in, Constants.JSON_CHAR_SET));
    List<RealmGenre> genreList = new ArrayList<>();
    reader.beginObject();
    reader.nextName();
    reader.beginArray();
    while (reader.hasNext()) {
      RealmGenre genre = new Gson().fromJson(reader, RealmGenre.class);
      genre.setQueryDate(System.currentTimeMillis());
      genreList.add(genre);
    }
    reader.endArray();
    reader.close();
    return genreList;
  }

  private boolean isDataStaled(Long timeStamp) {
    if (timeStamp == null) {
      return false;
    }
    new DateTime().minusMonths(6).toDate();
    Calendar timeStampCalendar = Calendar.getInstance();
    timeStampCalendar.setTimeInMillis(timeStamp);
    int stampMonth = timeStampCalendar.get(Calendar.MONTH);

    Calendar currentCalendar = Calendar.getInstance();
    currentCalendar.setTimeInMillis(System.currentTimeMillis());
    int currentMonth = currentCalendar.get(Calendar.MONTH);

    //return (Math.abs(stampMonth - currentMonth) > MONTHS_STALED);
    return false;
  }

  private void insertGenresToRealm(List<RealmGenre> genreList) {
    //realm.executeTransactionAsync(realm -> {
    //      realm.insertOrUpdate(genreList);
    //    },
    //    throwable -> {
    //      Timber.i(throwable, "Could not save data");
    //    });
  }

  public Observable<List<WatchMedia>> getTopRatedSubject() {
    return topRatedSubject.asObservable();
  }

  public Observable<List<WatchMedia>> getMostPopularSubject() {
    return mostPopularSubject.asObservable();
  }

  public Observable<List<WatchMedia>> getNowPlayingSubject() {
    return nowPlayingSubject.asObservable();
  }
}
