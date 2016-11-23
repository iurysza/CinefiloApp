package site.iurysouza.cinefilo.model.services;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;
import site.iurysouza.cinefilo.model.entities.pojo.Movie;
import site.iurysouza.cinefilo.model.entities.pojo.Results;

public interface MovieService {
  @GET("movie/{movie_id}")
  Observable<Movie> getMovieById(
      @Path("movie_id") int movieId,
      @Query("api_key") String apiKey);

  @GET("movie/popular")
  Observable<Results> getMostPopularMovies(
      @Query("api_key") String apiKey,
      @Query("page") int page);
}
