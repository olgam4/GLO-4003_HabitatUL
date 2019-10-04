package ca.ulaval.glo4003.coverage.application.claim.dto;

import ca.ulaval.glo4003.coverage.domain.claim.LossDeclarations;
import ca.ulaval.glo4003.coverage.domain.claim.SinisterType;

public class ClaimCreationDto {
  private SinisterType sinisterType;
  private LossDeclarations lossDeclarations;

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
