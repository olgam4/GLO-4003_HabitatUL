package ca.ulaval.glo4003.insuring.communication.policy;

import ca.ulaval.glo4003.insuring.application.policy.PolicyAppService;
import ca.ulaval.glo4003.insuring.application.policy.event.PolicyPurchasedEvent;
import ca.ulaval.glo4003.mediator.Mediator;

public class PolicyBoundedContextEventHandler {
  private PolicyAppService policyAppService;

  public PolicyBoundedContextEventHandler(PolicyAppService policyAppService) {
    this.policyAppService = policyAppService;
  }

  public void register(Mediator mediator) {
    mediator.addListener(PolicyPurchasedEvent.class, this::handlePolicyPurchasedEvent);
  }

  void handlePolicyPurchasedEvent(PolicyPurchasedEvent event) {
    policyAppService.issuePolicy(event);
  }
}
