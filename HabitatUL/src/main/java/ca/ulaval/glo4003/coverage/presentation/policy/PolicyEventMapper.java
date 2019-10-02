package ca.ulaval.glo4003.coverage.presentation.policy;

import ca.ulaval.glo4003.coverage.application.policy.dto.QuotePurchasedDto;
import ca.ulaval.glo4003.mediator.Event;

public class PolicyEventMapper {
  public static QuotePurchasedDto mapToQuotePurchasedDto(Event event) {
    return new QuotePurchasedDto();
  }
}
