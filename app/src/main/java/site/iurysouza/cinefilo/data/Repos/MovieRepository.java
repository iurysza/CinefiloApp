package site.iurysouza.cinefilo.data.Repos;

import java.io.Closeable;
import rx.Observable;
import site.iurysouza.cinefilo.data.entities.realm.RealmMovie;
import site.iurysouza.cinefilo.data.entities.realm.RealmPopularMovies;

/**
 * Created by Iury Souza on 12/10/2016.
 */

public interface MovieRepository extends Closeable {
  Observable<RealmMovie> getMovieById(int id);

  Observable<RealmPopularMovies> getMoviesByPopulariy(int page);
}
