package site.iurysouza.cinefilo.model.data.movies.storage;

import javax.inject.Inject;
import rx.Observable;
import site.iurysouza.cinefilo.model.entities.realm.RealmMoviesResults;
import site.iurysouza.cinefilo.model.services.MovieService;
import site.iurysouza.cinefilo.util.Constants;
import timber.log.Timber;

import static site.iurysouza.cinefilo.model.entities.realm.RealmMovie.NOW_QUERY;
import static site.iurysouza.cinefilo.model.entities.realm.RealmMovie.POP_QUERY;
import static site.iurysouza.cinefilo.model.entities.realm.RealmMovie.TOP_QUERY;

/**
 * Created by Iury Souza on 12/10/2016.
 */

public class CloudMovieDataSource {
  private final MovieService movieService;

  @Inject
  public CloudMovieDataSource(MovieService movieService) {
    this.movieService = movieService;
  }

  public Observable<RealmMoviesResults> getNowPlayingMovies(int page) {
    return movieService
        .getNowPlayingMovies(Constants.MOVIE_DB_API.API_KEY, page)
        .map(movieResults -> RealmMoviesResults.valueOf(movieResults, NOW_QUERY))
        .doOnNext(this::printLoadedMovies);
  }

  public Observable<RealmMoviesResults> getTopRatedMovies(int page) {
    return movieService
        .getTopRatedMovies(Constants.MOVIE_DB_API.API_KEY, page)
        .map(movieResults -> RealmMoviesResults.valueOf(movieResults, TOP_QUERY))
        .doOnNext(this::printLoadedMovies);
  }

  public Observable<RealmMoviesResults> getMostPopularMovies(int page) {
    return movieService
        .getMostPopularMovies(Constants.MOVIE_DB_API.API_KEY, page)
        .map(movieResults -> RealmMoviesResults.valueOf(movieResults, POP_QUERY))
        .doOnNext(this::printLoadedMovies);
  }

  private void printLoadedMovies(RealmMoviesResults realmMoviesResults) {
    Timber.i("Loaded %s movies from network", realmMoviesResults.getMovieList().size());
  }


  /*
  public void movieById(int movieId) {
    //movieService
    //    .getMovieById(movieId, Constants.MOVIE_DB_API.API_KEY)
    //    .map(MovieDataMapper::map)
    //    .subscribeOn(Schedulers.io())
    //    .observeOn(AndroidSchedulers.mainThread())
    //    .subscribe(this::saveToRealm, Throwable::printStackTrace);
  }

  public void getUpdateGenreList() {
    //movieService
    //    .getMovieGenreList(Constants.MOVIE_DB_API.API_KEY)
    //    .subscribeOn(Schedulers.io())
    //    .map(genreResult -> GenreDataMapper.map(genreResult.getGenreList()))
    //    .observeOn(AndroidSchedulers.mainThread())
    //    .subscribe(this::saveToRealm, Throwable::printStackTrace);
  }
  */

}
