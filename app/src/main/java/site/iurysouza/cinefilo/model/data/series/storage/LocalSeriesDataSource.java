package site.iurysouza.cinefilo.model.data.series.storage;

import io.realm.Realm;
import io.realm.Sort;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import org.joda.time.DateTime;
import site.iurysouza.cinefilo.model.entities.realm.RealmCurrentPage;
import site.iurysouza.cinefilo.model.entities.realm.RealmSeries;
import site.iurysouza.cinefilo.model.entities.realm.RealmSeriesResults;
import site.iurysouza.cinefilo.util.Constants;

import static site.iurysouza.cinefilo.model.entities.realm.RealmCurrentPage.MEDIA_SERIES;
import static site.iurysouza.cinefilo.model.entities.realm.RealmSeries.POPULARITY;
import static site.iurysouza.cinefilo.model.entities.realm.RealmSeries.QUERY_TYPE;
import static site.iurysouza.cinefilo.model.entities.realm.RealmSeries.TOP_QUERY;
import static site.iurysouza.cinefilo.model.entities.realm.RealmSeries.VOTE_AVG;
import static site.iurysouza.cinefilo.model.entities.realm.RealmSeries.VOTE_COUNT;

/**
 * Created by Iury Souza on 12/10/2016.
 */

public class LocalSeriesDataSource {


  @Inject
  public LocalSeriesDataSource() {
  }


  public void storeSeriesAndCurrentPageInRealm(RealmSeriesResults realmSeriesResults, int listType) {
    if (realmSeriesResults.getSeriesList().isEmpty()) return;
    Realm realm = Realm.getDefaultInstance();
    realm.executeTransaction(transaction ->
        transaction.copyToRealmOrUpdate(realmSeriesResults.getSeriesList())
    );
    realm.close();
    storeCurrentPage(realmSeriesResults.getPage(), listType);
  }

  private void storeCurrentPage(int page, int listType) {
    Realm realm = Realm.getDefaultInstance();
    RealmCurrentPage currentPage = new RealmCurrentPage();
    currentPage.setId(listType+MEDIA_SERIES);
    currentPage.setCurrentPage(page);
    realm.executeTransaction(transaction -> {
      transaction.copyToRealmOrUpdate(currentPage);
    });
    realm.close();
  }

  public int getCurrentPageFor(int listType) {
    Realm realm = Realm.getDefaultInstance();

    RealmCurrentPage currentPage = realm.copyFromRealm(
        realm.where(RealmCurrentPage.class)
            .equalTo(RealmCurrentPage.ID, listType + MEDIA_SERIES)
            .findFirst());

    realm.close();

    return currentPage.getCurrentPage();
  }

  public List<RealmSeries> getTopRatedSeries() {
    Realm realm = Realm.getDefaultInstance();
    List<RealmSeries> seriesList = realm.copyFromRealm(
        realm.where(RealmSeries.class)
            .equalTo(QUERY_TYPE, TOP_QUERY)
            .greaterThan(VOTE_COUNT, Constants.Media.MIN_VOTE_COUNT)
            .findAllSorted(VOTE_AVG, Sort.DESCENDING));

    realm.close();
    return seriesList;
  }

  public List<RealmSeries> getMostPopularSeries() {
    Realm realm = Realm.getDefaultInstance();
    List<RealmSeries> seriesList = realm.copyFromRealm(
        realm.where(RealmSeries.class)
            .equalTo(QUERY_TYPE, TOP_QUERY)
            .greaterThan(VOTE_COUNT, Constants.Media.MIN_VOTE_COUNT)
            .findAllSorted(POPULARITY, Sort.DESCENDING));
    realm.close();
    return seriesList;
  }

  public List<RealmSeries> getNowPlayingSeries() {
    Realm realm = Realm.getDefaultInstance();
    List<RealmSeries> seriesList = realm.copyFromRealm(
        realm.where(RealmSeries.class)
            .greaterThan(QUERY_TYPE, TOP_QUERY)
            .greaterThan(VOTE_AVG, Constants.Media.MIN_VOTE_AVG)
            .findAll());

    realm.close();
    return seriesList;
  }


  private Date getValidDate() {
    return new DateTime().minusMonths(6).toDate();
  }
}
