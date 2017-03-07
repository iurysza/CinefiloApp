package site.iurysouza.cinefilo.model.data.medialist.series.storage;

import rx.Observable;
import site.iurysouza.cinefilo.model.entities.realm.RealmSeriesResults;

/**
 * Created by Iury Souza on 13/02/2017.
 */
public interface ICloudSeriesDataSource {
  Observable<RealmSeriesResults> getNowPlayingSeries(int page);

  Observable<RealmSeriesResults> getTopRatedSeries(int page);

  Observable<RealmSeriesResults> getMostPopularSeries(int page);
}
