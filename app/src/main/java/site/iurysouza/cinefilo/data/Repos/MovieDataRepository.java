package site.iurysouza.cinefilo.data.Repos;

import android.support.annotation.UiThread;
import io.realm.Realm;
import javax.inject.Inject;
import rx.Observable;
import site.iurysouza.cinefilo.data.Repos.DataStore.CloudMovieDataStore;
import site.iurysouza.cinefilo.data.Repos.DataStore.LocalMovieDataStore;
import site.iurysouza.cinefilo.data.entities.MovieRealm;

/**
 * Created by Iury Souza on 12/10/2016.
 */

public class MovieDataRepository implements MovieRepository {

  private LocalMovieDataStore localDataStore;
  private CloudMovieDataStore cloudDataStore;
  private Realm realm;

  @Inject
  @UiThread
  public MovieDataRepository(LocalMovieDataStore localDataStore,
      CloudMovieDataStore cloudDataStore, Realm realm) {
    this.localDataStore = localDataStore;
    this.cloudDataStore = cloudDataStore;
    this.realm = realm;
  }

  @UiThread
  @Override public Observable<MovieRealm> getMovieById(int movieId) {
    if (!isMovieDataValid(movieId)) {
      cloudDataStore.movieById(movieId);
    }

    return localDataStore.movieById(movieId);
  }

  @UiThread
  private boolean isMovieDataValid(int movieId) {
    return realm
        .where(MovieRealm.class)
        .equalTo(MovieRealm.ID, movieId)
        .count() > 0;
  }

  @Override public void close() {
    realm.close();
  }
}
