package site.iurysouza.cinefilo.model.data.moviedetail.storage;

import javax.inject.Inject;
import rx.Observable;
import site.iurysouza.cinefilo.model.entities.pojo.Movie;
import site.iurysouza.cinefilo.model.services.MovieDetailService;
import site.iurysouza.cinefilo.util.Constants;
import timber.log.Timber;

/**
 * Created by Iury Souza on 31/01/2017.
 */
public class CloudMovieDetailDataSource {
  private MovieDetailService detailService;

  @Inject
  public CloudMovieDetailDataSource(MovieDetailService detailService) {
    this.detailService = detailService;
  }

  public Observable<Movie> getMovieById(int movieId) {
    return detailService
        .getMovieById(movieId, Constants.MOVIE_DB_API.API_KEY)
        .doOnNext(movie -> Timber.d("Loaded %s from api", movie.getTitle()));
  }
}
