package site.iurysouza.cinefilo.model.data.storage;

import io.realm.Realm;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;
import javax.inject.Inject;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import site.iurysouza.cinefilo.model.entities.mapper.MovieDataMapper;
import site.iurysouza.cinefilo.model.entities.realm.RealmMovie;
import site.iurysouza.cinefilo.model.entities.realm.RealmMoviesResults;
import site.iurysouza.cinefilo.model.services.MovieService;
import site.iurysouza.cinefilo.util.Constants;
import timber.log.Timber;

/**
 * Created by Iury Souza on 12/10/2016.
 */

public class CloudMovieDataStore {
  private MovieService movieService;
  private Realm realm;

  @Inject
  public CloudMovieDataStore(MovieService movieService, Realm realm) {
    this.movieService = movieService;
    this.realm = realm;
  }

  public void movieById(int movieId) {
    movieService
        .getMovieById(movieId, Constants.MOVIE_DB_API.API_KEY)
        .map(MovieDataMapper::map)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(this::saveToRealm,
            throwable -> {
              Timber.e("error: %s", movieId,
                  throwable.toString());
            });
  }

  public void getTopRatedMovies(int page) {
    movieService
        .getTopRatedMovies(Constants.MOVIE_DB_API.API_KEY, page)
        .subscribeOn(Schedulers.io())
        .map(MovieDataMapper::mapResultsToRealmResults)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(this::saveToRealm, Throwable::printStackTrace);
  }

  public void getNowPlayingMovies(int page) {
    movieService
        .getNowPlayingMovies(Constants.MOVIE_DB_API.API_KEY, page)
        .subscribeOn(Schedulers.io())
        .map(MovieDataMapper::mapResultsToRealmResults)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(this::saveToRealm, Throwable::printStackTrace);
  }

  public void getMostPopularMovies(int page) {
    movieService
        .getMostPopularMovies(Constants.MOVIE_DB_API.API_KEY, page)
        .subscribeOn(Schedulers.io())
        .map(MovieDataMapper::mapResultsToRealmResults)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(this::saveToRealm, Throwable::printStackTrace);
  }

  private void saveToRealm(RealmMoviesResults realmMoviesResults) {
    realm.executeTransactionAsync(realm1 -> {
          try {
            realm1.insert(realmMoviesResults);
          } catch (RealmPrimaryKeyConstraintException e) {
          }
        },
        throwable -> {
          Timber.e(throwable, "Could not save data");
        });
  }

  private void saveToRealm(RealmMovie realmMovie) {
    realm.executeTransactionAsync(realm -> realm.insertOrUpdate(realmMovie),
        throwable -> {
          Timber.e(throwable, "Could not save data");
        });
  }
}
