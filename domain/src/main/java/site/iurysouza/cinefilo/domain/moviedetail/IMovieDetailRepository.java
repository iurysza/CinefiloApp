package site.iurysouza.cinefilo.domain.moviedetail;

import java.util.List;
import rx.Observable;
import site.iurysouza.cinefilo.domain.watchmedialist.WatchMedia;

/**
 * Created by Iury Souza on 14/02/2017.
 */
public interface IMovieDetailRepository {
  Observable<MovieDetail> getMovieById(int movieId);

  Observable<List<WatchMedia>> getMoviesSimilarTo(int movieId, int page);
}
