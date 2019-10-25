package ca.ulaval.glo4003.helper.claim;

import ca.ulaval.glo4003.coverage.domain.claim.*;

import static ca.ulaval.glo4003.helper.claim.ClaimGenerator.*;

public class ClaimBuilder {
  private static final ClaimId DEFAULT_CLAIM_ID = createClaimId();
  private static final ClaimStatus DEFAULT_CLAIM_STATUS = createClaimStatus();
  private static final SinisterType DEFAULT_SINISTER_TYPE = createSinisterType();
  private static final LossDeclarations DEFAULT_LOSS_DECLARATIONS = createLossDeclarations();

  private ClaimId claimId = DEFAULT_CLAIM_ID;
  private ClaimStatus claimStatus = DEFAULT_CLAIM_STATUS;
  private SinisterType sinisterType = DEFAULT_SINISTER_TYPE;
  private LossDeclarations lossDeclarations = DEFAULT_LOSS_DECLARATIONS;

  private ClaimBuilder() {}

  public static ClaimBuilder aClaim() {
    return new ClaimBuilder();
  }

  public ClaimBuilder withLossDeclarations(LossDeclarations lossDeclarations) {
    this.lossDeclarations = lossDeclarations;
    return this;
  }

  public Claim build() {
    return new Claim(claimId, claimStatus, sinisterType, lossDeclarations);
  }
}
