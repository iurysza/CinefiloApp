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

public class SeriesWatchMediaListUseCase implements WatchMediaListUseCase {

  private static final int FIRST_PAGE = 1;
  private WatchMediaRepository seriesRepository;
  private String apiKey="";

  @Inject
  public SeriesWatchMediaListUseCase(WatchMediaRepository seriesRepository, Decryptor decryptor) {
    this.seriesRepository = seriesRepository;
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

  @Override public Observable<List<WatchMedia>> getMediaByGender(int gender) {
    return null;
  }

  @Override
  public Observable<List<WatchMedia>> getMostPopular() {

    return seriesRepository.getMostPopular(FIRST_PAGE, false, apiKey);
  }

  @Override
  public Observable<List<WatchMedia>> getNextPopular(int nextPage) {
    return seriesRepository.getMostPopular(nextPage, false, apiKey);
  }

  @Override
  public Observable<List<WatchMedia>> getTopRated() {
    return seriesRepository.getTopRated(FIRST_PAGE, false, apiKey);
  }

  @Override public Observable<List<WatchMedia>> getNextTopRated(int nextPage) {
    return seriesRepository.getTopRated(nextPage, true, apiKey);
  }

  @Override
  public Observable<List<WatchMedia>> getFilteredMedia(int page, MediaFilter mediaFilter) {
    return null;
  }

  @Override
  public Observable<List<WatchMedia>> getNowPlaying() {
    return seriesRepository.getNowPlaying(FIRST_PAGE, false, apiKey);
  }

  @Override
  public Observable<List<WatchMedia>> getNextNowPlaying(int nextPage) {
    return seriesRepository.getNowPlaying(nextPage, true, apiKey);
  }
}
