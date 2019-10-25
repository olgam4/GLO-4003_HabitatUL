package ca.ulaval.glo4003.underwriting.domain.quote.error;

public class QuoteDifferentAdditionalInsuredError extends QuoteError {
  private static final String ERROR = "QUOTE_DIFFERENT_ADDITIONAL_INSURED";
  private static final String MESSAGE =
      "sorry, named and additional insureds must be different people";

  public QuoteDifferentAdditionalInsuredError() {
    super(ERROR, MESSAGE);
  }
}
