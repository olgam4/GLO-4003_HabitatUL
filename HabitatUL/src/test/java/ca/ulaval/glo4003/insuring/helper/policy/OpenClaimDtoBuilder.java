package ca.ulaval.glo4003.insuring.helper.policy;

import ca.ulaval.glo4003.insuring.application.policy.dto.OpenClaimDto;
import ca.ulaval.glo4003.insuring.domain.claim.LossDeclarations;
import ca.ulaval.glo4003.insuring.domain.claim.SinisterType;

import static ca.ulaval.glo4003.insuring.helper.claim.ClaimGenerator.createSinisterType;
import static ca.ulaval.glo4003.insuring.helper.claim.LossDeclarationsGenerator.createLossDeclarations;

public class OpenClaimDtoBuilder {
  private final SinisterType DEFAULT_SINISTER_TYPE = createSinisterType();
  private final LossDeclarations DEFAULT_LOSS_DECLARATIONS = createLossDeclarations();

  private SinisterType sinisterType = DEFAULT_SINISTER_TYPE;
  private LossDeclarations lossDeclarations = DEFAULT_LOSS_DECLARATIONS;

  private OpenClaimDtoBuilder() {}

  public static OpenClaimDtoBuilder anOpenClaimDto() {
    return new OpenClaimDtoBuilder();
  }

  public OpenClaimDtoBuilder withLossDeclarations(LossDeclarations lossDeclarations) {
    this.lossDeclarations = lossDeclarations;
    return this;
  }

  public OpenClaimDto build() {
    return new OpenClaimDto(sinisterType, lossDeclarations);
  }
}
