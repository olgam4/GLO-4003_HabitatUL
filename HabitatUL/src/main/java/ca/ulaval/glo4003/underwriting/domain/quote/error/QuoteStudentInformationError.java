package ca.ulaval.glo4003.underwriting.domain.quote.error;

public class QuoteStudentInformationError extends QuoteError {
  private static final String ERROR = "QUOTE_STUDENT_INFORMATION";
  private static final String MESSAGE =
      "sorry, we have not been able to confirm your student information";

  public String getError() {
    return ERROR;
  }

  public String getMessage() {
    return String.format(MESSAGE);
  }
}
