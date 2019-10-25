package ca.ulaval.glo4003.underwriting.domain.quote.error;

public class QuoteStudentNamedInsuredError extends QuoteError {
  private static final String ERROR = "QUOTE_STUDENT_NAMED_INSURED";
  private static final String MESSAGE = "sorry, the named insured must have a university profile";

  public QuoteStudentNamedInsuredError() {
    super(ERROR, MESSAGE);
  }
}
