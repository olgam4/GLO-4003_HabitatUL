package ca.ulaval.glo4003.coverage.application.claim;

import ca.ulaval.glo4003.coverage.application.claim.dto.ClaimCreationDto;
import ca.ulaval.glo4003.coverage.application.claim.dto.ClaimDto;
import ca.ulaval.glo4003.coverage.domain.claim.Claim;
import ca.ulaval.glo4003.gateway.presentation.claim.request.ClaimRequest;

public class ClaimAssembler {
  public ClaimCreationDto from(ClaimRequest claimRequest) {
    return new ClaimCreationDto(claimRequest.getSinisterType(), claimRequest.getLossDeclarations());
  }

  public ClaimDto from(Claim claim) {
    return new ClaimDto(
        claim.getClaimId(),
        claim.getClaimStatus(),
        claim.getSinisterType(),
        claim.getLossDeclarations());
  }
}
