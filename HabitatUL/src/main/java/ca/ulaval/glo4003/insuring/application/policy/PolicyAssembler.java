package ca.ulaval.glo4003.insuring.application.policy;

import ca.ulaval.glo4003.coverage.domain.form.BicycleEndorsementForm;
import ca.ulaval.glo4003.coverage.domain.form.CoverageModificationForm;
import ca.ulaval.glo4003.coverage.domain.form.CoverageRenewalForm;
import ca.ulaval.glo4003.insuring.application.policy.dto.*;
import ca.ulaval.glo4003.insuring.domain.policy.Policy;
import ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModification;
import ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyRenewal;

public class PolicyAssembler {
  public BicycleEndorsementForm from(InsureBicycleDto insureBicycleDto, Policy policy) {
    return new BicycleEndorsementForm(
        insureBicycleDto.getBicycle(), policy.getCoverageDetails(), policy.getPremiumDetails());
  }

  public CoverageModificationForm from(ModifyCoverageDto modifyCoverageDto, Policy policy) {
    return new CoverageModificationForm(
        modifyCoverageDto.getPersonalPropertyCoverageAmount(),
        modifyCoverageDto.getCivilLiabilityLimit(),
        policy.getPolicyInformation().getBuilding().getNumberOfUnits(),
        policy.getCoverageDetails(),
        policy.getPremiumDetails());
  }

  public PolicyModificationDto from(PolicyModification policyModification) {
    return new PolicyModificationDto(
        policyModification.getPolicyModificationId(),
        policyModification.getStatus(),
        policyModification.getExpirationDate(),
        policyModification.getProposedPremiumAdjustment(),
        policyModification.getProposedCoverageDetails(),
        policyModification.getProposedPremiumDetails());
  }

  public CoverageRenewalForm from(TriggerRenewalDto triggerRenewalDto, Policy policy) {
    return new CoverageRenewalForm(
        triggerRenewalDto.getPersonalPropertyCoverageAmount(),
        policy.getCoverageDetails(),
        policy.getPremiumDetails());
  }

  public PolicyRenewalDto from(PolicyRenewal policyRenewal) {
    return new PolicyRenewalDto(
        policyRenewal.getPolicyRenewalId(),
        policyRenewal.getStatus(),
        policyRenewal.getCoveragePeriod(),
        policyRenewal.getProposedCoverageDetails(),
        policyRenewal.getProposedPremiumDetails());
  }

  public PolicyDto from(Policy policy) {
    return new PolicyDto(
        policy.getPolicyId(),
        policy.getCoveragePeriod(),
        policy.getCoverageDetails(),
        policy.getPremiumDetails());
  }
}
