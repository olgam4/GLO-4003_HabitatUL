package ca.ulaval.glo4003.management.domain.user.exception;

public class KeyNotFoundException extends UserException {
  private String key;

  public KeyNotFoundException(String key) {
    this.key = key;
  }
}
