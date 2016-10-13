package site.iurysouza.cinefilo.data.services;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;
import site.iurysouza.cinefilo.data.entities.MovieEntity;

public interface MovieService {
  @GET("movie/{movie_id}")
  Observable<MovieEntity> getMovieById(
      @Path("movie_id") String movieId,
      @Query("api_key") String apiKey);
}
