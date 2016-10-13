package site.iurysouza.cinefilo.data.Repos;

import javax.inject.Inject;
import rx.Observable;
import site.iurysouza.cinefilo.data.Repos.DataStore.MovieDataStore;
import site.iurysouza.cinefilo.data.Repos.DataStore.MovieDataStoreFactory;
import site.iurysouza.cinefilo.data.entities.MovieEntity;

/**
 * Created by Iury Souza on 12/10/2016.
 */

public class MovieDataRepository implements MovieRepository {

  private MovieDataStoreFactory dataStoreFactory;

  @Inject
  public MovieDataRepository(MovieDataStoreFactory dataStoreFactory) {
    this.dataStoreFactory = dataStoreFactory;
  }

  @Override public Observable<MovieEntity> getMovieById(String id) {
    MovieDataStore movieDataStore = dataStoreFactory.create(id);
    return movieDataStore.movieById(id);
  }
}
