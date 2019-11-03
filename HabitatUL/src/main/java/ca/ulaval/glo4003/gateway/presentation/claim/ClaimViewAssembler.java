package ca.ulaval.glo4003.gateway.presentation.claim;

import ca.ulaval.glo4003.coverage.application.claim.dto.ClaimDto;
import ca.ulaval.glo4003.gateway.presentation.claim.response.ClaimResponse;

public class ClaimViewAssembler {
  public ClaimResponse from(ClaimDto claimDto) {
    return new ClaimResponse(claimDto.getClaimId(), claimDto.getClaimStatus());
  }
}
