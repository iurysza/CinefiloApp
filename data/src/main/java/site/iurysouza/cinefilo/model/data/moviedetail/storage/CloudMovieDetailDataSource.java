package site.iurysouza.cinefilo.model.data.moviedetail.storage;

import java.util.List;
import javax.inject.Inject;
import rx.Observable;
import site.iurysouza.cinefilo.model.entities.pojo.Movie;
import site.iurysouza.cinefilo.model.entities.pojo.MovieResults;
import site.iurysouza.cinefilo.model.data.moviedetail.services.MovieDetailService;

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
  public Observable<Movie> getMovieById(int movieId, String apiKey) {
    return detailService
        .getMovieById(movieId, apiKey);
  }
  @Override
  public Observable<List<Movie>> getMoviesSimilarTo(int movieId, int page, String apiKey) {
    return detailService
        .getMoviesSimilarTo(movieId, page, apiKey)
        .map(MovieResults::getMovieList);
  }
}
