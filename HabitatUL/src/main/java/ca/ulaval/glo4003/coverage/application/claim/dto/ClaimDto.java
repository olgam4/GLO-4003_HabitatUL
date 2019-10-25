package ca.ulaval.glo4003.coverage.application.claim.dto;

import ca.ulaval.glo4003.coverage.domain.claim.ClaimId;
import ca.ulaval.glo4003.coverage.domain.claim.ClaimStatus;
import ca.ulaval.glo4003.coverage.domain.claim.LossDeclarations;
import ca.ulaval.glo4003.coverage.domain.claim.SinisterType;
import ca.ulaval.glo4003.shared.application.DataTransferObject;

public class ClaimDto extends DataTransferObject {
  private final ClaimId claimId;
  private final ClaimStatus claimStatus;
  private final SinisterType sinisterType;
  private final LossDeclarations lossDeclarations;

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
