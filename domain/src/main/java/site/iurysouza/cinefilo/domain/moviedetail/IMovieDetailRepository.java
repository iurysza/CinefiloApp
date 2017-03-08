package site.iurysouza.cinefilo.domain.moviedetail;

import java.util.List;
import lombok.NonNull;
import rx.Observable;
import site.iurysouza.cinefilo.domain.medialist.WatchMedia;

/**
 * Created by Iury Souza on 14/02/2017.
 */
public interface IMovieDetailRepository {
  Observable<MovieDetail> getMovieById(int movieId, String apiKey);

  Observable<List<WatchMedia>> getMoviesSimilarTo(int movieId, int page, String apiKey);

  @NonNull Observable<Credits> getMoviesCredits(int movieId, String apiKey);
}
