package ca.ulaval.glo4003.coverage.application.policy.dto;

import ca.ulaval.glo4003.coverage.domain.claim.LossDeclarations;
import ca.ulaval.glo4003.coverage.domain.claim.SinisterType;
import ca.ulaval.glo4003.shared.application.DataTransferObject;

public class OpenClaimDto extends DataTransferObject {
  private final SinisterType sinisterType;
  private final LossDeclarations lossDeclarations;

  public OpenClaimDto(SinisterType sinisterType, LossDeclarations lossDeclarations) {
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
