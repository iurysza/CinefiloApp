package site.iurysouza.cinefilo.domain.watchmedialist;

import java.util.List;
import javax.inject.Inject;
import rx.Observable;

/**
 * Created by Iury Souza on 09/11/2016.
 */

public class MoviesWatchMediaListUseCase implements WatchMediaListUseCase {

  private static final int FIRST_PAGE = 1;
  private WatchMediaRepository movieRepository;

  @Inject
  public MoviesWatchMediaListUseCase(WatchMediaRepository movieRepository) {
    this.movieRepository = movieRepository;
  }

  @Override
  public Observable<List<WatchMedia>> getMostPopular() {
    return movieRepository.getMostPopular(FIRST_PAGE, false);
  }

  @Override
  public Observable<List<WatchMedia>> getNextPopular(int nextPage) {
    return movieRepository.getMostPopular(nextPage, true);
  }

  @Override
  public Observable<List<WatchMedia>> getTopRated() {
    return movieRepository.getTopRated(FIRST_PAGE, false);
  }

  @Override
  public Observable<List<WatchMedia>> getNextTopRated(int nextPage) {
    return movieRepository.getTopRated(nextPage, true);
  }

  @Override
  public Observable<List<WatchMedia>> getFilteredMedia(int page, MediaFilter mediaFilter) {
    return movieRepository
        .getFilteredBy(page, mediaFilter);
  }

  @Override
  public Observable<List<WatchMedia>> getNowPlaying() {
    return movieRepository.getNowPlaying(FIRST_PAGE, false);
  }

  @Override public Observable<List<WatchMedia>> getMediaByGender(int gender) {
    return movieRepository.getByGenre(gender);
  }

  @Override
  public Observable<List<WatchMedia>> getNextNowPlaying(int nextPage) {
    return movieRepository.getNowPlaying(nextPage, true);
  }
}
