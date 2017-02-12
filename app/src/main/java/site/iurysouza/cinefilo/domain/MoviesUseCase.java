package site.iurysouza.cinefilo.domain;

import java.util.List;
import javax.inject.Inject;
import rx.Observable;
import site.iurysouza.cinefilo.domain.entity.WatchMediaValue;
import site.iurysouza.cinefilo.model.data.movies.MoviesRepository;
import site.iurysouza.cinefilo.presentation.UseCase;

/**
 * Created by Iury Souza on 09/11/2016.
 */

public class MoviesUseCase implements UseCase {

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
        .map(WatchMediaValue::valueOf);
  }

  @Override
  public Observable<List<WatchMediaValue>> getNextPopular(int nextPage) {
    movieRepository.getMostPopular(nextPage, true);

    return movieRepository
        .getMostPopularSubject()
        .map(WatchMediaValue::valueOf);
  }

  @Override
  public Observable<List<WatchMediaValue>> getTopRated() {
    movieRepository.getTopRated(FIRST_PAGE, false);

    return movieRepository
        .getTopRatedSubject()
        .map(WatchMediaValue::valueOf);
  }

  @Override
  public Observable<List<WatchMediaValue>> getNextTopRated(int nextPage) {
      movieRepository.getTopRated(nextPage, true);
      return movieRepository
          .getTopRatedSubject()
          .map(WatchMediaValue::valueOf);
  }

  @Override
  public Observable<List<WatchMediaValue>> getFilteredMedia(int page, MediaFilter mediaFilter) {
    movieRepository.getFilteredBy(page, mediaFilter);
    return movieRepository
        .getFilteredMoviesSubject()
        .map(WatchMediaValue::valueOf);
  }

  @Override
  public Observable<List<WatchMediaValue>> getNowPlaying() {
    movieRepository.getNowPlaying(FIRST_PAGE, false);

    return movieRepository
        .getNowPlayingSubject()
        .map(WatchMediaValue::valueOf);
  }

  @Override public Observable<List<WatchMediaValue>> getMediaByGender(int gender) {
    movieRepository.getByGenre(gender);
    return movieRepository
        .getGenresSubject()
        .map(WatchMediaValue::valueOf);
  }

  @Override
  public Observable<List<WatchMediaValue>> getNextNowPlaying(int nextPage) {
    movieRepository.getNowPlaying(nextPage, true);

    return movieRepository
        .getNowPlayingSubject()
        .map(WatchMediaValue::valueOf);
  }
}
