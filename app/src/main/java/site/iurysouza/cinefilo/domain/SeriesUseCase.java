package site.iurysouza.cinefilo.domain;

import java.util.List;
import javax.inject.Inject;
import rx.Observable;
import site.iurysouza.cinefilo.domain.entity.WatchMediaValue;
import site.iurysouza.cinefilo.model.data.series.SeriesRepositoryI;
import site.iurysouza.cinefilo.presentation.UseCase;

/**
 * Created by Iury Souza on 09/11/2016.
 */

public class SeriesUseCase implements UseCase {

  private SeriesRepositoryI seriesRepository;
  private static final int FIRST_PAGE = 1;

  @Inject
  public SeriesUseCase(SeriesRepositoryI seriesRepository) {
    this.seriesRepository = seriesRepository;
    //seriesRepository.getGenreList();
  }

  @Override public Observable<List<WatchMediaValue>> getMediaByGender(int gender) {
    return null;
  }

  @Override
  public Observable<List<WatchMediaValue>> getMostPopular() {
    seriesRepository.getMostPopular(FIRST_PAGE, false);

    return seriesRepository
        .getMostPopularSubject()
        .map(WatchMediaValue::valueOf);
  }

  @Override
  public Observable<List<WatchMediaValue>> getNextPopular(int nextPage) {
    seriesRepository.getMostPopular(nextPage, false);

    return seriesRepository
        .getMostPopularSubject()
        .map(WatchMediaValue::valueOf);
  }

  @Override
  public Observable<List<WatchMediaValue>> getTopRated() {
    seriesRepository.getTopRated(FIRST_PAGE, false);

    return seriesRepository
        .getTopRatedSubject()
        .map(WatchMediaValue::valueOf);
  }

  @Override public Observable<List<WatchMediaValue>> getNextTopRated(int nextPage) {
    seriesRepository.getTopRated(nextPage, true);

    return seriesRepository
        .getTopRatedSubject()
        .map(WatchMediaValue::valueOf);
  }

  @Override
  public Observable<List<WatchMediaValue>> getFilteredMedia(int page, MediaFilter mediaFilter) {
    return null;
  }

  @Override
  public Observable<List<WatchMediaValue>> getNowPlaying() {
    seriesRepository.getNowPlaying(FIRST_PAGE, false);

    return seriesRepository
        .getNowPlayingSubject()
        .map(WatchMediaValue::valueOf);
  }

  @Override
  public Observable<List<WatchMediaValue>> getNextNowPlaying(int nextPage) {
    seriesRepository.getNowPlaying(nextPage, true);

    return seriesRepository
        .getNowPlayingSubject()
        .map(WatchMediaValue::valueOf);
  }
}
