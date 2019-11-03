package ca.ulaval.glo4003.coverage.application.claim;

import ca.ulaval.glo4003.coverage.application.claim.dto.ClaimDto;
import ca.ulaval.glo4003.coverage.domain.claim.Claim;

public class ClaimAssembler {
  public ClaimDto from(Claim claim) {
    return new ClaimDto(
        claim.getClaimId(),
        claim.getClaimStatus(),
        claim.getSinisterType(),
        claim.getLossDeclarations());
  }
}
