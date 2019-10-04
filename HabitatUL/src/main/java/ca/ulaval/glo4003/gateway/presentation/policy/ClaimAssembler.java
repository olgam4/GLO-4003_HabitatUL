package ca.ulaval.glo4003.gateway.presentation.policy;

import ca.ulaval.glo4003.coverage.presentation.claim.ClaimDto;
import ca.ulaval.glo4003.gateway.presentation.policy.request.ClaimRequest;

public class ClaimAssembler {
  public ClaimDto from(ClaimRequest claimRequest) {
    return new ClaimDto(claimRequest.getSinisterType(), claimRequest.getLossDeclarations());
  }
}
