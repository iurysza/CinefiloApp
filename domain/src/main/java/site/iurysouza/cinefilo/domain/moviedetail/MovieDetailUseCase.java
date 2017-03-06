package site.iurysouza.cinefilo.domain.moviedetail;

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
import site.iurysouza.cinefilo.domain.medialist.WatchMedia;

/**
 * Created by Iury Souza on 31/01/2017.
 */
public class MovieDetailUseCase implements IMovieDetailUseCase {
  private static final int FIRST_PAGE = 1;
  private  String apiKey ="";
  private IMovieDetailRepository detailRepository;

  @Inject
  public MovieDetailUseCase(IMovieDetailRepository detailRepository, Decryptor decryptor) {
    this.detailRepository = detailRepository;
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
  public Observable<MovieDetail> getMovieById(int movieId) {
    return detailRepository.getMovieById(movieId,apiKey);
  }

  @Override
  public Observable<List<WatchMedia>> geMoviesSimilarTo(int movieId) {
    return detailRepository
        .getMoviesSimilarTo(movieId, FIRST_PAGE,apiKey);
  }
}
