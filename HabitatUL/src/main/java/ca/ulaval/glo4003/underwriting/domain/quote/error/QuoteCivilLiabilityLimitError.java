package ca.ulaval.glo4003.underwriting.domain.quote.error;

public class QuoteCivilLiabilityLimitError extends QuoteError {
  private static final String ERROR = "QUOTE_CIVIL_LIABILITY_LIMIT";
  private static final String MESSAGE =
      "sorry, the requested civil liability limit is not allowed based on your risk exposure";

  public QuoteCivilLiabilityLimitError() {
    super(ERROR, MESSAGE);
  }
}
