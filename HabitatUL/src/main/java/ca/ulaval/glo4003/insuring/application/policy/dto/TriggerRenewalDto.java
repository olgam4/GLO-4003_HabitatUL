package ca.ulaval.glo4003.insuring.application.policy.dto;

import ca.ulaval.glo4003.shared.application.DataTransferObject;
import ca.ulaval.glo4003.shared.domain.money.Amount;

public class TriggerRenewalDto extends DataTransferObject {
  private final Amount personalPropertyCoverageAmount;

  public TriggerRenewalDto(Amount personalPropertyCoverageAmount) {
    this.personalPropertyCoverageAmount = personalPropertyCoverageAmount;
  }

  public Amount getPersonalPropertyCoverageAmount() {
    return personalPropertyCoverageAmount;
  }
}
