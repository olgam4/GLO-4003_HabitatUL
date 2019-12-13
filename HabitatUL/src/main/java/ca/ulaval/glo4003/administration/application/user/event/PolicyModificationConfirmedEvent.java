package ca.ulaval.glo4003.administration.application.user.event;

import ca.ulaval.glo4003.mediator.Event;
import ca.ulaval.glo4003.shared.domain.money.Money;

public class PolicyModificationConfirmedEvent extends Event {
  private final String policyKey;
  private final Money premium;

  public PolicyModificationConfirmedEvent(String policyKey, Money premium) {
    this.policyKey = policyKey;
    this.premium = premium;
  }

  public String getPolicyKey() {
    return policyKey;
  }

  public Money getPremium() {
    return premium;
  }
}
