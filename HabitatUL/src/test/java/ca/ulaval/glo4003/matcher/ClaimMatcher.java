package ca.ulaval.glo4003.matcher;

import ca.ulaval.glo4003.gateway.presentation.insuring.policy.request.ClaimRequest;
import ca.ulaval.glo4003.insuring.application.policy.dto.OpenClaimDto;
import ca.ulaval.glo4003.insuring.domain.claim.Claim;
import org.hamcrest.Matcher;

import static org.hamcrest.Matchers.*;

public class ClaimMatcher {
  private ClaimMatcher() {}

  public static Matcher<OpenClaimDto> matchesOpenClaimDto(final ClaimRequest claimRequest) {
    return allOf(
        hasProperty("sinisterType", equalTo(claimRequest.getSinisterType())),
        hasProperty("lossDeclarations", equalTo(claimRequest.getLossDeclarations())));
  }

  public static Matcher<Claim> matchesClaim(final Claim claim) {
    return allOf(
        hasProperty("claimId", equalTo(claim.getClaimId())),
        hasProperty("claimStatus", equalTo(claim.getClaimStatus())),
        hasProperty("sinisterType", equalTo(claim.getSinisterType())),
        hasProperty("lossDeclarations", equalTo(claim.getLossDeclarations())));
  }
}
