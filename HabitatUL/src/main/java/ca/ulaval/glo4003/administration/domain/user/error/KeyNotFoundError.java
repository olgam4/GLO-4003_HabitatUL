package ca.ulaval.glo4003.administration.domain.user.error;

public class KeyNotFoundError extends UserError {
  private String key;

  public KeyNotFoundError(String key) {
    this.key = key;
  }
}
