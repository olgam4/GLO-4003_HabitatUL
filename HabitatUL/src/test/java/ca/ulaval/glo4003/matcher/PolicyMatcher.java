package ca.ulaval.glo4003.matcher;

import ca.ulaval.glo4003.coverage.application.policy.dto.QuotePurchasedDto;
import ca.ulaval.glo4003.coverage.domain.policy.Policy;
import ca.ulaval.glo4003.coverage.domain.policy.QuoteId;
import ca.ulaval.glo4003.mediator.event.Event;
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

  public static Matcher<Policy> matchesPolicy(final QuotePurchasedDto quotePurchasedDto) {
    return hasProperty("quoteId", equalTo(quotePurchasedDto.getQuoteId()));
  }

  public static Matcher<QuotePurchasedDto> matchesQuotePurchasedDto(final Event event) {
    QuoteId expectedQuoteId = new QuoteId((String) event.get("quoteId"));
    return hasProperty("quoteId", equalTo(expectedQuoteId));
  }
}
