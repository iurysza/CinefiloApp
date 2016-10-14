package site.iurysouza.cinefilo.data.Repos.DataStore;

import android.support.annotation.UiThread;
import io.realm.Realm;
import io.realm.RealmObject;
import javax.inject.Inject;
import rx.Observable;
import site.iurysouza.cinefilo.data.entities.MovieEntity;
import site.iurysouza.cinefilo.data.entities.MovieRealm;

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
  public Observable<MovieRealm> movieById(int movieId) {

    MovieRealm movieRealm = realm
        .where(MovieRealm.class)
        .equalTo(MovieEntity.ID, movieId)
        .findFirstAsync();

    return RealmObject
        .asObservable(movieRealm)
        .filter(RealmObject::isLoaded)
        .filter(RealmObject::isValid);
  }
}
