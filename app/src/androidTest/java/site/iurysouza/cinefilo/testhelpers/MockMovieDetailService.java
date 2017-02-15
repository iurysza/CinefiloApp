package site.iurysouza.cinefilo.testhelpers;

import retrofit2.mock.BehaviorDelegate;
import rx.Observable;
import site.iurysouza.cinefilo.model.entities.pojo.Movie;
import site.iurysouza.cinefilo.model.entities.pojo.MovieResults;
import site.iurysouza.cinefilo.model.services.MovieDetailService;
import site.iurysouza.cinefilo.tests.MovieFactory;

/**
 * Created by Iury Souza on 14/02/2017.
 */

public class MockMovieDetailService implements MovieDetailService {
  private final BehaviorDelegate<MovieDetailService> delegate;

  public MockMovieDetailService(BehaviorDelegate<MovieDetailService> delegate) {
    this.delegate = delegate;
  }

  @Override public Observable<Movie> getMovieById(int movieId, String apiKey) {
    return delegate.returningResponse(MovieFactory.createMovieDetailWithId(movieId)).getMovieById(movieId, apiKey);
  }

  @Override
  public Observable<MovieResults> getMoviesSimilarTo(int movieId, int page, String apiKey) {
    return delegate.returningResponse(MovieFactory.createMovieResultsByPage(page)).getMoviesSimilarTo(movieId, page, apiKey);
  }
}
