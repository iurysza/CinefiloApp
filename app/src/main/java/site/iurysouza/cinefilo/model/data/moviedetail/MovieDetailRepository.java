package site.iurysouza.cinefilo.model.data.moviedetail;

import android.support.annotation.NonNull;
import java.util.List;
import javax.inject.Inject;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import site.iurysouza.cinefilo.model.data.entity.MovieDetailValue;
import site.iurysouza.cinefilo.model.data.entity.WatchMedia;
import site.iurysouza.cinefilo.model.data.moviedetail.storage.ICloudMovieDetailDataSource;
import site.iurysouza.cinefilo.model.data.moviedetail.storage.ILocalDetailDataSource;
import site.iurysouza.cinefilo.model.entities.realm.RealmMovie;

import static site.iurysouza.cinefilo.model.entities.realm.RealmMovie.DEFAULT_QUERY;

/**
 * Created by Iury Souza on 31/01/2017.
 */
public class MovieDetailRepository implements IMovieDetailRepository {

  private final ILocalDetailDataSource localDetailDataSource;
  private final ICloudMovieDetailDataSource cloudMovieDetailDataSource;

  @Inject
  public MovieDetailRepository(
      ILocalDetailDataSource localDetailDataSource,
      ICloudMovieDetailDataSource cloudMovieDetailDataSource) {

    this.localDetailDataSource = localDetailDataSource;
    this.cloudMovieDetailDataSource = cloudMovieDetailDataSource;
  }

  @Override
  public Observable<MovieDetailValue> getMovieById(int movieId) {

    Observable<RealmMovie> movieFromLocalSource =
        localDetailDataSource
            .getMovieById(movieId)
            .subscribeOn(Schedulers.computation());

    Observable<RealmMovie> cloudFromLocalSource =
        getNowPlayingFromApi(movieId);

    return Observable.concat(movieFromLocalSource, cloudFromLocalSource)
        .first(realmMovie -> realmMovie != null)
        .map(MovieDetailValue::mapToValueMedia)
        .observeOn(AndroidSchedulers.mainThread());
  }

  @NonNull @Override public Observable<RealmMovie> getNowPlayingFromApi(int movieId) {
    return cloudMovieDetailDataSource
        .getMovieById(movieId)
        .map(movie -> {
          RealmMovie realmMovie = RealmMovie.valueOf(movie, DEFAULT_QUERY);
          localDetailDataSource.storeMovie(realmMovie);
          return realmMovie;
        });
  }

  @NonNull @Override
  public Observable<List<WatchMedia>> getMoviesSimilarTo(int movieId, int page) {
    return cloudMovieDetailDataSource
        .getMoviesSimilarTo(movieId, page)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .map(WatchMedia::valueOfMovieList);
  }
}
