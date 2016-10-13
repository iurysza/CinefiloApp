package site.iurysouza.cinefilo.data.Repos.DataStore;

import io.realm.Realm;
import javax.inject.Inject;
import site.iurysouza.cinefilo.data.entities.MovieRealm;
import site.iurysouza.cinefilo.data.services.MovieService;

/**
 * Created by Iury Souza on 12/10/2016.
 */

public class MovieDataStoreFactory {
  private MovieService movieService;
  private Realm realm;

  @Inject
    public MovieDataStoreFactory(MovieService movieService, Realm realm) {
    this.movieService = movieService;
    this.realm = realm;
  }

  public MovieDataStore create(String movieId) {
    MovieDataStore movieDataStore;

    if (isMovieSavedLocally(movieId)){
      movieDataStore = new LocalMovieDataStore(realm);
    } else {
      movieDataStore = new CloudMovieDataStore(movieService, realm);
    }

    return movieDataStore;
  }

  private boolean isMovieSavedLocally(String movieId) {
    return realm.
        where(MovieRealm.class)
        .equalTo(MovieRealm.ID, Integer.valueOf(movieId))
        .count() > 0;
  }
}
