package site.iurysouza.cinefilo.model.data.medialist.movies.storage;

import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.Sort;
import site.iurysouza.cinefilo.model.entities.realm.RealmCurrentPage;
import site.iurysouza.cinefilo.model.entities.realm.RealmGenre;
import site.iurysouza.cinefilo.model.entities.realm.RealmMovie;
import site.iurysouza.cinefilo.model.entities.realm.RealmMoviesResults;
import site.iurysouza.cinefilo.util.Constants;

import static site.iurysouza.cinefilo.model.entities.realm.RealmCurrentPage.MEDIA_MOVIE;
import static site.iurysouza.cinefilo.model.entities.realm.RealmMovie.NOW_QUERY;
import static site.iurysouza.cinefilo.model.entities.realm.RealmMovie.POPULARITY;
import static site.iurysouza.cinefilo.model.entities.realm.RealmMovie.POP_QUERY;
import static site.iurysouza.cinefilo.model.entities.realm.RealmMovie.QUERY_TYPE;
import static site.iurysouza.cinefilo.model.entities.realm.RealmMovie.TOP_QUERY;
import static site.iurysouza.cinefilo.model.entities.realm.RealmMovie.VOTE_AVG;
import static site.iurysouza.cinefilo.model.entities.realm.RealmMovie.VOTE_COUNT;

/**
 * Created by Iury Souza on 12/10/2016.
 */

public class LocalMovieDataSource implements ILocalMovieDataSource {

  @Inject
  public LocalMovieDataSource() {
  }

  @Override
  public void storeMoviesAndCurrentPageInRealm(RealmMoviesResults realmMoviesResults,
      int listType) {
    if (realmMoviesResults.getMovieList().isEmpty()) return;
    Realm realm = Realm.getDefaultInstance();
    realm.executeTransaction(transaction ->
        transaction.copyToRealmOrUpdate(realmMoviesResults.getMovieList())
    );
    realm.close();
    storeCurrentPage(realmMoviesResults.getPage(), listType);
  }

  @Override public void storeCurrentPage(int page, int listType) {
    Realm realm = Realm.getDefaultInstance();
    RealmCurrentPage currentPage = new RealmCurrentPage();
    currentPage.setId(MEDIA_MOVIE + listType);
    currentPage.setCurrentPage(page);
    realm.executeTransaction(transaction -> {
      transaction.copyToRealmOrUpdate(currentPage);
    });
    realm.close();
  }

  @Override
  public int getCurrentPageFor(int listType) {
    Realm realm = Realm.getDefaultInstance();

    RealmCurrentPage currentPage = realm.copyFromRealm(
        realm.where(RealmCurrentPage.class)
            .equalTo(RealmCurrentPage.ID, listType + MEDIA_MOVIE)
            .findFirst());

    realm.close();

    return currentPage.getCurrentPage();
  }

  @Override
  public List<RealmMovie> getTopRatedMovies() {
    Realm realm = Realm.getDefaultInstance();
    List<RealmMovie> movieList = realm.copyFromRealm(
        realm.where(RealmMovie.class)
            .equalTo(QUERY_TYPE, TOP_QUERY)
            .greaterThan(VOTE_COUNT, Constants.Media.MIN_VOTE_COUNT)
            .findAllSorted(VOTE_AVG, Sort.DESCENDING));

    realm.close();
    return movieList;
  }

  @Override
  public List<RealmMovie> getMostPopularMovies() {
    Realm realm = Realm.getDefaultInstance();
    List<RealmMovie> movieList = realm.copyFromRealm(
        realm.where(RealmMovie.class)
            .equalTo(QUERY_TYPE, POP_QUERY)
            .greaterThan(VOTE_COUNT, Constants.Media.MIN_VOTE_COUNT)
            .findAllSorted(POPULARITY, Sort.DESCENDING));
    realm.close();
    return movieList;
  }

  @Override
  public List<RealmMovie> getNowPlayingMovies() {
    Realm realm = Realm.getDefaultInstance();
    List<RealmMovie> movieList = realm.copyFromRealm(
        realm.where(RealmMovie.class)
            .equalTo(QUERY_TYPE, NOW_QUERY)
            .greaterThan(VOTE_AVG, Constants.Media.MIN_VOTE_AVG)
            .findAll());

    realm.close();
    return movieList;
  }

  @Override public void storeGenresInRealm(List<RealmGenre> realmGenres) {
    Realm.getDefaultInstance().executeTransactionAsync(realm -> {
          try {
            realm.insertOrUpdate(realmGenres);
          } catch (RuntimeException e) {
          }
        },
        throwable -> {

        });
  }
}
