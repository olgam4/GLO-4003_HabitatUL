package ca.ulaval.glo4003.gateway.presentation.insuring.policy;

import ca.ulaval.glo4003.gateway.presentation.coverage.CoverageViewAssembler;
import ca.ulaval.glo4003.gateway.presentation.insuring.policy.request.ClaimRequest;
import ca.ulaval.glo4003.gateway.presentation.insuring.policy.request.InsureBicycleRequest;
import ca.ulaval.glo4003.gateway.presentation.insuring.policy.request.ModifyCoverageRequest;
import ca.ulaval.glo4003.gateway.presentation.insuring.policy.request.TriggerRenewalRequest;
import ca.ulaval.glo4003.gateway.presentation.insuring.policy.response.PoliciesResponse;
import ca.ulaval.glo4003.gateway.presentation.insuring.policy.response.PolicyModificationResponse;
import ca.ulaval.glo4003.gateway.presentation.insuring.policy.response.PolicyRenewalResponse;
import ca.ulaval.glo4003.gateway.presentation.insuring.policy.response.PolicyResponse;
import ca.ulaval.glo4003.insuring.application.policy.dto.*;

import java.util.List;

public class PolicyViewAssembler {
  private CoverageViewAssembler coverageViewAssembler;

  public PolicyViewAssembler() {
    this.coverageViewAssembler = new CoverageViewAssembler();
  }

  public PoliciesResponse from(List<String> policies) {
    return new PoliciesResponse(policies);
  }

  public InsureBicycleDto from(InsureBicycleRequest insureBicycleRequest) {
    return new InsureBicycleDto(
        coverageViewAssembler.fromBicycleRequest(insureBicycleRequest.getBicycle()));
  }

  public ModifyCoverageDto from(ModifyCoverageRequest modifyCoverageRequest) {
    return new ModifyCoverageDto(
        modifyCoverageRequest.getPersonalProperty(), modifyCoverageRequest.getCivilLiability());
  }

  public PolicyModificationResponse from(PolicyModificationDto policyModificationDto) {
    return new PolicyModificationResponse(
        policyModificationDto.getPolicyModificationId(),
        policyModificationDto.getStatus(),
        policyModificationDto.getExpirationDate(),
        policyModificationDto.getProposedPremiumAdjustment(),
        policyModificationDto.getProposedCoverageDetails(),
        policyModificationDto.getProposedPremiumDetails());
  }

  public TriggerRenewalDto from(TriggerRenewalRequest triggerRenewalRequest) {
    return new TriggerRenewalDto(triggerRenewalRequest.getPersonalProperty());
  }

  public PolicyRenewalResponse from(PolicyRenewalDto policyRenewalDto) {
    return new PolicyRenewalResponse(
        policyRenewalDto.getPolicyRenewalId(),
        policyRenewalDto.getStatus(),
        policyRenewalDto.getCoveragePeriod(),
        policyRenewalDto.getProposedPremiumDetails().computeTotalPremium(),
        policyRenewalDto.getProposedCoverageDetails(),
        policyRenewalDto.getProposedPremiumDetails());
  }

  public PolicyResponse from(PolicyDto policyDto) {
    return new PolicyResponse(
        policyDto.getPolicyId(),
        policyDto.getCoveragePeriod(),
        policyDto.getPremiumDetails().computeTotalPremium(),
        policyDto.getCoverageDetails(),
        policyDto.getPremiumDetails());
  }

  public OpenClaimDto from(ClaimRequest claimRequest) {
    return new OpenClaimDto(claimRequest.getSinisterType(), claimRequest.getLossDeclarations());
  }
}
