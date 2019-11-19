package ca.ulaval.glo4003.gateway.presentation.insuring.policy.request;

import ca.ulaval.glo4003.shared.domain.money.Amount;

public class TriggerRenewalRequest {
  private Amount personalProperty;

  private TriggerRenewalRequest() {}

  public TriggerRenewalRequest(Amount personalProperty) {
    this.personalProperty = personalProperty;
  }

  public Amount getPersonalProperty() {
    return personalProperty;
  }
}
