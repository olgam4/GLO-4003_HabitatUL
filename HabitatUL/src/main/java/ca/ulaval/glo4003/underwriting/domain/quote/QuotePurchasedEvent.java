package ca.ulaval.glo4003.underwriting.domain.quote;

import ca.ulaval.glo4003.mediator.Event;
import ca.ulaval.glo4003.mediator.EventChannel;
import ca.ulaval.glo4003.shared.domain.ClockProvider;
import ca.ulaval.glo4003.underwriting.domain.premium.Premium;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;
import ca.ulaval.glo4003.underwriting.domain.quote.form.identity.Identity;
import ca.ulaval.glo4003.underwriting.domain.quote.form.location.Location;

import java.util.HashMap;
import java.util.Map;

public class QuotePurchasedEvent extends Event {
  private static final String QUOTE_PURCHASE_EVENT_TYPE = "quotePurchasedEvent";

  private static final String QUOTE_ID_PAYLOAD_KEY = "quoteId";
  private static final String PREMIUM_PAYLOAD_KEY = "premium";
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
      QuoteId quoteId, Premium premium, QuoteForm quoteForm, ClockProvider clockProvider) {
    super(
        EventChannel.QUOTES,
        QUOTE_PURCHASE_EVENT_TYPE,
        createPayload(quoteId, premium, quoteForm),
        clockProvider);
  }

  private static Map<String, Object> createPayload(
      QuoteId quoteId, Premium premium, QuoteForm quoteForm) {
    Map<String, Object> payload = new HashMap<>();
    payload.put(QUOTE_ID_PAYLOAD_KEY, quoteId.getValue().toString());
    payload.put(PREMIUM_PAYLOAD_KEY, premium.getValue().toString());
    payload.put(IDENTITY_PAYLOAD_KEY, createIdentityPayload(quoteForm.getIdentity()));
    payload.put(LOCATION_PAYLOAD_KEY, createLocationPayload(quoteForm.getLocation()));
    return payload;
  }

  private static HashMap<String, Object> createIdentityPayload(Identity identity) {
    HashMap<String, Object> payload = new HashMap<>();
    payload.put(IDENTITY_FIRST_NAME_PAYLOAD_KEY, identity.getFirstName());
    payload.put(IDENTITY_LAST_NAME_PAYLOAD_KEY, identity.getLastName());
    payload.put(IDENTITY_BIRTH_DATE_PAYLOAD_KEY, identity.getBirthDate().getValue().toString());
    payload.put(IDENTITY_GENDER_PAYLOAD_KEY, identity.getGender().toString());
    return payload;
  }

  private static HashMap<Object, Object> createLocationPayload(Location location) {
    HashMap<Object, Object> payload = new HashMap<>();
    payload.put(LOCATION_POSTAL_CODE_PAYLOAD_KEY, location.getPostalCode().getValue());
    payload.put(LOCATION_STREET_NUMBER_PAYLOAD_KEY, location.getStreetNumber());
    payload.put(LOCATION_APARTMENT_NUMBER_PAYLOAD_KEY, location.getApartmentNumber());
    payload.put(LOCATION_FLOOR_PAYLOAD_KEY, location.getFloor().getValue());
    return payload;
  }
}
