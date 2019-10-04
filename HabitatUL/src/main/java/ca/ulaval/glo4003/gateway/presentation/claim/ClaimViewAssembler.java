package ca.ulaval.glo4003.gateway.presentation.claim;

import ca.ulaval.glo4003.coverage.application.claim.dto.ClaimCreationDto;
import ca.ulaval.glo4003.coverage.application.claim.dto.ClaimDto;
import ca.ulaval.glo4003.gateway.presentation.claim.request.ClaimRequest;
import ca.ulaval.glo4003.gateway.presentation.claim.response.ClaimResponse;

public class ClaimViewAssembler {
  public ClaimCreationDto from(ClaimRequest claimRequest) {
    return new ClaimCreationDto(claimRequest.getSinisterType(), claimRequest.getLossDeclarations());
  }

  public ClaimResponse from(ClaimDto claimDto) {
    return new ClaimResponse(claimDto.getClaimId(), claimDto.getClaimStatus());
  }
}
