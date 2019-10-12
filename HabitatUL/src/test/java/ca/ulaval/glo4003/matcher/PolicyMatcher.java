package ca.ulaval.glo4003.matcher;

import ca.ulaval.glo4003.coverage.application.policy.event.PolicyCreationRequestedEvent;
import ca.ulaval.glo4003.coverage.domain.policy.Policy;
import org.hamcrest.Matcher;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.core.AllOf.allOf;

public class PolicyMatcher {
  public static Matcher<Policy> matchesPolicy(final Policy policy) {
    return allOf(
        hasProperty("policyId", equalTo(policy.getPolicyId())),
        hasProperty("quoteId", equalTo(policy.getQuoteId())));
  }

  public static Matcher<Policy> matchesPolicy(final PolicyCreationRequestedEvent event) {
    return hasProperty("quoteId", equalTo(event.getQuoteId()));
  }
}
