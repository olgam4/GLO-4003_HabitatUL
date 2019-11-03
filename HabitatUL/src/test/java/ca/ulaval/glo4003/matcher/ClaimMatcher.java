package ca.ulaval.glo4003.matcher;

import ca.ulaval.glo4003.coverage.application.claim.dto.ClaimCreationDto;
import ca.ulaval.glo4003.gateway.presentation.policy.request.ClaimRequest;
import org.hamcrest.Matcher;

import static org.hamcrest.Matchers.*;

public class ClaimMatcher {
  private ClaimMatcher() {}

  public static Matcher<ClaimCreationDto> matchesClaimCreationDto(final ClaimRequest claimRequest) {
    return allOf(
        hasProperty("sinisterType", equalTo(claimRequest.getSinisterType())),
        hasProperty("lossDeclarations", equalTo(claimRequest.getLossDeclarations())));
  }
}
