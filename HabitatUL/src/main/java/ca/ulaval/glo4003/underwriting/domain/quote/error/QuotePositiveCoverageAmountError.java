package ca.ulaval.glo4003.underwriting.domain.quote.error;

public class QuotePositiveCoverageAmountError extends QuoteError {
  private static final String ERROR = "QUOTE_POSITIVE_COVERAGE_AMOUNT";
  private static final String MESSAGE = "sorry, coverage amount must be greater than 0";

  public QuotePositiveCoverageAmountError() {
    super(ERROR, MESSAGE);
  }
}
