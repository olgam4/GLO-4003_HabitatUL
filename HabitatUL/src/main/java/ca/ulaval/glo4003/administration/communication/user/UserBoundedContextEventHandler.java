package ca.ulaval.glo4003.administration.communication.user;

import ca.ulaval.glo4003.administration.application.user.UserAppService;
import ca.ulaval.glo4003.administration.application.user.event.PolicyAssociatedEvent;
import ca.ulaval.glo4003.administration.application.user.event.QuotePaymentRequestedEvent;
import ca.ulaval.glo4003.mediator.Mediator;

public class UserBoundedContextEventHandler {
  private UserAppService userAppService;

  public UserBoundedContextEventHandler(UserAppService userAppService) {
    this.userAppService = userAppService;
  }

  public void register(Mediator mediator) {
    mediator.addListener(QuotePaymentRequestedEvent.class, this::handleQuotePaymentRequestedEvent);
    mediator.addListener(PolicyAssociatedEvent.class, this::handlePolicyAssociatedEvent);
  }

  void handleQuotePaymentRequestedEvent(QuotePaymentRequestedEvent event) {
    userAppService.processQuotePayment(event.getQuoteKey(), event.getTotal());
  }

  void handlePolicyAssociatedEvent(PolicyAssociatedEvent event) {
    userAppService.associatePolicy(event.getQuoteKey(), event.getPolicyKey());
  }
}
