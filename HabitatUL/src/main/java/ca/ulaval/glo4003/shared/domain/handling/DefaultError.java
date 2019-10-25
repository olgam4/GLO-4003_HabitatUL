package ca.ulaval.glo4003.shared.domain.handling;

public class DefaultError extends BaseError {
  private static final String ERROR = "ERROR";
  private static final String MESSAGE = "sorry, something wrong happened";

  public DefaultError() {
    super(ERROR, MESSAGE);
  }
}
