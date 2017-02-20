package site.iurysouza.cinefilo.domain.watchmedialist;

import java.util.List;
import javax.inject.Inject;
import rx.Observable;

/**
 * Created by Iury Souza on 09/11/2016.
 */

public class SeriesWatchMediaListUseCase implements WatchMediaListUseCase {

  private static final int FIRST_PAGE = 1;
  private WatchMediaRepository seriesRepository;

  @Inject
  public SeriesWatchMediaListUseCase(WatchMediaRepository seriesRepository) {
    this.seriesRepository = seriesRepository;
  }

  @Override public Observable<List<WatchMedia>> getMediaByGender(int gender) {
    return null;
  }

  @Override
  public Observable<List<WatchMedia>> getMostPopular() {

    return seriesRepository.getMostPopular(FIRST_PAGE, false);
  }

  @Override
  public Observable<List<WatchMedia>> getNextPopular(int nextPage) {
    return seriesRepository.getMostPopular(nextPage, false);
  }

  @Override
  public Observable<List<WatchMedia>> getTopRated() {
    return seriesRepository.getTopRated(FIRST_PAGE, false);
  }

  @Override public Observable<List<WatchMedia>> getNextTopRated(int nextPage) {
    return seriesRepository.getTopRated(nextPage, true);
  }

  @Override
  public Observable<List<WatchMedia>> getFilteredMedia(int page, MediaFilter mediaFilter) {
    return null;
  }

  @Override
  public Observable<List<WatchMedia>> getNowPlaying() {
    return seriesRepository.getNowPlaying(FIRST_PAGE, false);
  }

  @Override
  public Observable<List<WatchMedia>> getNextNowPlaying(int nextPage) {
    return seriesRepository.getNowPlaying(nextPage, true);
  }
}
