package site.iurysouza.cinefilo.domain.moviedetail;

import java.util.List;
import rx.Observable;
import site.iurysouza.cinefilo.domain.medialist.WatchMedia;

/**
 * Created by Iury Souza on 14/02/2017.
 */
public interface IMovieDetailUseCase {
  Observable<MovieDetail> getMovieById(int movieId);

  Observable<List<WatchMedia>> geMoviesSimilarTo(int movieId);

  Observable<Credits> getMoviesCredits(int movieId);
}
