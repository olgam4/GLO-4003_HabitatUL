package ca.ulaval.glo4003.administration.domain.user.error;

public class KeyAlreadyExistError extends UserError {
  private String key;

  public KeyAlreadyExistError(String key) {
    this.key = key;
  }
}
