package ca.ulaval.glo4003.insuring.domain.claim;

public class Claim {
  private ClaimId claimId;
  private ClaimStatus claimStatus;
  private SinisterType sinisterType;
  private LossDeclarations lossDeclarations;

  public Claim(
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
