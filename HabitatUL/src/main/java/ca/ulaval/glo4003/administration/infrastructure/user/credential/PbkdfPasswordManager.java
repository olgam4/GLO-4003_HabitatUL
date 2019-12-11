package ca.ulaval.glo4003.administration.infrastructure.user.credential;

import ca.ulaval.glo4003.administration.domain.user.credential.PasswordManager;
import ca.ulaval.glo4003.administration.domain.user.credential.exception.InvalidCredentialsException;
import ca.ulaval.glo4003.administration.domain.user.credential.exception.InvalidPasswordException;
import org.apache.commons.codec.digest.DigestUtils;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class PbkdfPasswordManager implements PasswordManager {
  private static final String ALGORITHM = "PBKDF2WithHmacSHA512";
  private static final int ITERATION_COUNT = 65536;
  private static final int KEY_LENGTH = 512;
  private static final int SALT_LENGTH = KEY_LENGTH;

  private PbkdfPasswordStorage storage;
  private SecureRandom secureRandom;
  private SecretKeyFactory keyFactory;

  public PbkdfPasswordManager(PbkdfPasswordStorage storage) throws NoSuchAlgorithmException {
    this.storage = storage;
    this.secureRandom = new SecureRandom();
    this.keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
  }

  @Override
  public void registerPassword(String userKey, String password) throws InvalidPasswordException {
    if (password == null) throw new InvalidPasswordException(null, "NULL PASSWORD");

    String salt = createSalt();
    String hash = hashPasswordSafely(password, salt);
    Secret secret = new Secret(salt, hash);
    storage.store(userKey, secret);
  }

  private String createSalt() {
    byte[] salt = new byte[SALT_LENGTH];
    secureRandom.nextBytes(salt);
    return DigestUtils.sha256Hex(salt);
  }

  private String hashPasswordSafely(String password, String salt) throws InvalidPasswordException {
    try {
      return hashPassword(salt, password);
    } catch (InvalidKeySpecException e) {
      throw new InvalidPasswordException(null, "SOMETHING WENT WRONG");
    }
  }

  @Override
  public void validatePassword(String userKey, String password) throws InvalidCredentialsException {
    Secret secret = storage.fetch(userKey);
    if (secret == null || password == null) throw new InvalidCredentialsException();
    if (!isValidPassword(password, secret)) throw new InvalidCredentialsException();
  }

  private boolean isValidPassword(String password, Secret secret) {
    try {
      String currentHash = hashPassword(secret.getSalt(), password);
      return secret.getHash().equals(currentHash);
    } catch (InvalidKeySpecException e) {
      return false;
    }
  }

  private String hashPassword(String salt, String password) throws InvalidKeySpecException {
    KeySpec keySpec =
        new PBEKeySpec(password.toCharArray(), salt.getBytes(), ITERATION_COUNT, KEY_LENGTH);
    return generateSecretSafely(keySpec);
  }

  private String generateSecretSafely(KeySpec keySpec) throws InvalidKeySpecException {
    return new String(keyFactory.generateSecret(keySpec).getEncoded());
  }
}
