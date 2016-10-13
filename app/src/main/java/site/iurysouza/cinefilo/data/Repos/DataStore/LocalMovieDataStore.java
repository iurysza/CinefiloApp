package site.iurysouza.cinefilo.data.Repos.DataStore;

import io.realm.Realm;
import io.realm.RealmResults;
import javax.inject.Inject;
import rx.Observable;
import site.iurysouza.cinefilo.data.entities.MovieEntity;
import site.iurysouza.cinefilo.data.entities.MovieRealm;

/**
 * Created by Iury Souza on 12/10/2016.
 */

public class LocalMovieDataStore implements MovieDataStore {
  private Realm realm;

  @Inject
  public LocalMovieDataStore(Realm realm) {
    this.realm = realm;
  }

  @Override public Observable<MovieEntity> movieById(String movieId) {
    return realm
        .where(MovieRealm.class)
        .equalTo(MovieEntity.ID, movieId)
        .findAllAsync()
        .asObservable()
        .map(RealmResults::first)
        .map(MovieDataMapper::transform);
  }
}
