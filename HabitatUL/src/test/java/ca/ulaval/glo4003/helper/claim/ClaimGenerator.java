package ca.ulaval.glo4003.helper.claim;

import ca.ulaval.glo4003.coverage.application.claim.ClaimAssembler;
import ca.ulaval.glo4003.coverage.application.claim.dto.ClaimCreationDto;
import ca.ulaval.glo4003.coverage.application.claim.dto.ClaimDto;
import ca.ulaval.glo4003.coverage.domain.claim.*;
import ca.ulaval.glo4003.helper.EnumSampler;

import java.util.HashMap;

public class ClaimGenerator {
  private ClaimGenerator() {}

  public static ClaimCreationDto createClaimCreationDto() {
    return new ClaimCreationDto(createSinisterType(), createLossDeclarations());
  }

  public static ClaimDto createClaimDto() {
    return new ClaimAssembler().from(createClaim());
  }

  public static Claim createClaim() {
    return new Claim(
        createClaimId(), createClaimStatus(), createSinisterType(), createLossDeclarations());
  }

  public static ClaimId createClaimId() {
    return new ClaimId();
  }

  public static ClaimStatus createClaimStatus() {
    return EnumSampler.sample(ClaimStatus.class);
  }

  public static SinisterType createSinisterType() {
    return EnumSampler.sample(SinisterType.class);
  }

  public static LossDeclarations createLossDeclarations() {
    // TODO: feed values
    return new LossDeclarations(new HashMap<>());
  }

  public static LossCategory createLossCategory() {
    return EnumSampler.sample(LossCategory.class);
  }
}
