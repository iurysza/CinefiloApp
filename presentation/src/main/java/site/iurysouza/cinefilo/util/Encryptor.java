package site.iurysouza.cinefilo.util;

import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Base64;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.UnrecoverableEntryException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 * ______        _____                  _
 * |  ____|      / ____|                | |
 * | |__   _ __ | |     _ __ _   _ _ __ | |_ ___  _ __
 * |  __| | '_ \| |    | '__| | | | '_ \| __/ _ \| '__|
 * | |____| | | | |____| |  | |_| | |_) | || (_) | |
 * |______|_| |_|\_____|_|   \__, | .__/ \__\___/|_|
 * __/ | |
 * |___/|_|
 */
public class Encryptor {

  private static final String TRANSFORMATION = "AES/GCM/NoPadding";
  private static final String ANDROID_KEY_STORE = "AndroidKeyStore";
  private final String alias;
  private final String textToEncrypt;

  private byte[] encryption;
  private byte[] iv;

  public Encryptor(String alias, String textToEncrypt) {
    this.alias = alias;
    this.textToEncrypt = textToEncrypt;
  }

  public static Encryptor.EncryptorBuilder builder() {
    return new EncryptorBuilder();
  }

  public void encryptText()
      throws UnrecoverableEntryException, NoSuchAlgorithmException, KeyStoreException,
      NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, IOException,
      InvalidAlgorithmParameterException, SignatureException, BadPaddingException,
      IllegalBlockSizeException {

    final Cipher cipher = Cipher.getInstance(TRANSFORMATION);
    cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(alias));
    iv = cipher.getIV();
    encryption = cipher.doFinal(textToEncrypt.getBytes("UTF-8"));
  }

  private SecretKey getSecretKey(final String alias) throws NoSuchAlgorithmException,
      NoSuchProviderException, InvalidAlgorithmParameterException {

    final KeyGenerator keyGenerator = KeyGenerator
        .getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEY_STORE);

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      keyGenerator.init(new KeyGenParameterSpec.Builder(alias,
          KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
          .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
          .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
          .build());
    }

    return keyGenerator.generateKey();
  }

  public byte[] getEncryption() {
    return encryption;
  }

  public byte[] getIv() {
    return iv;
  }
  public String getAlias() {
    return alias;
  }

  public String getEncryptedText () {
    return (Base64.encodeToString(encryption, Base64.DEFAULT));
  }

  public static class EncryptorBuilder {

    private String alias;
    private String textToEncrypt;

    public EncryptorBuilder withAlias(String alias) {
      this.alias = alias;
      return this;
    }

    public EncryptorBuilder withTextToEncrypt(String textToEncrypt) {
      this.textToEncrypt = textToEncrypt;
      return this;
    }

    public Encryptor build() throws NoSuchPaddingException {
      return  new Encryptor(alias, textToEncrypt);
    }
  }
}