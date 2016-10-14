package site.iurysouza.cinefilo.data.Repos.DataStore;

import io.realm.Realm;
import javax.inject.Inject;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import site.iurysouza.cinefilo.data.entities.MovieRealm;
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
        .map(MovieDataMapper::transform)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(this::saveToRealm,
            throwable -> {
              Timber.e("Failure: Movie Data not with id: %s - error: %s", movieId,
                  throwable.toString());
            });
  }

  private void saveToRealm(MovieRealm movieRealm) {
    realm.executeTransactionAsync(realm -> realm.insertOrUpdate(movieRealm),
        throwable -> {
          Timber.e(throwable, "Could not save data");
        });
  }
}
