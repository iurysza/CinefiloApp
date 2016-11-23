package site.iurysouza.cinefilo.data.Repos.DataStore;

import io.realm.Realm;
import javax.inject.Inject;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import site.iurysouza.cinefilo.data.entities.mapper.MovieDataMapper;
import site.iurysouza.cinefilo.data.entities.realm.RealmMovie;
import site.iurysouza.cinefilo.data.entities.realm.RealmPopularMovies;
import site.iurysouza.cinefilo.data.services.MovieService;
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
        .map(MovieDataMapper::mapResults)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(this::savePopularMovieListToRealm, Throwable::printStackTrace);
  }

  private void savePopularMovieListToRealm(RealmPopularMovies popularListRealm) {
    realm.executeTransactionAsync(realm -> realm.insert(popularListRealm),
        throwable -> {
          Timber.e(throwable, "Could not save data");
        });
  }
}
