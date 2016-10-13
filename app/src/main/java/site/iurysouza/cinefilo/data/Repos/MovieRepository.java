package site.iurysouza.cinefilo.data.Repos;

import rx.Observable;
import site.iurysouza.cinefilo.data.entities.MovieEntity;

/**
 * Created by Iury Souza on 12/10/2016.
 */

public interface MovieRepository {
  Observable<MovieEntity> getMovieById(String id);
}
