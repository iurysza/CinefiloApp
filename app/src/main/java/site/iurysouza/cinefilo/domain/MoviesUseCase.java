package site.iurysouza.cinefilo.domain;

import io.realm.RealmResults;
import java.util.List;
import javax.inject.Inject;
import rx.Observable;
import site.iurysouza.cinefilo.model.data.MovieDataRepository;
import site.iurysouza.cinefilo.model.entities.realm.RealmMovie;
import site.iurysouza.cinefilo.util.Constants;

/**
 * Created by Iury Souza on 09/11/2016.
 */

public class MoviesUseCase implements UseCase {

  private MovieDataRepository movieRepository;
  private int popPage;
  private int topPage;
  private int recPage;

  @Inject
  public MoviesUseCase(MovieDataRepository movieRepository) {
    this.movieRepository = movieRepository;
  }

  @Override public Observable<RealmResults<RealmMovie>> getPopMoviesObservable() {
    popPage = 1;
    return movieRepository.getMoviesByPopulariy(popPage,true);
  }
  @Override public Observable<RealmResults<RealmMovie>> getTopRatedMoviesObservable() {
    topPage = 1;
    return movieRepository.getTopRatedMovies(topPage, true);
  }

  public Observable<RealmResults<RealmMovie>> getNowPlayingMovies() {
    recPage = 1;
    return movieRepository.getNowPlayingMovies(recPage, true);
  }


  public Observable<List<RealmMovie>> getMoviewBackDrops() {
    updateGenres();
    return movieRepository.getShowCaseMovies();
  }

  private void updateGenres() {
    movieRepository.getGenreList();
  }



  public Observable<RealmResults<RealmMovie>> getNextPopularPage(int page) {
    return movieRepository.getMoviesByPopulariy(page, true);
  }

  private int getNextPageBasedOnListSize(int page) {
    return Math.round(page / Constants.Movies.PAGE_SIZE)+1;
  }

  public Observable<RealmResults<RealmMovie>> getNextTopPage(int page) {
    return movieRepository.getTopRatedMovies(page, true);
  }
  public Observable<RealmResults<RealmMovie>> getNextRecentPage(int page) {
    return movieRepository.getNowPlayingMovies(page, true);
  }
}
