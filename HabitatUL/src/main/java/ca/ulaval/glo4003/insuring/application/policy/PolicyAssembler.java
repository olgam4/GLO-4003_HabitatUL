package ca.ulaval.glo4003.insuring.application.policy;

import ca.ulaval.glo4003.coverage.domain.form.BicycleEndorsementForm;
import ca.ulaval.glo4003.insuring.application.policy.dto.InsureBicycleDto;
import ca.ulaval.glo4003.insuring.application.policy.dto.PolicyModificationDto;
import ca.ulaval.glo4003.insuring.domain.policy.Policy;
import ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModification;

public class PolicyAssembler {
  public BicycleEndorsementForm from(InsureBicycleDto insureBicycleDto, Policy policy) {
    return new BicycleEndorsementForm(
        insureBicycleDto.getBicycle(), policy.getCoverageDetails(), policy.getPremiumDetails());
  }

  public PolicyModificationDto from(PolicyModification policyModification) {
    return new PolicyModificationDto(
        policyModification.getPolicyModificationId(),
        policyModification.getExpirationDate(),
        policyModification.getStatus(),
        policyModification.getPremiumAdjustment(),
        policyModification.getProposedCoverageDetails(),
        policyModification.getProposedPremiumDetails());
  }
}
