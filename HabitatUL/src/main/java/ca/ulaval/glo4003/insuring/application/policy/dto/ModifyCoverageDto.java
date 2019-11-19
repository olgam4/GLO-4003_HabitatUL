package ca.ulaval.glo4003.insuring.application.policy.dto;

import ca.ulaval.glo4003.coverage.domain.form.civilliability.CivilLiabilityLimit;
import ca.ulaval.glo4003.shared.application.DataTransferObject;
import ca.ulaval.glo4003.shared.domain.money.Amount;

public class ModifyCoverageDto extends DataTransferObject {
  private final Amount personalPropertyCoverageAmount;
  private final CivilLiabilityLimit civilLiabilityLimit;

  public ModifyCoverageDto(
      Amount personalPropertyCoverageAmount, CivilLiabilityLimit civilLiabilityLimit) {
    this.personalPropertyCoverageAmount = personalPropertyCoverageAmount;
    this.civilLiabilityLimit = civilLiabilityLimit;
  }

  public Amount getPersonalPropertyCoverageAmount() {
    return personalPropertyCoverageAmount;
  }

  public CivilLiabilityLimit getCivilLiabilityLimit() {
    return civilLiabilityLimit;
  }
}
