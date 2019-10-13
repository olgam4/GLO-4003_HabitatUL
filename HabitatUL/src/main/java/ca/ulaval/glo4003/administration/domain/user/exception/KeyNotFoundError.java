package ca.ulaval.glo4003.administration.domain.user.exception;

public class KeyNotFoundError extends UserError {
  private String key;

  public KeyNotFoundError(String key) {
    this.key = key;
  }
}
