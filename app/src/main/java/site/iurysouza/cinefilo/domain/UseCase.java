package site.iurysouza.cinefilo.domain;

import rx.Observable;
import site.iurysouza.cinefilo.model.entities.realm.RealmPopularMovies;
import site.iurysouza.cinefilo.model.entities.realm.RealmTopMovies;

/**
 * Created by Iury Souza on 09/11/2016.
 */

public interface UseCase {
  Observable getPopMoviesObservable();

  Observable<RealmTopMovies> getTopRatedMoviesObservable();
}
