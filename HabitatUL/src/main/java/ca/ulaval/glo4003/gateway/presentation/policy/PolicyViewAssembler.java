package ca.ulaval.glo4003.gateway.presentation.policy;

import ca.ulaval.glo4003.gateway.presentation.policy.request.ClaimRequest;
import ca.ulaval.glo4003.gateway.presentation.policy.response.PoliciesResponse;
import ca.ulaval.glo4003.insuring.application.policy.dto.OpenClaimDto;

import java.util.List;

public class PolicyViewAssembler {
  public PoliciesResponse from(List<String> policies) {
    return new PoliciesResponse(policies);
  }

  public OpenClaimDto from(ClaimRequest claimRequest) {
    return new OpenClaimDto(claimRequest.getSinisterType(), claimRequest.getLossDeclarations());
  }
}
