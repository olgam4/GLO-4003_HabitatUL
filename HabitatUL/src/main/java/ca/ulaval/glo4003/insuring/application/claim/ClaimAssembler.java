package ca.ulaval.glo4003.insuring.application.claim;

import ca.ulaval.glo4003.insuring.application.claim.dto.ClaimDto;
import ca.ulaval.glo4003.insuring.domain.claim.Claim;

public class ClaimAssembler {
  public ClaimDto from(Claim claim) {
    return new ClaimDto(
        claim.getClaimId(),
        claim.getClaimStatus(),
        claim.getSinisterType(),
        claim.getLossDeclarations());
  }
}
