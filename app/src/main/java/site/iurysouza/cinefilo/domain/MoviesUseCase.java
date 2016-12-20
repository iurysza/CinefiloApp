package site.iurysouza.cinefilo.domain;

import io.realm.RealmResults;
import javax.inject.Inject;
import rx.Observable;
import site.iurysouza.cinefilo.model.data.MovieDataRepository;
import site.iurysouza.cinefilo.model.entities.realm.RealmMovie;
import site.iurysouza.cinefilo.model.entities.realm.RealmPopularMovies;
import site.iurysouza.cinefilo.model.entities.realm.RealmTopMovies;

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

  @Override public Observable<RealmPopularMovies> getPopMoviesObservable() {
    popPage = 1;
    return movieRepository.getMoviesByPopulariy(popPage);
  }
  @Override public Observable<RealmTopMovies> getTopRatedMoviesObservable() {
    topPage = 1;
    return movieRepository.getTopRatedMovies(topPage);
  }
  public Observable<RealmResults<RealmMovie>> getPopMoviesObservableNew() {
    topPage = 1;
    return movieRepository.getPopMoviesNew(topPage);
  }

  public Observable getNextPopularPage() {
    popPage++;
    return movieRepository.getMoviesByPopulariy(popPage);
  }
  public Observable getNextTopPage() {
    topPage++;
    return movieRepository.getTopRatedMovies(topPage);
  }
}
