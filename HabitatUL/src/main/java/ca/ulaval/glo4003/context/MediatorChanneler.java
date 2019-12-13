package ca.ulaval.glo4003.context;

import ca.ulaval.glo4003.administration.application.user.event.PolicyAssociatedEvent;
import ca.ulaval.glo4003.administration.application.user.event.PolicyModificationConfirmedEvent;
import ca.ulaval.glo4003.administration.application.user.event.PolicyRenewalConfirmedEvent;
import ca.ulaval.glo4003.administration.application.user.event.QuotePurchaseConfirmedEvent;
import ca.ulaval.glo4003.coverage.domain.form.QuoteForm;
import ca.ulaval.glo4003.insuring.application.policy.event.PolicyPurchasedEvent;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyInformation;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyIssuedEvent;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyModifiedEvent;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyRenewedEvent;
import ca.ulaval.glo4003.mediator.Mediator;
import ca.ulaval.glo4003.underwriting.domain.quote.QuotePurchasedEvent;

class MediatorChanneler {
  static void registerChannels(Mediator mediator) {
    mediator.addChannel(
        QuotePurchasedEvent.class,
        QuotePurchaseConfirmedEvent.class,
        event ->
            new QuotePurchaseConfirmedEvent(
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
                from(event.getQuoteForm()),
                event.getCoverageDetails(),
                event.getPremiumDetails()));

    mediator.addChannel(
        PolicyModifiedEvent.class,
        PolicyModificationConfirmedEvent.class,
        event ->
            new PolicyModificationConfirmedEvent(
                event.getPolicyId().toRepresentation(), event.getPremium()));

    mediator.addChannel(
        PolicyIssuedEvent.class,
        PolicyAssociatedEvent.class,
        event ->
            new PolicyAssociatedEvent(event.getPolicyId().toRepresentation(), event.getQuoteKey()));

    mediator.addChannel(
        PolicyRenewedEvent.class,
        PolicyRenewalConfirmedEvent.class,
        event ->
            new PolicyRenewalConfirmedEvent(
                event.getPolicyId().toRepresentation(), event.getPremium()));
  }

  private static PolicyInformation from(QuoteForm quoteForm) {
    return new PolicyInformation(
        quoteForm.getPersonalInformation(),
        quoteForm.getAdditionalInsured(),
        quoteForm.getLocation(),
        quoteForm.getBuilding(),
        quoteForm.getPersonalProperty().getAnimals(),
        quoteForm.getPersonalProperty().getBicycle());
  }
}
