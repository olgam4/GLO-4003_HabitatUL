package ca.ulaval.glo4003.insuring.domain.policy;

import ca.ulaval.glo4003.mediator.Event;
import ca.ulaval.glo4003.shared.domain.money.Money;

public class PolicyModifiedEvent extends Event {
  private final PolicyId policyId;
  private final Money premium;

  public PolicyModifiedEvent(PolicyId policyId, Money premium) {
    this.policyId = policyId;
    this.premium = premium;
  }

  public PolicyId getPolicyId() {
    return policyId;
  }

  public Money getPremium() {
    return premium;
  }
}
