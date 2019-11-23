package ca.ulaval.glo4003.helper.claim;

import ca.ulaval.glo4003.gateway.presentation.insuring.claim.request.ProvideAuthorityNumberRequest;
import ca.ulaval.glo4003.gateway.presentation.insuring.policy.request.ClaimRequest;
import ca.ulaval.glo4003.helper.shared.EnumSampler;
import ca.ulaval.glo4003.insuring.application.claim.ClaimAssembler;
import ca.ulaval.glo4003.insuring.application.claim.dto.ClaimDto;
import ca.ulaval.glo4003.insuring.domain.claim.Claim;
import ca.ulaval.glo4003.insuring.domain.claim.ClaimId;
import ca.ulaval.glo4003.insuring.domain.claim.ClaimStatus;
import ca.ulaval.glo4003.insuring.domain.claim.SinisterType;

import static ca.ulaval.glo4003.helper.claim.LossDeclarationsGenerator.createLossDeclarations;
import static ca.ulaval.glo4003.helper.shared.AuthorityGenerator.createAuthorityNumber;
import static ca.ulaval.glo4003.helper.shared.TemporalGenerator.createDate;

public class ClaimGenerator {
  private ClaimGenerator() {}

  public static ProvideAuthorityNumberRequest createAuthorityNumberRequest() {
    return new ProvideAuthorityNumberRequest(createAuthorityNumber());
  }

  public static ClaimRequest createClaimRequest() {
    return new ClaimRequest(createSinisterType(), createLossDeclarations());
  }

  public static ClaimDto createClaimDto() {
    return new ClaimAssembler().from(createClaim());
  }

  public static Claim createClaim() {
    return new Claim(
        createClaimId(),
        createDate(),
        createClaimStatus(),
        createAuthorityNumber(),
        createSinisterType(),
        createLossDeclarations());
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
