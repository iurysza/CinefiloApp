package site.iurysouza.cinefilo.domain;

import io.realm.RealmResults;
import javax.inject.Inject;
import rx.Observable;
import site.iurysouza.cinefilo.model.data.MovieDataRepository;
import site.iurysouza.cinefilo.model.entities.realm.RealmMovie;

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
    return movieRepository.getMoviesByPopulariy(popPage);
  }
  @Override public Observable<RealmResults<RealmMovie>> getTopRatedMoviesObservable() {
    topPage = 1;
    return movieRepository.getTopRatedMovies(topPage);
  }
  public Observable<RealmResults<RealmMovie>> getNowPlayingMovies() {
    recPage = 1;
    return movieRepository.getNowPlayingMovies(recPage);
  }

  public Observable getNextPopularPage() {
    popPage++;
    return movieRepository.getMoviesByPopulariy(popPage);
  }
  public Observable getNextTopPage() {
    topPage++;
    return movieRepository.getTopRatedMovies(topPage);
  }
  public Observable getNextRecentPage() {
    recPage++;
    return movieRepository.getNowPlayingMovies(recPage);
  }
}
