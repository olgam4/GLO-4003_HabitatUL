package ca.ulaval.glo4003.gateway.presentation.claim.request;

import ca.ulaval.glo4003.coverage.domain.claim.LossDeclarations;
import ca.ulaval.glo4003.coverage.domain.claim.SinisterType;

import javax.validation.constraints.NotNull;

public class ClaimRequest {
  @NotNull private SinisterType sinisterType;
  @NotNull private LossDeclarations lossDeclarations;

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
