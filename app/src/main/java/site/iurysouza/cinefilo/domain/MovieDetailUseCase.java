package site.iurysouza.cinefilo.domain;

import java.util.List;
import javax.inject.Inject;
import rx.Observable;
import site.iurysouza.cinefilo.domain.entity.WatchMediaValue;
import site.iurysouza.cinefilo.model.data.entity.MovieDetailValue;
import site.iurysouza.cinefilo.model.data.moviedetail.MovieDetailRepository;

/**
 * Created by Iury Souza on 31/01/2017.
 */
public class MovieDetailUseCase {
  private MovieDetailRepository detailRepository;

  @Inject
  public MovieDetailUseCase(MovieDetailRepository detailRepository) {
    this.detailRepository = detailRepository;
  }

  public Observable<MovieDetailValue> getMovieById(int movieId) {
    return detailRepository.getMovieById(movieId);
  }

  public Observable<List<WatchMediaValue>> geMoviesSimilarTo(int movieId, int page) {
    return detailRepository
        .getMoviesSimilarTo(movieId, page)
        .map(WatchMediaValue::valueOf);
  }
}
