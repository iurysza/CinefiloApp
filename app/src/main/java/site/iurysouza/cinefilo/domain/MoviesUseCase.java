package site.iurysouza.cinefilo.domain;

import android.support.annotation.NonNull;
import java.util.List;
import javax.inject.Inject;
import rx.Observable;
import rx.functions.Func1;
import site.iurysouza.cinefilo.domain.entity.WatchMediaValue;
import site.iurysouza.cinefilo.model.data.entity.WatchMedia;
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
    return movieRepository
        .getFilteredBy(page, mediaFilter)
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

  @NonNull private Func1<List<WatchMedia>, List<WatchMedia>> takeOnePageOnly() {
    return watchMedias -> {
      if (watchMedias.size() > 20) {
        return watchMedias.subList(20, watchMedias.size() - 1);
      } else {
        return watchMedias;
      }
    };
  }
}
