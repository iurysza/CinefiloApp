package site.iurysouza.cinefilo.model.data.moviedetail.storage;

import io.realm.Realm;
import rx.Observable;
import site.iurysouza.cinefilo.model.entities.pojo.Movie;
import site.iurysouza.cinefilo.model.entities.realm.RealmMovie;

/**
 * Created by Iury Souza on 31/01/2017.
 */
public class LocalDetailDataSource {

  public Observable<RealmMovie> getMovieById(int movieId) {
    Realm realm = Realm.getDefaultInstance();
    RealmMovie movie = realm.copyFromRealm(realm
        .where(RealmMovie.class)
        .equalTo(RealmMovie.ID, movieId)
        .findFirst());
    realm.close();
    return Observable.just(movie);
  }

  public void storeMovie(Movie movie) {

  }
}
