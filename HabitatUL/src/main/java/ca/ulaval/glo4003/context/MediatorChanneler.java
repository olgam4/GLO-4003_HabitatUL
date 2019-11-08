package ca.ulaval.glo4003.context;

import ca.ulaval.glo4003.administration.application.user.event.PolicyAssociatedEvent;
import ca.ulaval.glo4003.administration.application.user.event.QuotePaymentRequestedEvent;
import ca.ulaval.glo4003.coverage.application.policy.event.PolicyCreationRequestedEvent;
import ca.ulaval.glo4003.coverage.domain.policy.PolicyIssuedEvent;
import ca.ulaval.glo4003.mediator.Mediator;
import ca.ulaval.glo4003.underwriting.domain.quote.QuotePurchasedEvent;

class MediatorChanneler {
  static void registerChannels(Mediator mediator) {
    mediator.addChannel(
        QuotePurchasedEvent.class,
        QuotePaymentRequestedEvent.class,
        event ->
            new QuotePaymentRequestedEvent(
                event.getQuoteId().toRepresentation(), event.getPremium()));

    mediator.addChannel(
        QuotePurchasedEvent.class,
        PolicyCreationRequestedEvent.class,
        event ->
            new PolicyCreationRequestedEvent(
                event.getQuoteId().toRepresentation(),
                event.getEffectivePeriod(),
                event.getPurchaseDate(),
                event.getQuoteForm().getPersonalProperty().getCoverageAmount()));

    mediator.addChannel(
        PolicyIssuedEvent.class,
        PolicyAssociatedEvent.class,
        event ->
            new PolicyAssociatedEvent(event.getPolicyId().toRepresentation(), event.getQuoteKey()));
  }
}
