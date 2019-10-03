package ca.ulaval.glo4003.matcher.policy;

import ca.ulaval.glo4003.coverage.application.policy.dto.PolicyDto;
import ca.ulaval.glo4003.coverage.domain.policy.Policy;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class PolicyDtoMatcher extends TypeSafeMatcher<PolicyDto> {
  private final Policy policy;

  private PolicyDtoMatcher(final Policy policy) {
    this.policy = policy;
  }

  public static Matcher<PolicyDto> matchesPolicyDto(final Policy policy) {
    return new PolicyDtoMatcher(policy);
  }

  @Override
  public void describeTo(final Description description) {
    description.appendText(String.format("matches corresponding policy: %s", policy.toString()));
  }

  @Override
  public boolean matchesSafely(final PolicyDto policyDto) {
    // TODO
    return true;
  }
}
