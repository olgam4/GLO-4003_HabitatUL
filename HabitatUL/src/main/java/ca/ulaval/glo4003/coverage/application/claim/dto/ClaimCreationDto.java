package ca.ulaval.glo4003.coverage.application.claim.dto;

import ca.ulaval.glo4003.coverage.domain.claim.LossDeclarations;
import ca.ulaval.glo4003.coverage.domain.claim.SinisterType;
import ca.ulaval.glo4003.shared.application.DataTransferObject;

public class ClaimCreationDto extends DataTransferObject {
  private final SinisterType sinisterType;
  private final LossDeclarations lossDeclarations;

  public ClaimCreationDto(SinisterType sinisterType, LossDeclarations lossDeclarations) {
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
