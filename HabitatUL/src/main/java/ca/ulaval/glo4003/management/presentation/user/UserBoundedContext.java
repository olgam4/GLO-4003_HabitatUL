package ca.ulaval.glo4003.management.presentation.user;

import ca.ulaval.glo4003.management.application.user.UserAppService;
import ca.ulaval.glo4003.mediator.BoundedContext;
import ca.ulaval.glo4003.mediator.event.Event;
import ca.ulaval.glo4003.mediator.event.EventChannel;
import ca.ulaval.glo4003.shared.domain.Money;

import java.math.BigDecimal;

public class UserBoundedContext implements BoundedContext {
  static final String POLICY_ISSUED_EVENT_TYPE = "policyIssuedEvent";
  static final String QUOTE_PURCHASED_EVENT_TYPE = "quotePurchasedEvent";

  private UserAppService userAppService;

  public UserBoundedContext(UserAppService userAppService) {
    this.userAppService = userAppService;
  }

  @Override
  public void process(Event event) {
    if (event.getChannel().equals(EventChannel.POLICIES)) processPolicyEvent(event);
    if (event.getChannel().equals(EventChannel.QUOTES)) processQuoteEvent(event);
  }

  private void processPolicyEvent(Event event) {
    if (event.getType().equals(POLICY_ISSUED_EVENT_TYPE)) {
      String policyKey = (String) event.get("policyId");
      String quoteKey = (String) event.get("quoteId");
      userAppService.associatePolicy(quoteKey, policyKey);
    }
  }

  private void processQuoteEvent(Event event) {
    if (event.getType().equals(QUOTE_PURCHASED_EVENT_TYPE)) {
      String quoteKey = (String) event.get("quoteId");
      Money price = new Money(BigDecimal.valueOf((Double) event.get("premium")));
      userAppService.processQuotePayment(quoteKey, price);
    }
  }
}
