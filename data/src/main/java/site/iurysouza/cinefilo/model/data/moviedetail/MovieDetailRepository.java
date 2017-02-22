package site.iurysouza.cinefilo.model.data.moviedetail;

import java.util.List;
import javax.inject.Inject;
import lombok.NonNull;
import rx.Observable;
import rx.schedulers.Schedulers;
import site.iurysouza.cinefilo.domain.moviedetail.IMovieDetailRepository;
import site.iurysouza.cinefilo.domain.moviedetail.MovieDetail;
import site.iurysouza.cinefilo.domain.watchmedialist.WatchMedia;
import site.iurysouza.cinefilo.model.data.moviedetail.storage.ICloudMovieDetailDataSource;
import site.iurysouza.cinefilo.model.data.moviedetail.storage.ILocalDetailDataSource;
import site.iurysouza.cinefilo.model.entities.pojo.Movie;
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
  public Observable<MovieDetail> getMovieById(int movieId) {

    Observable<RealmMovie> movieFromLocalSource =
            localDetailDataSource
                    .getMovieById(movieId)
                    .subscribeOn(Schedulers.computation());

    Observable<RealmMovie> cloudFromLocalSource =
            getMovieDetailFromApi(movieId);

    return Observable.concat(movieFromLocalSource, cloudFromLocalSource)
            .first(realmMovie -> realmMovie != null)
            .map(RealmMovie::mapToValueMedia);
  }

  @NonNull private Observable<RealmMovie> getMovieDetailFromApi(int movieId) {
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
            .map(Movie::valueOfMovieList);
  }
}
