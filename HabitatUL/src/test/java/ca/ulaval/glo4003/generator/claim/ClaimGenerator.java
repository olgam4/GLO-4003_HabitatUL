package ca.ulaval.glo4003.generator.claim;

import ca.ulaval.glo4003.coverage.application.claim.ClaimAssembler;
import ca.ulaval.glo4003.coverage.application.claim.dto.ClaimDto;
import ca.ulaval.glo4003.coverage.domain.claim.*;
import ca.ulaval.glo4003.generator.EnumSampler;

import java.util.HashMap;

public class ClaimGenerator {
  private ClaimGenerator() {}

  public static ClaimDto createClaimDto() {
    return new ClaimAssembler().from(createClaim());
  }

  private static Claim createClaim() {
    return new Claim(
        createClaimId(),
        EnumSampler.sample(ClaimStatus.class),
        EnumSampler.sample(SinisterType.class),
        createLossDeclaration());
  }

  private static LossDeclarations createLossDeclaration() {
    return new LossDeclarations(new HashMap<>());
  }

  private static ClaimId createClaimId() {
    return new ClaimId();
  }
}
