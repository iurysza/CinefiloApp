package site.iurysouza.cinefilo.model.data;

import android.support.annotation.UiThread;
import io.realm.Realm;
import io.realm.RealmResults;
import javax.inject.Inject;
import rx.Observable;
import site.iurysouza.cinefilo.model.data.storage.CloudMovieDataStore;
import site.iurysouza.cinefilo.model.data.storage.LocalMovieDataStore;
import site.iurysouza.cinefilo.model.entities.realm.RealmMovie;
import site.iurysouza.cinefilo.model.entities.realm.RealmPopularMovies;
import site.iurysouza.cinefilo.model.entities.realm.RealmTopMovies;

/**
 * Created by Iury Souza on 12/10/2016.
 */

@UiThread
public class MovieDataRepository implements MovieRepository {

  private LocalMovieDataStore localDataStore;
  private CloudMovieDataStore cloudDataStore;
  private Realm realm;

  @Inject
  public MovieDataRepository(LocalMovieDataStore localDataStore,
      CloudMovieDataStore cloudDataStore, Realm realm) {
    this.localDataStore = localDataStore;
    this.cloudDataStore = cloudDataStore;
    this.realm = realm;
  }

  @Override public Observable<RealmMovie> getMovieById(int movieId) {
    if (!isMovieDataValid(movieId)) {
      cloudDataStore.movieById(movieId);
    }
    return localDataStore.movieById(movieId);
  }

  @Override public Observable<RealmPopularMovies> getMoviesByPopulariy(int page) {
    if (!isPopularListAvailable(page)) {
      cloudDataStore.getMostPopularMovies(page);
    }
    return localDataStore.getMostPopularMovies();
  }

  @Override public Observable<RealmTopMovies> getTopRatedMovies(int page) {
    if (!isTopRatedListAvailable(page)) {
      cloudDataStore.getTopRatedMovies(page);
    }
    return localDataStore.getTopRatedMovies();
  }

  public Observable<RealmResults<RealmMovie>> getPopMoviesNew(int page) {
      cloudDataStore.getMostPopularMovies(page);
    return localDataStore.getPopularMoviesNew();
  }


  private boolean isMovieDataValid(int movieId) {
    return realm
        .where(RealmMovie.class)
        .equalTo(RealmMovie.ID, movieId)
        .count() > 0;
  }

  private boolean isPopularListAvailable(int page) {
    return realm
        .where(RealmPopularMovies.class)
        .equalTo(RealmPopularMovies.PAGE, page)
        .count() > 0;
  }

  private boolean isTopRatedListAvailable(int page) {
    return realm
        .where(RealmTopMovies.class)
        .equalTo(RealmTopMovies.PAGE, page)
        .count() > 0;
  }

  @Override public void close() {
    realm.close();
  }
}
