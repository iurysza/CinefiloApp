package site.iurysouza.cinefilo.data.Repos.DataStore;

import android.support.annotation.UiThread;
import io.realm.Realm;
import io.realm.RealmObject;
import javax.inject.Inject;
import rx.Observable;
import site.iurysouza.cinefilo.data.entities.realm.RealmMovie;
import site.iurysouza.cinefilo.data.entities.realm.RealmPopularMovies;

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
}
