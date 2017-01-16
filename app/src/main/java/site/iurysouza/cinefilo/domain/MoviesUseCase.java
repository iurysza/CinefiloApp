package site.iurysouza.cinefilo.domain;

import java.util.List;
import javax.inject.Inject;
import rx.Observable;
import site.iurysouza.cinefilo.domain.entity.WatchMediaValue;
import site.iurysouza.cinefilo.domain.entity.WatchMediaValueMapper;
import site.iurysouza.cinefilo.model.data.movies.MoviesRepository;
import site.iurysouza.cinefilo.presentation.UseCase;

import static site.iurysouza.cinefilo.model.entities.realm.RealmCurrentPage.TOP_RATED_LIST;

/**
 * Created by Iury Souza on 09/11/2016.
 */

public class MoviesUseCase implements UseCase {

  public static final int NO_FILTER = -1;
  private static final int FIRST_PAGE = 1;
  private MoviesRepository movieRepository;

  @Inject
  public MoviesUseCase(MoviesRepository movieRepository) {
    this.movieRepository = movieRepository;
    //movieRepository.getGenreList();
  }

  @Override
  public Observable<List<WatchMediaValue>> getMostPopular() {
    movieRepository.getMostPopular(FIRST_PAGE, false);

    return movieRepository
        .getMostPopularSubject()
        .map(WatchMediaValueMapper::mapToValueMedia);
  }

  @Override
  public Observable<List<WatchMediaValue>> getNextPopular(int nextPage) {
    movieRepository.getMostPopular(nextPage, false);

    return movieRepository
        .getMostPopularSubject()
        .map(WatchMediaValueMapper::mapToValueMedia);
  }

  @Override
  public Observable<List<WatchMediaValue>> getTopRated() {
    movieRepository.getTopRated(FIRST_PAGE, false);

    return movieRepository
        .getTopRatedSubject()
        .map(WatchMediaValueMapper::mapToValueMedia);
  }

  @Override
  public Observable<List<WatchMediaValue>> getNextTopRated(int nextPage, int filter) {

    if (filter == NO_FILTER) {
      movieRepository.getTopRated(nextPage, true);
      return movieRepository
          .getTopRatedSubject()
          .map(WatchMediaValueMapper::mapToValueMedia);
    } else {
      movieRepository.getByGenre(filter, TOP_RATED_LIST);
      return movieRepository
          .getTopRatedSubject()
          .skip(1) //skips BehaviorSubject reemmission
          .map(WatchMediaValueMapper::mapToValueMedia);
    }
  }

  @Override
  public Observable<List<WatchMediaValue>> getNowPlaying() {
    movieRepository.getNowPlaying(FIRST_PAGE, false);

    return movieRepository
        .getNowPlayingSubject()
        .map(WatchMediaValueMapper::mapToValueMedia);
  }

  @Override
  public Observable<List<WatchMediaValue>> getNextNowPlaying(int nextPage) {
    movieRepository.getNowPlaying(nextPage, true);

    return movieRepository
        .getNowPlayingSubject()
        .map(WatchMediaValueMapper::mapToValueMedia);
  }
}
