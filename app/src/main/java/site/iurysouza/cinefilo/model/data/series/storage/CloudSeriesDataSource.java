package site.iurysouza.cinefilo.model.data.series.storage;

import javax.inject.Inject;
import rx.Observable;
import site.iurysouza.cinefilo.model.entities.realm.RealmSeriesResults;
import site.iurysouza.cinefilo.model.services.SeriesService;
import site.iurysouza.cinefilo.util.Constants;
import timber.log.Timber;

import static site.iurysouza.cinefilo.model.entities.realm.RealmMovie.NOW_QUERY;
import static site.iurysouza.cinefilo.model.entities.realm.RealmMovie.POP_QUERY;
import static site.iurysouza.cinefilo.model.entities.realm.RealmMovie.TOP_QUERY;

/**
 * Created by Iury Souza on 12/10/2016.
 */

public class CloudSeriesDataSource implements ICloudSeriesDataSource{
  private final SeriesService seriesService;

  @Inject
  public CloudSeriesDataSource(SeriesService seriesService) {
    this.seriesService = seriesService;
  }

  public Observable<RealmSeriesResults> getNowPlayingSeries(int page) {
    return seriesService
        .getNowPlayingSeries(Constants.MOVIE_DB_API.API_KEY, page)
        .map(seriesResults -> RealmSeriesResults.valueOf(seriesResults, NOW_QUERY))
        .doOnNext(this::printLoadedSeries);
  }

  public Observable<RealmSeriesResults> getTopRatedSeries(int page) {
    return seriesService
        .getTopRatedSeries(Constants.MOVIE_DB_API.API_KEY, page)
        .map(seriesResults -> RealmSeriesResults.valueOf(seriesResults, TOP_QUERY))
        .doOnNext(this::printLoadedSeries);
  }

  public Observable<RealmSeriesResults> getMostPopularSeries(int page) {
    return seriesService
        .getMostPopularSeries(Constants.MOVIE_DB_API.API_KEY, page)
        .map(seriesResults -> RealmSeriesResults.valueOf(seriesResults, POP_QUERY))
        .doOnNext(this::printLoadedSeries);
  }

  private void printLoadedSeries(RealmSeriesResults realmSeriesResults) {
    Timber.i("Loaded %s seriess from network", realmSeriesResults.getSeriesList().size());
  }
}
