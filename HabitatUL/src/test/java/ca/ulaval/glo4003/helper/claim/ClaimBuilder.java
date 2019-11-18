package ca.ulaval.glo4003.helper.claim;

import ca.ulaval.glo4003.insuring.domain.claim.*;

import static ca.ulaval.glo4003.helper.claim.ClaimGenerator.*;
import static ca.ulaval.glo4003.helper.claim.LossDeclarationsGenerator.createLossDeclarations;

public class ClaimBuilder {
  private final ClaimId DEFAULT_CLAIM_ID = createClaimId();
  private final ClaimStatus DEFAULT_CLAIM_STATUS = createClaimStatus();
  private final SinisterType DEFAULT_SINISTER_TYPE = createSinisterType();
  private final LossDeclarations DEFAULT_LOSS_DECLARATIONS = createLossDeclarations();

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
