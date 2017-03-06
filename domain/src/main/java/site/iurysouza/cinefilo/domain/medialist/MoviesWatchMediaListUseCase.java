package site.iurysouza.cinefilo.domain.medialist;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableEntryException;
import java.util.List;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.inject.Inject;
import rx.Observable;
import site.iurysouza.cinefilo.domain.Decryptor;

/**
 * Created by Iury Souza on 09/11/2016.
 */

public class MoviesWatchMediaListUseCase implements WatchMediaListUseCase {

  private static final int FIRST_PAGE = 1;
  private WatchMediaRepository movieRepository;
  private String apiKey="";

  @Inject
  public MoviesWatchMediaListUseCase(WatchMediaRepository movieRepository, Decryptor decryptor) {
    this.movieRepository = movieRepository;
    try {
      this.apiKey = decryptor.decryptData();
    } catch (
        UnrecoverableEntryException |
            NoSuchAlgorithmException |
            KeyStoreException |
            NoSuchPaddingException |
            NoSuchProviderException |
            BadPaddingException |
            IllegalBlockSizeException |
            InvalidAlgorithmParameterException |
            IOException |
            InvalidKeyException e
        ) {
      e.printStackTrace();
    }
  }

  @Override
  public Observable<List<WatchMedia>> getMostPopular() {
    return movieRepository.getMostPopular(FIRST_PAGE, false, apiKey);
  }

  @Override
  public Observable<List<WatchMedia>> getNextPopular(int nextPage) {
    return movieRepository.getMostPopular(nextPage, true, apiKey);
  }

  @Override
  public Observable<List<WatchMedia>> getTopRated() {
    return movieRepository.getTopRated(FIRST_PAGE, false, apiKey);
  }

  @Override
  public Observable<List<WatchMedia>> getNextTopRated(int nextPage) {
    return movieRepository.getTopRated(nextPage, true, apiKey);
  }

  @Override
  public Observable<List<WatchMedia>> getFilteredMedia(int page, MediaFilter mediaFilter) {
    return movieRepository
        .getFilteredBy(page, mediaFilter,apiKey);
  }

  @Override
  public Observable<List<WatchMedia>> getNowPlaying() {
    return movieRepository.getNowPlaying(FIRST_PAGE, false, apiKey);
  }

  @Override public Observable<List<WatchMedia>> getMediaByGender(int gender) {
    return movieRepository.getByGenre(gender);
  }

  @Override
  public Observable<List<WatchMedia>> getNextNowPlaying(int nextPage) {
    return movieRepository.getNowPlaying(nextPage, true, apiKey);
  }
}
