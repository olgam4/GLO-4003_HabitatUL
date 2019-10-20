package ca.ulaval.glo4003.underwriting.domain.quote.error;

public class QuoteCivilLiabilityError extends QuoteError {
  private static final String ERROR = "QUOTE_CIVIL_LIABILITY";
  private static final String MESSAGE =
      "sorry, the requested civil liability coverage amount is "
          + "not allowed based on your risk exposure";

  public String getError() {
    return ERROR;
  }

  public String getMessage() {
    return String.format(MESSAGE);
  }
}
