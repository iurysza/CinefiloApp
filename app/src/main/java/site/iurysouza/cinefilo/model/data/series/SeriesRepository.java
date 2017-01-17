package site.iurysouza.cinefilo.model.data.series;

import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import org.joda.time.DateTime;
import rx.Observable;
import rx.subjects.BehaviorSubject;
import site.iurysouza.cinefilo.domain.WatchMediaRepository;
import site.iurysouza.cinefilo.model.data.entity.WatchMedia;
import site.iurysouza.cinefilo.model.data.series.storage.CloudSeriesDataSource;
import site.iurysouza.cinefilo.model.data.series.storage.LocalSeriesDataSource;
import timber.log.Timber;

import static site.iurysouza.cinefilo.model.data.entity.WatchMedia.valueOfRealmSeries;
import static site.iurysouza.cinefilo.model.entities.realm.RealmCurrentPage.MOST_POPULAR_LIST;
import static site.iurysouza.cinefilo.model.entities.realm.RealmCurrentPage.NOW_PLAYING_LIST;
import static site.iurysouza.cinefilo.model.entities.realm.RealmCurrentPage.TOP_RATED_LIST;
import static site.iurysouza.cinefilo.presentation.medias.MediaListFragment.INVALID_PAGE;

/**
 * Created by Iury Souza on 12/10/2016.
 */

@UiThread
public class SeriesRepository implements WatchMediaRepository {

  private final LocalSeriesDataSource localDataStore;
  private final CloudSeriesDataSource cloudDataStore;

  private BehaviorSubject<List<WatchMedia>> nowPlayingSubject = BehaviorSubject.create();
  private BehaviorSubject<List<WatchMedia>> topRatedSubject = BehaviorSubject.create();
  private BehaviorSubject<List<WatchMedia>> mostPopularSubject = BehaviorSubject.create();

  @Inject
  public SeriesRepository(LocalSeriesDataSource localDataStore,
      CloudSeriesDataSource cloudDataStore) {
    this.localDataStore = localDataStore;
    this.cloudDataStore = cloudDataStore;
  }

  @Override public void getByGenre(int genreId) {

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
        .subscribe(mediaPublisher::onNext
            , throwable -> {
              Timber.e("Failed to load nowplaying data. Reason: %s", throwable.getMessage());
              mediaPublisher.onNext(Collections.emptyList());
            });
  }

  @NonNull
  private Observable<List<WatchMedia>> getNowPlayingFromApi(int page, int pageId) {
    return cloudDataStore
        .getNowPlayingSeries(page)
        .map(realmSeriesResults -> {
          List<WatchMedia> watchMedias = valueOfRealmSeries(realmSeriesResults.getSeriesList());
          localDataStore.storeSeriesAndCurrentPageInRealm(realmSeriesResults, pageId);
          return watchMedias;
        });
  }

  @NonNull
  private Observable<List<WatchMedia>> getTopRatedFromApi(int page, int pageId) {
    return cloudDataStore
        .getTopRatedSeries(page)
        .map(realmSeriesResults -> {
          List<WatchMedia> watchMedias = valueOfRealmSeries(realmSeriesResults.getSeriesList());
          localDataStore.storeSeriesAndCurrentPageInRealm(realmSeriesResults, pageId);
          return watchMedias;
        });
  }

  @NonNull
  private Observable<List<WatchMedia>> getMostPopularFromApi(int page, int pageId) {
    return cloudDataStore
        .getMostPopularSeries(page)
        .map(realmSeriesResults -> {
          List<WatchMedia> watchMedias = valueOfRealmSeries(realmSeriesResults.getSeriesList());
          localDataStore.storeSeriesAndCurrentPageInRealm(realmSeriesResults, pageId);
          return watchMedias;
        });
  }

  @NonNull
  private Observable<List<WatchMedia>> getNowPlayingFromRealm(boolean forceRemote) {
    return forceRemote ? Observable.just(Collections.emptyList())
        : Observable
            .fromCallable(localDataStore::getNowPlayingSeries)
            .map(WatchMedia::valueOfRealmSeries)
            .onErrorResumeNext(e -> {
              Timber.e("Failed to get NowPlaying series from realm :", e.getMessage());
              return Observable.just(Collections.emptyList());
            })
            .doOnNext(watchMedias -> Timber.i("Loaded NowPlaying series from realm: %s",
                watchMedias.size()));
  }

  @NonNull
  private Observable<List<WatchMedia>> getTopRatedFromRealm(boolean forceRemote) {
    return forceRemote ? Observable.just(Collections.emptyList())
        : Observable
            .fromCallable(localDataStore::getTopRatedSeries)
            .map(WatchMedia::valueOfRealmSeries)
            .onErrorResumeNext(e -> {
              Timber.e("Failed to get topRated series from realm :", e.getMessage());
              return Observable.just(Collections.emptyList());
            })
            .doOnNext(watchMedias -> Timber.i("Loaded topRated series from realm: %s",
                watchMedias.size()));
  }

  @NonNull
  private Observable<List<WatchMedia>> getMostPopularFromRealm(boolean forceRemote) {
    return forceRemote ? Observable.just(Collections.emptyList())
        : Observable
            .fromCallable(localDataStore::getMostPopularSeries)
            .map(WatchMedia::valueOfRealmSeries)
            .onErrorResumeNext(e -> {
              Timber.e("Failed to get MostPopular series from realm :", e.getMessage());
              return Observable.just(Collections.emptyList());
            })
            .doOnNext(watchMedias -> Timber.i("Loaded MostPopular series from realm: %s",
                watchMedias.size()));
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

}
