package site.iurysouza.cinefilo.model.data.storage;

import io.realm.Realm;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;
import javax.inject.Inject;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import site.iurysouza.cinefilo.model.entities.mapper.MovieDataMapper;
import site.iurysouza.cinefilo.model.entities.realm.RealmMovie;
import site.iurysouza.cinefilo.model.entities.realm.RealmPopularMovies;
import site.iurysouza.cinefilo.model.entities.realm.RealmTopMovies;
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

  private void saveToRealm(RealmMovie realmMovie) {
    realm.executeTransactionAsync(realm -> realm.insertOrUpdate(realmMovie),
        throwable -> {
          Timber.e(throwable, "Could not save data");
        });
  }

  public void getMostPopularMovies(int page) {
    movieService
        .getMostPopularMovies(Constants.MOVIE_DB_API.API_KEY, page)
        .subscribeOn(Schedulers.io())
        .map(MovieDataMapper::mapPopResults)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(this::savePopularMovieListToRealm, Throwable::printStackTrace);
  }

  private void savePopularMovieListToRealm(RealmPopularMovies popularListRealm) {
    realm.executeTransactionAsync(realm -> realm.insert(popularListRealm),
        throwable -> {
          Timber.e(throwable, "Could not save data");
        });
  }

  public void getTopRatedMovies(int page) {
    movieService
        .getTopRatedMovies(Constants.MOVIE_DB_API.API_KEY, page)
        .subscribeOn(Schedulers.io())
        .map(MovieDataMapper::mapTopResults)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(this::saveTopRatedMovies, Throwable::printStackTrace);
  }

  private void saveTopRatedMovies(RealmTopMovies topMovieListRealm) {
    realm.executeTransactionAsync(realm1 -> {
      try {
        realm1.insert(topMovieListRealm);
      } catch (RealmPrimaryKeyConstraintException e) {
      }
    },
        throwable -> {
          Timber.e(throwable, "Could not save data");
        });
  }
}
