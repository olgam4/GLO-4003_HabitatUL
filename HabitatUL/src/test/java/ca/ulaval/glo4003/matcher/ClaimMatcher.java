package ca.ulaval.glo4003.matcher;

import ca.ulaval.glo4003.coverage.application.policy.dto.OpenClaimDto;
import ca.ulaval.glo4003.coverage.domain.claim.Claim;
import ca.ulaval.glo4003.gateway.presentation.policy.request.ClaimRequest;
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
