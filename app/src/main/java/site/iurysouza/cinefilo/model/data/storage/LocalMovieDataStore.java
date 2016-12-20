package site.iurysouza.cinefilo.model.data.storage;

import android.support.annotation.UiThread;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.Sort;
import javax.inject.Inject;
import rx.Observable;
import site.iurysouza.cinefilo.model.entities.realm.RealmMovie;
import site.iurysouza.cinefilo.model.entities.realm.RealmPopularMovies;
import site.iurysouza.cinefilo.model.entities.realm.RealmTopMovies;

/**
 * Created by Iury Souza on 12/10/2016.
 */

public class LocalMovieDataStore {

  private Realm realm;

  @Inject
  public LocalMovieDataStore(Realm realm) {
    this.realm = realm;
  }

  @UiThread
  public Observable<RealmMovie> movieById(int movieId) {

    RealmMovie realmMovie = realm
        .where(RealmMovie.class)
        .equalTo(RealmMovie.ID, movieId)
        .findFirstAsync();

    return RealmObject
        .asObservable(realmMovie)
        .filter(RealmObject::isLoaded)
        .filter(RealmObject::isValid);
  }

  public Observable<RealmPopularMovies> getMostPopularMovies() {

    RealmPopularMovies movieRealm = realm
        .where(RealmPopularMovies.class)
        .findFirstAsync();

     return RealmObject
        .asObservable(movieRealm)
        .filter(RealmObject::isLoaded)
        .filter(RealmObject::isValid);
  }

  public Observable<RealmTopMovies> getTopRatedMovies() {

    RealmTopMovies movieRealm = realm
        .where(RealmTopMovies.class)
        .findFirstAsync();

    return RealmObject
        .asObservable(movieRealm)
        .filter(RealmObject::isLoaded)
        .filter(RealmObject::isValid);
  }

  public Observable<RealmResults<RealmMovie>> getPopularMoviesNew() {

    RealmResults<RealmMovie> movieRealm = realm
        .where(RealmMovie.class)
        .findAllSorted(RealmMovie.POPULARITY, Sort.ASCENDING);

    return movieRealm.asObservable()
        .filter(RealmResults::isLoaded)
        .filter(RealmResults::isValid);
  }
}
