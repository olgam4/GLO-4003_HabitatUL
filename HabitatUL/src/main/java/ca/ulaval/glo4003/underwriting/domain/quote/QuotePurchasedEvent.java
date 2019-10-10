package ca.ulaval.glo4003.underwriting.domain.quote;

import ca.ulaval.glo4003.mediator.event.Event;
import ca.ulaval.glo4003.mediator.event.EventChannel;
import ca.ulaval.glo4003.mediator.event.EventPayload;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import ca.ulaval.glo4003.underwriting.domain.price.Price;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;
import ca.ulaval.glo4003.underwriting.domain.quote.form.identity.Identity;
import ca.ulaval.glo4003.underwriting.domain.quote.form.location.Location;

public class QuotePurchasedEvent extends Event {
  private static final String QUOTE_PURCHASE_EVENT_TYPE = "quotePurchasedEvent";

  private static final String QUOTE_ID_PAYLOAD_KEY = "quoteId";
  private static final String PRICE_PAYLOAD_KEY = "price";
  private static final String IDENTITY_PAYLOAD_KEY = "identity";
  private static final String LOCATION_PAYLOAD_KEY = "location";

  private static final String IDENTITY_FIRST_NAME_PAYLOAD_KEY = "firstName";
  private static final String IDENTITY_LAST_NAME_PAYLOAD_KEY = "lastName";
  private static final String IDENTITY_BIRTH_DATE_PAYLOAD_KEY = "birthDate";
  private static final String IDENTITY_GENDER_PAYLOAD_KEY = "gender";

  private static final String LOCATION_POSTAL_CODE_PAYLOAD_KEY = "postalCode";
  private static final String LOCATION_STREET_NUMBER_PAYLOAD_KEY = "streetNumber";
  private static final String LOCATION_APARTMENT_NUMBER_PAYLOAD_KEY = "apartmentNumber";
  private static final String LOCATION_FLOOR_PAYLOAD_KEY = "floor";

  public QuotePurchasedEvent(
      QuoteId quoteId, Price price, QuoteForm quoteForm, ClockProvider clockProvider) {
    super(
        EventChannel.QUOTES,
        QUOTE_PURCHASE_EVENT_TYPE,
        createPayload(quoteId, price, quoteForm),
        clockProvider);
  }

  private static EventPayload createPayload(QuoteId quoteId, Price price, QuoteForm quoteForm) {
    return EventPayload.EventPayloadBuilder.anEventPayload()
        .withEntry(QUOTE_ID_PAYLOAD_KEY, quoteId.getValue().toString())
        .withEntry(PRICE_PAYLOAD_KEY, price.getValue().toString())
        .withObject(IDENTITY_PAYLOAD_KEY, createIdentityPayload(quoteForm.getIdentity()))
        .withObject(LOCATION_PAYLOAD_KEY, createLocationPayload(quoteForm.getLocation()))
        .build();
  }

  private static EventPayload createIdentityPayload(Identity identity) {
    return EventPayload.EventPayloadBuilder.anEventPayload()
        .withEntry(IDENTITY_FIRST_NAME_PAYLOAD_KEY, identity.getFirstName())
        .withEntry(IDENTITY_LAST_NAME_PAYLOAD_KEY, identity.getLastName())
        .withEntry(IDENTITY_BIRTH_DATE_PAYLOAD_KEY, identity.getBirthDate().getValue().toString())
        .withEntry(IDENTITY_GENDER_PAYLOAD_KEY, identity.getGender().toString())
        .build();
  }

  private static EventPayload createLocationPayload(Location location) {
    return EventPayload.EventPayloadBuilder.anEventPayload()
        .withEntry(LOCATION_POSTAL_CODE_PAYLOAD_KEY, location.getPostalCode().toString())
        .withEntry(LOCATION_STREET_NUMBER_PAYLOAD_KEY, location.getStreetNumber())
        .withEntry(LOCATION_APARTMENT_NUMBER_PAYLOAD_KEY, location.getApartmentNumber())
        .withEntry(LOCATION_FLOOR_PAYLOAD_KEY, location.getFloor().getValue())
        .build();
  }
}
