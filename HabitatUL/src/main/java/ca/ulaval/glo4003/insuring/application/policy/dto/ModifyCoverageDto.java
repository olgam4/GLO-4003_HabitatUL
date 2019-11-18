package ca.ulaval.glo4003.insuring.application.policy.dto;

import ca.ulaval.glo4003.coverage.domain.form.civilliability.CivilLiabilityLimit;
import ca.ulaval.glo4003.shared.application.DataTransferObject;
import ca.ulaval.glo4003.shared.domain.money.Amount;

public class ModifyCoverageDto extends DataTransferObject {
  private final Amount personalProperty;
  private final CivilLiabilityLimit civilLiability;

  public ModifyCoverageDto(Amount personalProperty, CivilLiabilityLimit civilLiability) {
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
