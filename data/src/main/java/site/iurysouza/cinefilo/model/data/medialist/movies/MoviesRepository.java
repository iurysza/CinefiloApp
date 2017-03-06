package site.iurysouza.cinefilo.model.data.medialist.movies;

import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import lombok.NonNull;
import rx.Observable;
import rx.schedulers.Schedulers;
import site.iurysouza.cinefilo.domain.medialist.MediaFilter;
import site.iurysouza.cinefilo.domain.medialist.WatchMedia;
import site.iurysouza.cinefilo.domain.medialist.WatchMediaRepository;
import site.iurysouza.cinefilo.model.data.medialist.movies.storage.ICloudMovieDataSource;
import site.iurysouza.cinefilo.model.data.medialist.movies.storage.ILocalMovieDataSource;
import site.iurysouza.cinefilo.model.entities.pojo.Movie;
import site.iurysouza.cinefilo.model.entities.pojo.MovieResults;
import site.iurysouza.cinefilo.model.entities.realm.RealmMovie;

import static site.iurysouza.cinefilo.model.entities.realm.RealmCurrentPage.MOST_POPULAR_LIST;
import static site.iurysouza.cinefilo.model.entities.realm.RealmCurrentPage.NOW_PLAYING_LIST;
import static site.iurysouza.cinefilo.model.entities.realm.RealmCurrentPage.TOP_RATED_LIST;
import static site.iurysouza.cinefilo.model.entities.realm.RealmMovie.valueOf;

/**
 * Created by Iury Souza on 12/10/2016.
 */

public class MoviesRepository implements WatchMediaRepository {

  private static final int INVALID_PAGE = -1;

  private final ILocalMovieDataSource localDataStore;
  private final ICloudMovieDataSource cloudDataStore;

  @Inject
  public MoviesRepository(ILocalMovieDataSource localDataStore,
      ICloudMovieDataSource cloudDataStore) {
    this.localDataStore = localDataStore;
    this.cloudDataStore = cloudDataStore;
  }

  @Override
  public Observable<List<WatchMedia>> getMostPopular(int page, boolean forceRemote, String apiKey) {
    boolean firstPageWasLoadedFromLocalStorage = page == INVALID_PAGE && forceRemote;

    if (firstPageWasLoadedFromLocalStorage) {
      int nextPageSize = getNextPageFor(MOST_POPULAR_LIST);
      return getMostPopular(nextPageSize, true, apiKey);
    }

    return queryLocalAndRemoteData(getMostPopularFromRealm(forceRemote),
        getMostPopularFromApi(page, MOST_POPULAR_LIST, apiKey));
  }

  @Override
  public Observable<List<WatchMedia>> getByGenre(int genreId) {
    return cloudDataStore.getByGenre(genreId)
        .subscribeOn(Schedulers.io())
        .map(realmMoviesResults -> valueOf(realmMoviesResults.getMovieList()));
  }

  @Override
  public Observable<List<WatchMedia>> getTopRated(int page, boolean forceRemote, String apiKey) {
    boolean firstPageWasLoadedFromLocalStorage = page == INVALID_PAGE && forceRemote;

    if (firstPageWasLoadedFromLocalStorage) {
      int nextPageSize = getNextPageFor(TOP_RATED_LIST);
      return getTopRated(nextPageSize, true, apiKey);
    }

    return queryLocalAndRemoteData(getTopRatedFromRealm(forceRemote),
        getTopRatedFromApi(page, TOP_RATED_LIST, apiKey));
  }

  @Override
  public Observable<List<WatchMedia>> getNowPlaying(int page, boolean forceRemote, String apiKey) {
    boolean firstPageWasLoadedFromLocalStorage = page == INVALID_PAGE && forceRemote;

    if (firstPageWasLoadedFromLocalStorage) {
      int nextPageSize = getNextPageFor(NOW_PLAYING_LIST);
      return getNowPlaying(nextPageSize, true, apiKey);
    }

    return queryLocalAndRemoteData(getNowPlayingFromRealm(forceRemote),
        getNowPlayingFromApi(page, NOW_PLAYING_LIST, apiKey));
  }

  private int getNextPageFor(int listId) {
    int lastPageLoaded = localDataStore.getCurrentPageFor(listId);
    return ++lastPageLoaded;
  }

  private Observable<List<WatchMedia>> queryLocalAndRemoteData(
      Observable<List<WatchMedia>> localObservable,
      Observable<List<WatchMedia>> cloudObservable) {

    return Observable.concat(localObservable, cloudObservable)
        .takeFirst(watchMedias -> !watchMedias.isEmpty());
  }

  @NonNull
  private Observable<List<WatchMedia>> getNowPlayingFromApi(int page, int pageId, String apiKey) {
    return cloudDataStore
        .getNowPlayingMovies(page, apiKey)
        .map(realmMoviesResults -> {
          List<WatchMedia> watchMedias = valueOf(realmMoviesResults.getMovieList());
          localDataStore.storeMoviesAndCurrentPageInRealm(realmMoviesResults, pageId);
          return watchMedias;
        });
  }

  @Override
  public Observable<List<WatchMedia>> getFilteredBy(int page, MediaFilter mediaFilter,
      String apiKey) {
    return cloudDataStore
        .getFilteredMovies(page, mediaFilter,apiKey)
        .map(MovieResults::getMovieList)
        .map(Movie::valueOfMovieList)
        .onErrorResumeNext(e -> {
          return Observable.just(Collections.emptyList());
        });
  }

  @NonNull
  private Observable<List<WatchMedia>> getTopRatedFromApi(int page, int pageId, String apiKey) {
    return cloudDataStore
        .getTopRatedMovies(page, apiKey)
        .map(realmMoviesResults -> {
          List<WatchMedia> watchMedias = valueOf(realmMoviesResults.getMovieList());
          localDataStore.storeMoviesAndCurrentPageInRealm(realmMoviesResults, pageId);
          return watchMedias;
        });
  }

  @NonNull
  private Observable<List<WatchMedia>> getMostPopularFromApi(int page, int pageId, String apiKey) {
    return cloudDataStore
        .getMostPopularMovies(page, apiKey)
        .map(realmMoviesResults -> {
          List<WatchMedia> watchMedias = valueOf(realmMoviesResults.getMovieList());
          localDataStore.storeMoviesAndCurrentPageInRealm(realmMoviesResults, pageId);
          return watchMedias;
        });
  }

  @NonNull
  private Observable<List<WatchMedia>> getNowPlayingFromRealm(boolean forceRemote) {
    return forceRemote ? Observable.just(Collections.emptyList())
        : Observable
            .fromCallable(localDataStore::getNowPlayingMovies)
            .map(RealmMovie::valueOf)
            .onErrorResumeNext(e -> Observable.just(Collections.emptyList()));
  }

  @NonNull
  private Observable<List<WatchMedia>> getTopRatedFromRealm(boolean forceRemote) {
    return forceRemote ? Observable.just(Collections.emptyList())
        : Observable
            .fromCallable(localDataStore::getTopRatedMovies)
            .map(RealmMovie::valueOf)
            .onErrorResumeNext(e -> Observable.just(Collections.emptyList()));
  }

  @NonNull
  private Observable<List<WatchMedia>> getMostPopularFromRealm(boolean forceRemote) {
    return forceRemote ? Observable.just(Collections.emptyList())
        : Observable
            .fromCallable(localDataStore::getMostPopularMovies)
            .map(RealmMovie::valueOf)
            .onErrorResumeNext(e -> Observable.just(Collections.emptyList()));
  }
}
