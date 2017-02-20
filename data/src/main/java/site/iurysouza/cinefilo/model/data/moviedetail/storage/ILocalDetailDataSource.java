package site.iurysouza.cinefilo.model.data.moviedetail.storage;

import rx.Observable;
import site.iurysouza.cinefilo.model.entities.realm.RealmMovie;

/**
 * Created by Iury Souza on 08/02/2017.
 */
public interface ILocalDetailDataSource {
  Observable<RealmMovie> getMovieById(int movieId);

  void storeMovie(RealmMovie movie);
}
