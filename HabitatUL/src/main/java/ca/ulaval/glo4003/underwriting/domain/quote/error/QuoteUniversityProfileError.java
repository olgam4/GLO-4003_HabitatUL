package ca.ulaval.glo4003.underwriting.domain.quote.error;

public class QuoteUniversityProfileError extends QuoteError {
  private static final String ERROR = "QUOTE_UNIVERSITY_PROFILE";
  private static final String MESSAGE =
      "sorry, we have not been able to confirm your university profile";

  public QuoteUniversityProfileError() {
    super(ERROR, MESSAGE);
  }
}
