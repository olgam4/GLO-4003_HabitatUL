package ca.ulaval.glo4003.coverage.presentation.policy;

import ca.ulaval.glo4003.coverage.application.policy.PolicyAppService;
import ca.ulaval.glo4003.coverage.application.policy.dto.QuotePurchasedDto;
import ca.ulaval.glo4003.mediator.BoundedContext;
import ca.ulaval.glo4003.mediator.Event;
import ca.ulaval.glo4003.mediator.EventChannel;

public class PolicyBoundedContext implements BoundedContext {
  static final String QUOTE_PURCHASED_EVENT_TYPE = "quotePurchasedEvent";

  private PolicyAppService policyAppService;

  public PolicyBoundedContext(PolicyAppService policyAppService) {
    this.policyAppService = policyAppService;
  }

  @Override
  public void process(Event event) {
    if (event.getChannel().equals(EventChannel.QUOTES)) processQuoteEvent(event);
  }

  private void processQuoteEvent(Event event) {
    if (event.getType().equals(QUOTE_PURCHASED_EVENT_TYPE)) {
      QuotePurchasedDto quotePurchasedDto = PolicyEventMapper.mapToQuotePurchasedDto(event);
      policyAppService.createPolicy(quotePurchasedDto);
    }
  }
}
