package ca.ulaval.glo4003.management.domain.user.exception;

public class KeyAlreadyExistError extends UserError {
  private String key;

  public KeyAlreadyExistError(String key) {
    this.key = key;
  }
}
