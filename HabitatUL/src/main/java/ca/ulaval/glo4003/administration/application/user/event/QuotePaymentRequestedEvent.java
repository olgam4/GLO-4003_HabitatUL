package ca.ulaval.glo4003.administration.application.user.event;

import ca.ulaval.glo4003.mediator.Event;
import ca.ulaval.glo4003.shared.domain.money.Money;

public class QuotePaymentRequestedEvent extends Event {
  private final String quoteKey;
  private final Money total;

  public QuotePaymentRequestedEvent(String quoteKey, Money total) {
    this.quoteKey = quoteKey;
    this.total = total;
  }

  public String getQuoteKey() {
    return quoteKey;
  }

  public Money getTotal() {
    return total;
  }
}
