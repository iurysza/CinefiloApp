package site.iurysouza.cinefilo.model.data.moviedetail.storage;

import java.util.List;
import rx.Observable;
import site.iurysouza.cinefilo.model.entities.pojo.Movie;
import site.iurysouza.cinefilo.model.entities.pojo.MovieCredits;

/**
 * Created by Iury Souza on 08/02/2017.
 */
public interface ICloudMovieDetailDataSource {
  Observable<Movie> getMovieById(int movieId, String apiKey);

  Observable<List<Movie>> getMoviesSimilarTo(int movieId, int page, String apiKey);

  Observable<MovieCredits> getMovieCredits(int movieId, String apiKey);
}