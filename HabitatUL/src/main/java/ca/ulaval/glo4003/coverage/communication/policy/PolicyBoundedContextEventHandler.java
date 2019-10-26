package ca.ulaval.glo4003.coverage.communication.policy;

import ca.ulaval.glo4003.coverage.application.policy.PolicyAppService;
import ca.ulaval.glo4003.coverage.application.policy.event.PolicyCreationRequestedEvent;
import ca.ulaval.glo4003.mediator.Mediator;

public class PolicyBoundedContextEventHandler {
  private PolicyAppService policyAppService;

  public PolicyBoundedContextEventHandler(PolicyAppService policyAppService) {
    this.policyAppService = policyAppService;
  }

  public void register(Mediator mediator) {
    mediator.addListener(
        PolicyCreationRequestedEvent.class, this::handlePolicyCreationRequestedEvent);
  }

  void handlePolicyCreationRequestedEvent(PolicyCreationRequestedEvent event) {
    policyAppService.issuePolicy(
        event.getQuoteKey(),
        event.getCoveragePeriod(),
        event.getPurchaseDate(),
        event.getCoverageAmount());
  }
}
