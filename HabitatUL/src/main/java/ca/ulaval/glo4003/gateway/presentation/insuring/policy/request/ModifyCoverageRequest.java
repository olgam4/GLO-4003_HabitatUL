package ca.ulaval.glo4003.gateway.presentation.insuring.policy.request;

import ca.ulaval.glo4003.coverage.domain.form.civilliability.CivilLiabilityLimit;
import ca.ulaval.glo4003.shared.domain.money.Amount;

public class ModifyCoverageRequest {
  private Amount personalProperty;
  private CivilLiabilityLimit civilLiability;

  private ModifyCoverageRequest() {}

  public ModifyCoverageRequest(Amount personalProperty, CivilLiabilityLimit civilLiability) {
    this.personalProperty = personalProperty;
    this.civilLiability = civilLiability;
  }

  public Amount getPersonalProperty() {
    return personalProperty;
  }

  public CivilLiabilityLimit getCivilLiability() {
    return civilLiability;
  }
}
