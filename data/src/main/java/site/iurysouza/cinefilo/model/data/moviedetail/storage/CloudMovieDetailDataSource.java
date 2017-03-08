package site.iurysouza.cinefilo.model.data.moviedetail.storage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import rx.Observable;
import site.iurysouza.cinefilo.model.data.moviedetail.services.MovieDetailService;
import site.iurysouza.cinefilo.model.entities.pojo.Movie;
import site.iurysouza.cinefilo.model.entities.pojo.MovieCredits;
import site.iurysouza.cinefilo.model.entities.pojo.MovieResults;

import static site.iurysouza.cinefilo.model.data.moviedetail.services.MovieDetailService.API_KEY;
import static site.iurysouza.cinefilo.model.data.moviedetail.services.MovieDetailService.APPEND_TO_RESPONSE;

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
    Map<String, String> params = new HashMap<>();
    params.put(API_KEY, apiKey);
    params.put(APPEND_TO_RESPONSE, "videos");

    return detailService.getMovieById(movieId, params);
  }

  @Override
  public Observable<List<Movie>> getMoviesSimilarTo(int movieId, int page, String apiKey) {
    return detailService
        .getMoviesSimilarTo(movieId, page, apiKey)
        .map(MovieResults::getMovieList);
  }

  @Override
  public Observable<MovieCredits> getMovieCredits(int movieId, String apiKey) {
    return detailService
        .getMovieCredits(movieId, apiKey);
  }
}
