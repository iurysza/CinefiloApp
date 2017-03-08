package site.iurysouza.cinefilo.model.data.medialist.series.storage;

import java.util.List;
import site.iurysouza.cinefilo.model.entities.realm.RealmSeries;
import site.iurysouza.cinefilo.model.entities.realm.RealmSeriesResults;

/**
 * Created by Iury Souza on 13/02/2017.
 */
public interface ILocalSeriesDataSource {
  void storeSeriesAndCurrentPageInRealm(RealmSeriesResults realmSeriesResults,
      int listType);

  void storeCurrentPage(int page, int listType);

  int getCurrentPageFor(int listType);

  List<RealmSeries> getTopRatedSeries();

  List<RealmSeries> getMostPopularSeries();

  List<RealmSeries> getNowPlayingSeries();
}
