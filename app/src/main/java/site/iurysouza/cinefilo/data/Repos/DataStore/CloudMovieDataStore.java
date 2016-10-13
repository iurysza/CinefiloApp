package site.iurysouza.cinefilo.data.Repos.DataStore;

import io.realm.Realm;
import javax.inject.Inject;
import rx.Observable;
import rx.functions.Action1;
import site.iurysouza.cinefilo.data.entities.MovieEntity;
import site.iurysouza.cinefilo.data.entities.MovieRealm;
import site.iurysouza.cinefilo.data.services.MovieService;
import site.iurysouza.cinefilo.util.Constants;

/**
 * Created by Iury Souza on 12/10/2016.
 */

public class CloudMovieDataStore implements MovieDataStore {
  private MovieService movieService;
  private Realm realm;

  @Inject
  public CloudMovieDataStore(MovieService movieService, Realm realm) {
    this.movieService = movieService;
    this.realm = realm;
  }

  @Override public Observable<MovieEntity> movieById(String movieId) {
    return movieService
        .getMovieById(movieId, Constants.MOVIE_DB_API.API_KEY);
        //.doOnNext(saveToLocalDataStore);
  }

  private final Action1<MovieEntity> saveToLocalDataStore = m -> {
    try(Realm realmInstance = Realm.getDefaultInstance()) {
      MovieRealm movieRealm = realmInstance.createObject(MovieRealm.class);
      movieRealm.setAdult(m.getAdult());
      movieRealm.setBackdropPath(m.getBackdropPath());
      //movieRealm.setBelongsToCollection(m.getBelongsToCollection());
      movieRealm.setBudget(m.getBudget());
      //movieRealm.setGenreEntities(m.getGenreEntities());
      movieRealm.setHomepage(m.getHomepage());
      movieRealm.setId(m.getId());
      movieRealm.setImdbId(m.getImdbId());
      movieRealm.setHomepage(m.getHomepage());
      movieRealm.setOriginalLanguage(m.getOriginalLanguage());
      movieRealm.setOriginalTitle(m.getOriginalTitle());
      movieRealm.setOverview(m.getOverview());
      movieRealm.setPopularity(m.getPopularity());
      movieRealm.setPosterPath(m.getPosterPath());
      //movieRealm.setProductionCompanies(m.getProductionCompanies());
      //movieRealm.setProductionCountries(m.getProductionCountries());
      movieRealm.setReleaseDate(m.getReleaseDate());
      movieRealm.setRevenue(m.getRevenue());
      movieRealm.setRuntime(m.getRuntime());
      //movieRealm.setSpokenLangEntities(m.getSpokenLangEntities());
      movieRealm.setStatus(m.getStatus());
      movieRealm.setTagline(m.getTagline());
      movieRealm.setTitle(m.getTitle());
      movieRealm.setVideo(m.getVideo());
      movieRealm.setVoteAverage(m.getVoteAverage());
      movieRealm.setVoteCount(m.getVoteCount());
      realmInstance.executeTransaction(realm -> realm.insertOrUpdate(movieRealm));
    }
  };
}
