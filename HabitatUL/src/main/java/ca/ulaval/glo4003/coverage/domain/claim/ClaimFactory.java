package ca.ulaval.glo4003.coverage.domain.claim;

public class ClaimFactory {
  public Claim create(SinisterType sinisterType, LossDeclarations lossDeclarations) {
    ClaimId claimId = new ClaimId();
    return new Claim(claimId, ClaimStatus.RECEIVED, sinisterType, lossDeclarations);
  }
}
