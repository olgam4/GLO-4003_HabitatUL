package ca.ulaval.glo4003.management.domain.user.exception;

public class KeyAlreadyExistException extends UserException {
  private String key;

  public KeyAlreadyExistException(String key) {
    this.key = key;
  }
}
