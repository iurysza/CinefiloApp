package site.iurysouza.cinefilo.model.data.moviedetail;

import android.support.annotation.NonNull;
import java.util.List;
import javax.inject.Inject;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import site.iurysouza.cinefilo.model.data.entity.MovieDetailValue;
import site.iurysouza.cinefilo.model.data.entity.WatchMedia;
import site.iurysouza.cinefilo.model.data.moviedetail.storage.CloudMovieDetailDataSource;
import site.iurysouza.cinefilo.model.data.moviedetail.storage.LocalDetailDataSource;
import site.iurysouza.cinefilo.model.entities.realm.RealmMovie;

import static site.iurysouza.cinefilo.model.entities.realm.RealmMovie.QUERY_TYPE_DETAIL;

/**
 * Created by Iury Souza on 31/01/2017.
 */
public class MovieDetailRepository {
  private final LocalDetailDataSource localDetailDataSource;
  private final CloudMovieDetailDataSource cloudMovieDetailDataSource;

  @Inject
  public MovieDetailRepository(LocalDetailDataSource localDetailDataSource,
      CloudMovieDetailDataSource cloudMovieDetailDataSource) {
    this.localDetailDataSource = localDetailDataSource;
    this.cloudMovieDetailDataSource = cloudMovieDetailDataSource;
  }

  public Observable<MovieDetailValue> getMovieById(int movieId) {

    //Observable<RealmMovie> movieFromLocalSource = localDetailDataSource.getMovieById(movieId).subscribeOn(Schedulers.computation());
    Observable<RealmMovie> cloudFromLocalSource = getNowPlayingFromApi(movieId).subscribeOn(Schedulers.io());

    return cloudFromLocalSource
        .map(MovieDetailValue::mapToValueMedia)
        .observeOn(AndroidSchedulers.mainThread());
  }

  @NonNull
  private Observable<RealmMovie> getNowPlayingFromApi(int movieId) {
    return cloudMovieDetailDataSource
        .getMovieById(movieId)
        .map(movie -> {
          localDetailDataSource.storeMovie(movie);
          return RealmMovie.valueOf(movie, QUERY_TYPE_DETAIL);
        });
  }

  @NonNull
  public Observable<List<WatchMedia>> getMoviesSimilarTo(int movieId, int page) {
    return cloudMovieDetailDataSource
        .getMoviesSimilarTo(movieId,page)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .map(WatchMedia::valueOfMovieList);
  }
}
