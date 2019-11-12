package ca.ulaval.glo4003.insuring.domain.claim;

public class ClaimFactory {
  public Claim create(SinisterType sinisterType, LossDeclarations lossDeclarations) {
    ClaimId claimId = new ClaimId();
    return new Claim(claimId, ClaimStatus.RECEIVED, sinisterType, lossDeclarations);
  }
}
