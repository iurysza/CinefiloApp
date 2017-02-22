package site.iurysouza.cinefilo.model.data.moviedetail.storage;

import java.util.List;
import javax.inject.Inject;
import rx.Observable;
import site.iurysouza.cinefilo.model.entities.pojo.Movie;
import site.iurysouza.cinefilo.model.entities.pojo.MovieResults;
import site.iurysouza.cinefilo.model.data.moviedetail.services.MovieDetailService;
import site.iurysouza.cinefilo.util.Constants;

/**
 * Created by Iury Souza on 31/01/2017.
 */
public class CloudMovieDetailDataSource implements ICloudMovieDetailDataSource {
  private MovieDetailService detailService;

  @Inject
  public CloudMovieDetailDataSource(MovieDetailService detailService) {
    this.detailService = detailService;
  }

  @Override
  public Observable<Movie> getMovieById(int movieId) {
    return detailService
        .getMovieById(movieId, Constants.MOVIE_DB_API.API_KEY);
  }
  @Override
  public Observable<List<Movie>> getMoviesSimilarTo(int movieId, int page) {
    return detailService
        .getMoviesSimilarTo(movieId, page, Constants.MOVIE_DB_API.API_KEY)
        .map(MovieResults::getMovieList);
  }
}
