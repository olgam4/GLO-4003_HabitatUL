package ca.ulaval.glo4003.shared.application.concurrency.error;

public class ConcurrentAccessError extends ConcurrencyError {
  private static final String ERROR = "CONCURRENT_ACCESS";
  private static final String MESSAGE = "sorry, too many requests received at once";

  public ConcurrentAccessError() {
    super(ERROR, MESSAGE);
  }
}
