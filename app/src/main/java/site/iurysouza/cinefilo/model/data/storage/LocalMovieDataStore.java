package site.iurysouza.cinefilo.model.data.storage;

import android.support.annotation.UiThread;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.Sort;
import javax.inject.Inject;
import rx.Observable;
import site.iurysouza.cinefilo.model.entities.realm.RealmMovie;

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

  public Observable<RealmResults<RealmMovie>> getNowPlayingMovies() {
    RealmResults<RealmMovie> movieRealm = realm
        .where(RealmMovie.class)
        .findAllSorted(RealmMovie.RELEASE_DATE, Sort.DESCENDING);

    return movieRealm.asObservable()
        .filter(RealmResults::isLoaded)
        .filter(RealmResults::isValid);
  }

  public Observable<RealmResults<RealmMovie>> getTopRatedMovies() {

    RealmResults<RealmMovie> movieRealm = realm
        .where(RealmMovie.class)
        .findAllSorted(RealmMovie.RATING, Sort.DESCENDING);

    return movieRealm.asObservable()
        .filter(RealmResults::isLoaded)
        .filter(RealmResults::isValid);
  }

  public Observable<RealmResults<RealmMovie>> getMostPopularMovies() {

    RealmResults<RealmMovie> movieRealm = realm
        .where(RealmMovie.class)
        .findAllSorted(RealmMovie.POPULARITY, Sort.DESCENDING);

    return movieRealm.asObservable()
        .filter(RealmResults::isLoaded)
        .filter(RealmResults::isValid);
  }
}
