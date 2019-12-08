package ca.ulaval.glo4003.gateway.presentation.insuring.policy;

import ca.ulaval.glo4003.gateway.presentation.coverage.CoverageViewAssembler;
import ca.ulaval.glo4003.gateway.presentation.insuring.policy.request.ClaimRequest;
import ca.ulaval.glo4003.gateway.presentation.insuring.policy.request.InsureBicycleRequest;
import ca.ulaval.glo4003.gateway.presentation.insuring.policy.request.ModifyCoverageRequest;
import ca.ulaval.glo4003.gateway.presentation.insuring.policy.request.TriggerRenewalRequest;
import ca.ulaval.glo4003.gateway.presentation.insuring.policy.response.*;
import ca.ulaval.glo4003.insuring.application.policy.dto.*;
import ca.ulaval.glo4003.insuring.domain.claim.ClaimId;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyId;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ca.ulaval.glo4003.gateway.presentation.insuring.policy.response.ConfigureMaximumLossRatioResponse.ExceedingClaimsByPolicyResponse;

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

  public ConfigureMaximumLossRatioResponse from(Map<PolicyId, List<ClaimId>> exceedingClaims) {
    return new ConfigureMaximumLossRatioResponse(
        exceedingClaims.entrySet().stream()
            .map(x -> new ExceedingClaimsByPolicyResponse(x.getKey(), x.getValue()))
            .collect(Collectors.toList()));
  }
}
