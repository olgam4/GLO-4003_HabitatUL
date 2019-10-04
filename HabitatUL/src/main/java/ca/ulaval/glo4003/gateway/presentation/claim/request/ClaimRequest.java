package ca.ulaval.glo4003.gateway.presentation.claim.request;

import ca.ulaval.glo4003.coverage.domain.claim.LossDeclarations;
import ca.ulaval.glo4003.coverage.domain.claim.SinisterType;

public class ClaimRequest {
  private SinisterType sinisterType;
  private LossDeclarations lossDeclarations;

  private ClaimRequest() {}

  public ClaimRequest(SinisterType sinisterType, LossDeclarations lossDeclarations) {
    this.sinisterType = sinisterType;
    this.lossDeclarations = lossDeclarations;
  }

  public SinisterType getSinisterType() {
    return sinisterType;
  }

  public LossDeclarations getLossDeclarations() {
    return lossDeclarations;
  }
}
