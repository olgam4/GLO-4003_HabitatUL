package ca.ulaval.glo4003.gateway.presentation.policy;

import ca.ulaval.glo4003.coverage.application.policy.dto.OpenClaimDto;
import ca.ulaval.glo4003.gateway.presentation.policy.request.ClaimRequest;
import ca.ulaval.glo4003.gateway.presentation.policy.response.PoliciesResponse;

import java.util.List;

public class PolicyViewAssembler {
  public PoliciesResponse from(List<String> policies) {
    return new PoliciesResponse(policies);
  }

  public OpenClaimDto from(ClaimRequest claimRequest) {
    return new OpenClaimDto(claimRequest.getSinisterType(), claimRequest.getLossDeclarations());
  }
}
