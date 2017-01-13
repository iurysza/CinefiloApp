package site.iurysouza.cinefilo.model.entities.realm;

import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.annotations.RealmClass;
import lombok.Data;
import site.iurysouza.cinefilo.model.entities.pojo.Series;
import site.iurysouza.cinefilo.model.entities.pojo.SeriesResult;

/**
 * Created by Iury Souza on 12/10/2016.
 */
@Data
@RealmClass
public class RealmSeriesResults implements RealmModel {
  public static final String PAGE = "page";
  private int page;
  private Long totalPages;
  private Long totalResults;
  private RealmList<RealmSeries> seriesList;

  public static RealmSeriesResults valueOf(SeriesResult seriesResult, int queryType) {
    RealmList<RealmSeries> realmSeriesList = new RealmList<>();
    RealmSeriesResults realmSeriesResults = new RealmSeriesResults();

    if (seriesResult.getSeriesList().isEmpty()) {
      return realmSeriesResults;
    }

    for (Series series : seriesResult.getSeriesList()) {
      RealmSeries realmSeries = RealmSeries.valueOf(series, queryType);
      if (realmSeries != null) {
        realmSeriesList.add(realmSeries);
      }
    }

    realmSeriesResults.setSeriesList(realmSeriesList);
    realmSeriesResults.setPage(seriesResult.getPage());
    return realmSeriesResults;
  }
}
