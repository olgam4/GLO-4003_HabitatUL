package ca.ulaval.glo4003.administration.application.user.event;

import ca.ulaval.glo4003.mediator.Event;
import ca.ulaval.glo4003.shared.domain.money.Money;

public class QuotePurchaseConfirmedEvent extends Event {
  private final String quoteKey;
  private final Money premium;

  public QuotePurchaseConfirmedEvent(String quoteKey, Money premium) {
    this.quoteKey = quoteKey;
    this.premium = premium;
  }

  public String getQuoteKey() {
    return quoteKey;
  }

  public Money getPremium() {
    return premium;
  }
}
