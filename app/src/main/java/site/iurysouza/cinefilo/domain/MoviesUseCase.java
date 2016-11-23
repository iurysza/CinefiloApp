package site.iurysouza.cinefilo.domain;

import javax.inject.Inject;
import rx.Observable;
import site.iurysouza.cinefilo.model.data.MovieDataRepository;
import site.iurysouza.cinefilo.model.entities.realm.RealmPopularMovies;

/**
 * Created by Iury Souza on 09/11/2016.
 */

public class MoviesUseCase implements UseCase {

  private MovieDataRepository movieRepository;
  private int page;

  @Inject
  public MoviesUseCase(MovieDataRepository movieRepository) {
    this.movieRepository = movieRepository;
  }

  @Override public Observable<RealmPopularMovies> buildUseCaseObservable() {
    page = 1;
    return movieRepository.getMoviesByPopulariy(page);
  }

  public Observable getNextPopularPage() {
    page++;
    return movieRepository.getMoviesByPopulariy(page);
  }
}
