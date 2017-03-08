package site.iurysouza.cinefilo.model.data.moviedetail.services;

import java.util.Map;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;
import site.iurysouza.cinefilo.model.entities.pojo.Movie;
import site.iurysouza.cinefilo.model.entities.pojo.MovieResults;

/**
 * Created by Iury Souza on 31/01/2017.
 */
public interface MovieDetailService {
  String APPEND_TO_RESPONSE = "append_to_response";
  String API_KEY = "api_key";

  @GET("movie/{movie_id}")
  Observable<Movie> getMovieById(
      @Path("movie_id") int movieId,
      @QueryMap Map<String, String> params);

  @GET("movie/{movie_id}/similar")
  Observable<MovieResults> getMoviesSimilarTo(
      @Path("movie_id") int movieId,
      @Query("page") int page,
      @Query("api_key") String apiKey);
}
