package ca.ulaval.glo4003.coverage.presentation.policy;

import ca.ulaval.glo4003.coverage.application.policy.dto.QuotePurchasedDto;
import ca.ulaval.glo4003.coverage.domain.policy.QuoteId;
import ca.ulaval.glo4003.mediator.event.Event;

public class PolicyEventMapper {
  public static QuotePurchasedDto mapToQuotePurchasedDto(Event event) {
    return new QuotePurchasedDto(new QuoteId((String) event.get("quoteId")));
  }
}
