package site.iurysouza.cinefilo.model.data.moviedetail.storage;

import io.realm.Realm;
import rx.Observable;
import site.iurysouza.cinefilo.model.entities.realm.RealmMovie;

/**
 * Created by Iury Souza on 31/01/2017.
 */
public class LocalDetailDataSource {

  public Observable<RealmMovie> getMovieById(int movieId) {
    Realm realm = Realm.getDefaultInstance();
    RealmMovie movieQuery = realm
        .where(RealmMovie.class)
        .equalTo(RealmMovie.ID, movieId)
        .findFirst();
    RealmMovie unmanagedMovie = null;
    if (movieQuery != null) {
      unmanagedMovie = realm.copyFromRealm(movieQuery);
    }
    realm.close();
    return Observable.just(unmanagedMovie);
  }

  public void storeMovie(RealmMovie movie) {
    Realm realm = Realm.getDefaultInstance();
    if (movie != null) {
      realm.executeTransaction(transaction -> transaction.copyToRealmOrUpdate(movie));
    }
    realm.close();
  }
}
