package ca.ulaval.glo4003.gateway.presentation.policy;

import ca.ulaval.glo4003.gateway.presentation.coverage.CoverageViewAssembler;
import ca.ulaval.glo4003.gateway.presentation.policy.request.ClaimRequest;
import ca.ulaval.glo4003.gateway.presentation.policy.request.ModificationRequest;
import ca.ulaval.glo4003.gateway.presentation.policy.response.PoliciesResponse;
import ca.ulaval.glo4003.insuring.application.policy.dto.ModifyPolicyDto;
import ca.ulaval.glo4003.insuring.application.policy.dto.OpenClaimDto;

import java.util.List;

public class PolicyViewAssembler {
  private CoverageViewAssembler coverageViewAssembler;

  public PolicyViewAssembler() {
    this.coverageViewAssembler = new CoverageViewAssembler();
  }

  public PoliciesResponse from(List<String> policies) {
    return new PoliciesResponse(policies);
  }

  public ModifyPolicyDto from(ModificationRequest modificationRequest) {
    return new ModifyPolicyDto(
        coverageViewAssembler.fromBicycleRequest(modificationRequest.getBicycle()));
  }

  public OpenClaimDto from(ClaimRequest claimRequest) {
    return new OpenClaimDto(claimRequest.getSinisterType(), claimRequest.getLossDeclarations());
  }
}
