package ca.ulaval.glo4003.gateway.presentation.insuring.claim;

import ca.ulaval.glo4003.gateway.presentation.insuring.claim.response.ClaimResponse;
import ca.ulaval.glo4003.insuring.application.claim.dto.ClaimDto;

public class ClaimViewAssembler {
  public ClaimResponse from(ClaimDto claimDto) {
    return new ClaimResponse(claimDto.getClaimId(), claimDto.getClaimStatus());
  }
}
