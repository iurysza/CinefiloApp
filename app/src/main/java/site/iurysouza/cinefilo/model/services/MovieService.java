package site.iurysouza.cinefilo.model.services;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;
import site.iurysouza.cinefilo.model.entities.pojo.GenreResult;
import site.iurysouza.cinefilo.model.entities.pojo.Movie;
import site.iurysouza.cinefilo.model.entities.pojo.MovieResults;

public interface MovieService {

  @GET("movie/{movie_id}")
  Observable<Movie> getMovieById(
      @Path("movie_id") int movieId,
      @Query("api_key") String apiKey);
  @GET("genre/{genre_id}/movies")

  Observable<MovieResults> getMoviesByGenre(
      @Path("genre_id") int genreId,
      @Query("api_key") String apiKey);

  @GET("movie/popular")
  Observable<MovieResults> getMostPopularMovies(
      @Query("api_key") String apiKey,
      @Query("page") int page);

  @GET("genre/movie/list")
  Observable<GenreResult> getMovieGenreList(
      @Query("api_key") String apiKey);

  @GET("movie/top_rated")
  Observable<MovieResults> getTopRatedMovies(
      @Query("api_key") String apiKey,
      @Query("page") int page);

  @GET("movie/now_playing")
  Observable<MovieResults> getNowPlayingMovies(
      @Query("api_key") String apiKey,
      @Query("page") int page);

  @GET("discover/movie")
  Observable<MovieResults> getFilteredMovies(
      @Query("api_key") String apiKey,
      @Query("page")int page,
      @Query("primary_release_date.gte") int startDate,
      @Query("primary_release_date.lte") int endDate,
      @Query("with_genres")String genres,
      @Query("vote_average.lte") int minScore);
}
