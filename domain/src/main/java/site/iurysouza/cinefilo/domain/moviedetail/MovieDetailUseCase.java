package site.iurysouza.cinefilo.domain.moviedetail;

import java.util.List;
import javax.inject.Inject;
import rx.Observable;
import site.iurysouza.cinefilo.domain.watchmedialist.WatchMedia;

/**
 * Created by Iury Souza on 31/01/2017.
 */
public class MovieDetailUseCase implements IMovieDetailUseCase {
  private static final int FIRST_PAGE = 1;
  private IMovieDetailRepository detailRepository;

  @Inject
  public MovieDetailUseCase(IMovieDetailRepository detailRepository) {
    this.detailRepository = detailRepository;
  }

  @Override
  public Observable<MovieDetail> getMovieById(int movieId) {
    return detailRepository.getMovieById(movieId);
  }

  @Override
  public Observable<List<WatchMedia>> geMoviesSimilarTo(int movieId) {
    return detailRepository
        .getMoviesSimilarTo(movieId, FIRST_PAGE);
  }
}
