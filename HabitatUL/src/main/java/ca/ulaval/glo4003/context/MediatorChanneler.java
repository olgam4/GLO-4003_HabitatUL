package ca.ulaval.glo4003.context;

import ca.ulaval.glo4003.coverage.application.policy.event.PolicyCreationRequestedEvent;
import ca.ulaval.glo4003.coverage.domain.policy.PolicyIssuedEvent;
import ca.ulaval.glo4003.coverage.domain.policy.QuoteId;
import ca.ulaval.glo4003.management.application.user.event.PolicyAssociatedEvent;
import ca.ulaval.glo4003.management.application.user.event.QuotePaymentRequestedEvent;
import ca.ulaval.glo4003.mediator.Mediator;
import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.underwriting.domain.quote.QuotePurchasedEvent;

class MediatorChanneler {
  static void registerChannels(Mediator mediator) {
    mediator.addChannel(
        QuotePurchasedEvent.class,
        QuotePaymentRequestedEvent.class,
        event ->
            new QuotePaymentRequestedEvent(
                event.getQuoteId().getValue().toString(), new Money(event.getPrice().getValue())));

    mediator.addChannel(
        QuotePurchasedEvent.class,
        PolicyCreationRequestedEvent.class,
        event ->
            new PolicyCreationRequestedEvent(
                new QuoteId(event.getQuoteId().getValue().toString())));

    mediator.addChannel(
        PolicyIssuedEvent.class,
        PolicyAssociatedEvent.class,
        event ->
            new PolicyAssociatedEvent(
                event.getPolicyId().getValue().toString(),
                event.getQuoteId().getValue().toString()));
  }
}
