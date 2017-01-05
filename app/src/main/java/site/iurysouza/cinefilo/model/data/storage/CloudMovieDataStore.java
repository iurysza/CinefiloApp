package site.iurysouza.cinefilo.model.data.storage;

import io.realm.Realm;
import java.util.List;
import javax.inject.Inject;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import site.iurysouza.cinefilo.model.entities.mapper.GenreDataMapper;
import site.iurysouza.cinefilo.model.entities.mapper.MovieDataMapper;
import site.iurysouza.cinefilo.model.entities.realm.RealmGenre;
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
        .map(results -> MovieDataMapper.mapResultsToRealmResults(results, RealmMovie.TOP_QUERY))
        .doOnNext(this::printLoadedMovies)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(this::saveToRealm, Throwable::printStackTrace);
  }

  public void getNowPlayingMovies(int page) {
    movieService
        .getNowPlayingMovies(Constants.MOVIE_DB_API.API_KEY, page)
        .subscribeOn(Schedulers.io())
        .map(results -> MovieDataMapper.mapResultsToRealmResults(results, RealmMovie.NOW_QUERY))
        .doOnNext(this::printLoadedMovies)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(this::saveToRealm, Throwable::printStackTrace);
  }

  private void printLoadedMovies(RealmMoviesResults realmMoviesResults) {
    Timber.e("Loaded %s movies from network",realmMoviesResults.getMovieList().size());
  }

  public void getMostPopularMovies(int page) {
    movieService
        .getMostPopularMovies(Constants.MOVIE_DB_API.API_KEY, page)
        .subscribeOn(Schedulers.io())
        .map(results -> MovieDataMapper.mapResultsToRealmResults(results, RealmMovie.POP_QUERY))
        .doOnNext(this::printLoadedMovies)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(this::saveToRealm, Throwable::printStackTrace);
  }

  public void getUpdateGenreList() {
    movieService
        .getMovieGenreList(Constants.MOVIE_DB_API.API_KEY)
        .subscribeOn(Schedulers.io())
        .map(genreResult -> GenreDataMapper.map(genreResult.getGenreList()))
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(this::saveToRealm, Throwable::printStackTrace);
  }

  private void saveToRealm(RealmMoviesResults realmMoviesResults) {
    realm.executeTransactionAsync(realm -> {
          try {
            realm.insert(realmMoviesResults);
          } catch (RuntimeException e) {
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

  private void saveToRealm(List<RealmGenre> realmGenres) {
    realm.executeTransactionAsync(realm -> {
          try {
            realm.insert(realmGenres);
          } catch (RuntimeException e) {
          }
        },
        throwable -> {
          Timber.e(throwable, "Could not save data");
        });
  }
}
