package site.iurysouza.cinefilo.di.modules;

import android.content.Context;
import dagger.Module;
import dagger.Provides;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.inject.Singleton;
import site.iurysouza.cinefilo.BuildConfig;
import site.iurysouza.cinefilo.domain.Decryptor;
import site.iurysouza.cinefilo.util.Constants;
import site.iurysouza.cinefilo.util.Encryptor;

/**
 * Created by Iury Souza on 11/10/2016.
 */
@Module
public class AppModule {
  protected Context context;

  public AppModule(Context context) {
    this.context = context;
  }

  @Provides
  @Singleton
  public Context provideContext() {
    return context;
  }

  @Singleton
  @Provides public Encryptor providesEncryptor() {
    try {
      return new Encryptor.EncryptorBuilder()
          .withAlias(Constants.MOVIE_DB_API.KEY_ALIAS)
          .withTextToEncrypt(BuildConfig.API_KEY)
          .build();
    } catch (NoSuchPaddingException e) {
      e.printStackTrace();
      return null;
    }
  }

  @Singleton
  @Provides public Decryptor providesDecryptor(Encryptor encryptor) {
    try {
      encryptor.encryptText();
      return new Decryptor(encryptor.getAlias(), encryptor.getEncryption(), encryptor.getIv());
    } catch (
        CertificateException |
            NoSuchAlgorithmException | KeyStoreException |
            IOException | SignatureException |
            UnrecoverableEntryException | InvalidKeyException |
            BadPaddingException | NoSuchProviderException |
            IllegalBlockSizeException | NoSuchPaddingException |
            InvalidAlgorithmParameterException e
        ) {
      e.printStackTrace();
    }
    return null;
  }
}