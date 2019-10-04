package ca.ulaval.glo4003.coverage.application.claim.dto;

import ca.ulaval.glo4003.coverage.domain.claim.ClaimId;
import ca.ulaval.glo4003.coverage.domain.claim.ClaimStatus;
import ca.ulaval.glo4003.coverage.domain.claim.LossDeclarations;
import ca.ulaval.glo4003.coverage.domain.claim.SinisterType;

public class ClaimDto {
  private ClaimId claimId;
  private ClaimStatus claimStatus;
  private SinisterType sinisterType;
  private LossDeclarations lossDeclarations;

  public ClaimDto(
      ClaimId claimId,
      ClaimStatus claimStatus,
      SinisterType sinisterType,
      LossDeclarations lossDeclarations) {
    this.claimId = claimId;
    this.claimStatus = claimStatus;
    this.sinisterType = sinisterType;
    this.lossDeclarations = lossDeclarations;
  }

  public ClaimId getClaimId() {
    return claimId;
  }

  public ClaimStatus getClaimStatus() {
    return claimStatus;
  }

  public SinisterType getSinisterType() {
    return sinisterType;
  }

  public LossDeclarations getLossDeclarations() {
    return lossDeclarations;
  }
}
