package site.iurysouza.cinefilo.model.data.medialist.series.services;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;
import site.iurysouza.cinefilo.model.entities.pojo.Series;
import site.iurysouza.cinefilo.model.entities.pojo.SeriesResult;

public interface SeriesService {

  @GET("tv/{tv_id}")
  Observable<Series> getSeriesById(
      @Path("tv_id") int seriesId,
      @Query("api_key") String apiKey);

  @GET("tv/popular")
  Observable<SeriesResult> getMostPopularSeries(
      @Query("api_key") String apiKey,
      @Query("page") int page);


  @GET("tv/top_rated")
  Observable<SeriesResult> getTopRatedSeries(
      @Query("api_key") String apiKey,
      @Query("page") int page);

  @GET("tv/on_the_air")
  Observable<SeriesResult> getNowPlayingSeries(
      @Query("api_key") String apiKey,
      @Query("page") int page);
}
