package ca.ulaval.glo4003.context;

import ca.ulaval.glo4003.administration.application.user.event.PolicyAssociatedEvent;
import ca.ulaval.glo4003.administration.application.user.event.QuotePaymentRequestedEvent;
import ca.ulaval.glo4003.insuring.application.policy.event.PolicyPurchasedEvent;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyIssuedEvent;
import ca.ulaval.glo4003.mediator.Mediator;
import ca.ulaval.glo4003.underwriting.domain.quote.QuotePurchasedEvent;

class MediatorChanneler {
  static void registerChannels(Mediator mediator) {
    mediator.addChannel(
        QuotePurchasedEvent.class,
        QuotePaymentRequestedEvent.class,
        event ->
            new QuotePaymentRequestedEvent(
                event.getQuoteId().toRepresentation(),
                event.getPremiumDetails().computeTotalPremium()));

    mediator.addChannel(
        QuotePurchasedEvent.class,
        PolicyPurchasedEvent.class,
        event ->
            new PolicyPurchasedEvent(
                event.getQuoteId().toRepresentation(),
                event.getEffectivePeriod(),
                event.getPurchaseDate(),
                event.getCoverageDetails(),
                event.getPremiumDetails()));

    mediator.addChannel(
        PolicyIssuedEvent.class,
        PolicyAssociatedEvent.class,
        event ->
            new PolicyAssociatedEvent(event.getPolicyId().toRepresentation(), event.getQuoteKey()));
  }
}
