package site.iurysouza.cinefilo.domain;

import io.realm.RealmResults;
import rx.Observable;
import site.iurysouza.cinefilo.model.entities.realm.RealmMovie;
import site.iurysouza.cinefilo.model.entities.realm.RealmTopMovies;

/**
 * Created by Iury Souza on 09/11/2016.
 */

public interface UseCase {
  Observable<RealmResults<RealmMovie>> getPopMoviesObservable();

  Observable<RealmResults<RealmMovie>> getTopRatedMoviesObservable();
}
