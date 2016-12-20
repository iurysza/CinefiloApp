package site.iurysouza.cinefilo.model.data;

import android.support.annotation.UiThread;
import io.realm.Realm;
import io.realm.RealmResults;
import javax.inject.Inject;
import rx.Observable;
import site.iurysouza.cinefilo.model.data.storage.CloudMovieDataStore;
import site.iurysouza.cinefilo.model.data.storage.LocalMovieDataStore;
import site.iurysouza.cinefilo.model.entities.realm.RealmMovie;

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
    cloudDataStore.movieById(movieId);
    return localDataStore.movieById(movieId);
  }

  @Override public Observable<RealmResults<RealmMovie>> getMoviesByPopulariy(int page) {
    cloudDataStore.getMostPopularMovies(page);
    return localDataStore.getMostPopularMovies();
  }

  @Override public Observable<RealmResults<RealmMovie>> getTopRatedMovies(int page) {
    cloudDataStore.getTopRatedMovies(page);
    return localDataStore.getTopRatedMovies();
  }

  public Observable<RealmResults<RealmMovie>> getNowPlayingMovies(int page) {
    cloudDataStore.getNowPlayingMovies(page);
    return localDataStore.getNowPlayingMovies();
  }

  @Override public void close() {
    realm.close();
  }
}
