package site.iurysouza.cinefilo.model.services;

import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;
import site.iurysouza.cinefilo.model.entities.pojo.Genre;
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

  @GET("/genre/movie/list")
  Observable<List<Genre>> getMovieGenreList(
      @Query("api_key") String apiKey);

  @GET("movie/top_rated")
  Observable<Results> getTopRatedMovies(
      @Query("api_key") String apiKey,
      @Query("page") int page);

  @GET("movie/now_playing")
  Observable<Results> getNowPlayingMovies(
      @Query("api_key") String apiKey,
      @Query("page") int page);
}
