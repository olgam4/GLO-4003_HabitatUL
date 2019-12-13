package ca.ulaval.glo4003.administration.communication.user;

import ca.ulaval.glo4003.administration.application.user.UserAppService;
import ca.ulaval.glo4003.administration.application.user.event.PolicyAssociatedEvent;
import ca.ulaval.glo4003.administration.application.user.event.PolicyModificationConfirmedEvent;
import ca.ulaval.glo4003.administration.application.user.event.PolicyRenewalConfirmedEvent;
import ca.ulaval.glo4003.administration.application.user.event.QuotePurchaseConfirmedEvent;
import ca.ulaval.glo4003.mediator.Mediator;

public class UserBoundedContextEventHandler {
  private UserAppService userAppService;

  public UserBoundedContextEventHandler(UserAppService userAppService) {
    this.userAppService = userAppService;
  }

  public void register(Mediator mediator) {
    mediator.addListener(
        QuotePurchaseConfirmedEvent.class, this::handleQuotePurchaseConfirmedEvent);
    mediator.addListener(
        PolicyModificationConfirmedEvent.class, this::handlePolicyModificationConfirmedEvent);
    mediator.addListener(
        PolicyRenewalConfirmedEvent.class, this::handlePolicyRenewalConfirmedEvent);
    mediator.addListener(PolicyAssociatedEvent.class, this::handlePolicyAssociatedEvent);
  }

  void handleQuotePurchaseConfirmedEvent(QuotePurchaseConfirmedEvent event) {
    userAppService.processQuotePayment(event.getQuoteKey(), event.getPremium());
  }

  void handlePolicyModificationConfirmedEvent(PolicyModificationConfirmedEvent event) {
    userAppService.processPolicyModificationPayment(event.getPolicyKey(), event.getPremium());
  }

  void handlePolicyRenewalConfirmedEvent(PolicyRenewalConfirmedEvent event) {
    userAppService.processPolicyRenewalPayment(event.getPolicyKey(), event.getPremium());
  }

  void handlePolicyAssociatedEvent(PolicyAssociatedEvent event) {
    userAppService.associatePolicy(event.getQuoteKey(), event.getPolicyKey());
  }
}
