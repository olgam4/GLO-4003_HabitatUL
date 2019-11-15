package ca.ulaval.glo4003.helper.claim;

import ca.ulaval.glo4003.gateway.presentation.policy.request.ClaimRequest;
import ca.ulaval.glo4003.helper.shared.EnumSampler;
import ca.ulaval.glo4003.insuring.application.claim.ClaimAssembler;
import ca.ulaval.glo4003.insuring.application.claim.dto.ClaimDto;
import ca.ulaval.glo4003.insuring.domain.claim.Claim;
import ca.ulaval.glo4003.insuring.domain.claim.ClaimId;
import ca.ulaval.glo4003.insuring.domain.claim.ClaimStatus;
import ca.ulaval.glo4003.insuring.domain.claim.SinisterType;

import static ca.ulaval.glo4003.helper.claim.LossDeclarationsGenerator.createLossDeclarations;

public class ClaimGenerator {
  private ClaimGenerator() {}

  public static ClaimRequest createClaimRequest() {
    return new ClaimRequest(createSinisterType(), createLossDeclarations());
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
}
