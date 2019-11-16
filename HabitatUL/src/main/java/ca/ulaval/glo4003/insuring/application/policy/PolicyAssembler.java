package ca.ulaval.glo4003.insuring.application.policy;

import ca.ulaval.glo4003.coverage.domain.form.BicycleEndorsementForm;
import ca.ulaval.glo4003.insuring.application.policy.dto.InsureBicycleDto;
import ca.ulaval.glo4003.insuring.domain.policy.Policy;

public class PolicyAssembler {
  public BicycleEndorsementForm from(InsureBicycleDto insureBicycleDto, Policy policy) {
    return new BicycleEndorsementForm(
        insureBicycleDto.getBicycle(),
        policy.getCurrentCoverageDetails(),
        policy.getCurrentPremiumDetails());
  }
}
