package ca.ulaval.glo4003.management.domain.user.exception;

public class QuoteKeyNotFoundException extends UserException {
  private String quoteKey;

  public QuoteKeyNotFoundException(String quoteKey) {
    this.quoteKey = quoteKey;
  }

  public String getQuoteKey() {
    return quoteKey;
  }
}
