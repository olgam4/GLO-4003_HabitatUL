package ca.ulaval.glo4003.gateway.presentation.insuring.policy;

import ca.ulaval.glo4003.gateway.presentation.coverage.CoverageViewAssembler;
import ca.ulaval.glo4003.gateway.presentation.insuring.policy.request.ClaimRequest;
import ca.ulaval.glo4003.gateway.presentation.insuring.policy.request.InsureBicycleRequest;
import ca.ulaval.glo4003.gateway.presentation.insuring.policy.request.ModifyPolicyRequest;
import ca.ulaval.glo4003.gateway.presentation.insuring.policy.response.PoliciesResponse;
import ca.ulaval.glo4003.gateway.presentation.insuring.policy.response.PolicyModificationResponse;
import ca.ulaval.glo4003.insuring.application.policy.dto.InsureBicycleDto;
import ca.ulaval.glo4003.insuring.application.policy.dto.ModifyCoverageDto;
import ca.ulaval.glo4003.insuring.application.policy.dto.OpenClaimDto;
import ca.ulaval.glo4003.insuring.application.policy.dto.PolicyModificationDto;

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

  public ModifyCoverageDto from(ModifyPolicyRequest modifyPolicyRequest) {
    return new ModifyCoverageDto();
  }

  public PolicyModificationResponse from(PolicyModificationDto policyModificationDto) {
    return new PolicyModificationResponse(
        policyModificationDto.getPolicyModificationId(),
        policyModificationDto.getExpirationDate(),
        policyModificationDto.getStatus(),
        policyModificationDto.getPremiumAdjustment(),
        policyModificationDto.getProposedCoverageDetails(),
        policyModificationDto.getProposedPremiumDetails());
  }

  public OpenClaimDto from(ClaimRequest claimRequest) {
    return new OpenClaimDto(claimRequest.getSinisterType(), claimRequest.getLossDeclarations());
  }
}
