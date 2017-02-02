package site.iurysouza.cinefilo.model.services;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;
import site.iurysouza.cinefilo.model.entities.pojo.Movie;

/**
 * Created by Iury Souza on 31/01/2017.
 */
public interface MovieDetailService {
  @GET("movie/{movie_id}")
  Observable<Movie> getMovieById(
      @Path("movie_id") int movieId,
      @Query("api_key") String apiKey);
}
