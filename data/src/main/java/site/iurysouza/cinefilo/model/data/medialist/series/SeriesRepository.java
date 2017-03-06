package site.iurysouza.cinefilo.model.data.medialist.series;

import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import lombok.NonNull;
import rx.Observable;
import site.iurysouza.cinefilo.domain.medialist.MediaFilter;
import site.iurysouza.cinefilo.domain.medialist.WatchMediaRepository;
import site.iurysouza.cinefilo.domain.medialist.WatchMedia;
import site.iurysouza.cinefilo.model.data.medialist.series.storage.ICloudSeriesDataSource;
import site.iurysouza.cinefilo.model.data.medialist.series.storage.ILocalSeriesDataSource;
import site.iurysouza.cinefilo.model.entities.realm.RealmSeries;

import static site.iurysouza.cinefilo.model.entities.realm.RealmCurrentPage.MOST_POPULAR_LIST;
import static site.iurysouza.cinefilo.model.entities.realm.RealmCurrentPage.NOW_PLAYING_LIST;
import static site.iurysouza.cinefilo.model.entities.realm.RealmCurrentPage.TOP_RATED_LIST;
import static site.iurysouza.cinefilo.model.entities.realm.RealmSeries.valueOf;

/**
 * Created by Iury Souza on 12/10/2016.
 */

public class SeriesRepository implements WatchMediaRepository {

  private static final double INVALID_PAGE = -1;
  private final ILocalSeriesDataSource localDataStore;
  private final ICloudSeriesDataSource cloudDataStore;

  @Inject
  public SeriesRepository(ILocalSeriesDataSource localDataStore,
      ICloudSeriesDataSource cloudDataStore) {
    this.localDataStore = localDataStore;
    this.cloudDataStore = cloudDataStore;
  }

  @Override public Observable<List<WatchMedia>> getByGenre(int genreId) {

    return null;
  }

  @Override
  public Observable<List<WatchMedia>> getMostPopular(int page, boolean forceRemote, String apiKey) {
    boolean firstPageWasLoadedFromLocalStorage = page == INVALID_PAGE && forceRemote;

    if (firstPageWasLoadedFromLocalStorage) {
      int nextPageSize = getNextPageFor(MOST_POPULAR_LIST);
      getMostPopular(nextPageSize, true, apiKey);
    }

   return queryLocalAndRemoteData(getMostPopularFromRealm(forceRemote),
        getMostPopularFromApi(page, MOST_POPULAR_LIST));
  }


  @Override
  public Observable<List<WatchMedia>> getTopRated(int page, boolean forceRemote, String apiKey) {
    boolean firstPageWasLoadedFromLocalStorage = page == INVALID_PAGE && forceRemote;

    if (firstPageWasLoadedFromLocalStorage) {
      int nextPageSize = getNextPageFor(TOP_RATED_LIST);
      getTopRated(nextPageSize, true, apiKey);
    }

    return queryLocalAndRemoteData(getTopRatedFromRealm(forceRemote),
        getTopRatedFromApi(page, TOP_RATED_LIST));

  }

  @Override
  public Observable<List<WatchMedia>> getNowPlaying(int page, boolean forceRemote, String apiKey) {
    boolean firstPageWasLoadedFromLocalStorage = page == INVALID_PAGE && forceRemote;

    if (firstPageWasLoadedFromLocalStorage) {
      int nextPageSize = getNextPageFor(NOW_PLAYING_LIST);
      getNowPlaying(nextPageSize, true, apiKey);
    }

    return queryLocalAndRemoteData(getNowPlayingFromRealm(forceRemote),
        getNowPlayingFromApi(page, NOW_PLAYING_LIST));
  }


  @Override public Observable<List<WatchMedia>> getFilteredBy(int page, MediaFilter mediaFilter,
      String apiKey) {
    return null;
  }

  private int getNextPageFor(int listId) {
    int lastPageLoaded = localDataStore.getCurrentPageFor(listId);
    return ++lastPageLoaded;
  }

  private Observable<List<WatchMedia>> queryLocalAndRemoteData(Observable<List<WatchMedia>> localObservable,
      Observable<List<WatchMedia>> cloudObservable) {

    return Observable.concat(localObservable, cloudObservable)
        .takeFirst(watchMedias -> !watchMedias.isEmpty());
  }

  @NonNull
  private Observable<List<WatchMedia>> getNowPlayingFromApi(int page, int pageId) {
    return cloudDataStore
        .getNowPlayingSeries(page)
        .map(realmSeriesResults -> {
          List<WatchMedia> watchMedias = valueOf(realmSeriesResults.getSeriesList());
          localDataStore.storeSeriesAndCurrentPageInRealm(realmSeriesResults, pageId);
          return watchMedias;
        });
  }

  @NonNull
  private Observable<List<WatchMedia>> getTopRatedFromApi(int page, int pageId) {
    return cloudDataStore
        .getTopRatedSeries(page)
        .map(realmSeriesResults -> {
          List<WatchMedia> watchMedias = valueOf(realmSeriesResults.getSeriesList());
          localDataStore.storeSeriesAndCurrentPageInRealm(realmSeriesResults, pageId);
          return watchMedias;
        });
  }

  @NonNull
  private Observable<List<WatchMedia>> getMostPopularFromApi(int page, int pageId) {
    return cloudDataStore
        .getMostPopularSeries(page)
        .map(realmSeriesResults -> {
          List<WatchMedia> watchMedias = valueOf(realmSeriesResults.getSeriesList());
          localDataStore.storeSeriesAndCurrentPageInRealm(realmSeriesResults, pageId);
          return watchMedias;
        });
  }

  @NonNull
  private Observable<List<WatchMedia>> getNowPlayingFromRealm(boolean forceRemote) {
    return forceRemote ? Observable.just(Collections.emptyList())
        : Observable
            .fromCallable(localDataStore::getNowPlayingSeries)
            .map(RealmSeries::valueOf)
            .onErrorResumeNext(e -> {
              return Observable.just(Collections.emptyList());
            });
  }

  @NonNull
  private Observable<List<WatchMedia>> getTopRatedFromRealm(boolean forceRemote) {
    return forceRemote ? Observable.just(Collections.emptyList())
        : Observable
            .fromCallable(localDataStore::getTopRatedSeries)
            .map(RealmSeries::valueOf)
            .onErrorResumeNext(e -> {
              return Observable.just(Collections.emptyList());
            });
  }

  @NonNull
  private Observable<List<WatchMedia>> getMostPopularFromRealm(boolean forceRemote) {
    return forceRemote ? Observable.just(Collections.emptyList())
        : Observable
            .fromCallable(localDataStore::getMostPopularSeries)
            .map(RealmSeries::valueOf)
            .onErrorResumeNext(e -> {
              return Observable.just(Collections.emptyList());
            });


  }
}
