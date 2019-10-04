package ca.ulaval.glo4003.matcher.policy;

import ca.ulaval.glo4003.coverage.application.policy.dto.QuotePurchasedDto;
import ca.ulaval.glo4003.coverage.domain.policy.Policy;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.mockito.hamcrest.MockitoHamcrest;

public class PolicyMatcher extends TypeSafeMatcher<Policy> {
  QuotePurchasedDto quotePurchasedDto;
  private Policy policy;

  private PolicyMatcher(final Policy policy) {
    this.policy = policy;
  }

  private PolicyMatcher(final QuotePurchasedDto quotePurchasedDto) {
    this.quotePurchasedDto = quotePurchasedDto;
  }

  public static Matcher<Policy> matchesPolicy(final Policy policy) {
    return new PolicyMatcher(policy);
  }

  public static Policy mockitoPolicyMatcher(final QuotePurchasedDto quotePurchasedDto) {
    return MockitoHamcrest.argThat(matchesPolicy(quotePurchasedDto));
  }

  public static Matcher<Policy> matchesPolicy(final QuotePurchasedDto quotePurchasedDto) {
    return new PolicyMatcher(quotePurchasedDto) {
      @Override
      public boolean matchesSafely(final Policy policy) {
        // TODO
        return true;
      }
    };
  }

  @Override
  public void describeTo(Description description) {
    description.appendText(String.format("matches corresponding policy: %s", policy.toString()));
  }

  @Override
  protected boolean matchesSafely(Policy policy) {
    // TODO
    return true;
  }
}
