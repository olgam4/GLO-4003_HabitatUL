package ca.ulaval.glo4003.underwriting.domain.quote.event;

import ca.ulaval.glo4003.generator.price.PriceGenerator;
import ca.ulaval.glo4003.generator.quote.form.QuoteFormGenerator;
import ca.ulaval.glo4003.mediator.event.EventChannel;
import ca.ulaval.glo4003.shared.infrastructure.FixedClockProvider;
import ca.ulaval.glo4003.underwriting.domain.price.Price;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteId;
import ca.ulaval.glo4003.underwriting.domain.quote.QuotePurchasedEvent;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class QuotePurchasedEventTest {
  private static final FixedClockProvider FIXED_CLOCK_PROVIDER = new FixedClockProvider();

  private QuotePurchasedEvent subject;
  private QuoteForm quoteForm;
  private Price price;
  private QuoteId quoteId;

  @Before
  public void setUp() {
    quoteId = new QuoteId();
    price = PriceGenerator.create();
    quoteForm = QuoteFormGenerator.createQuoteForm();
    subject = new QuotePurchasedEvent(quoteId, price, quoteForm, FIXED_CLOCK_PROVIDER);
  }

  @Test
  public void creatingEvent_shouldProduceQuoteEvent() {
    assertEquals(EventChannel.QUOTES, subject.getChannel());
  }

  @Test
  public void creatingEvent_shouldBeQuotePurchasedEventType() {
    assertEquals("quotePurchasedEvent", subject.getType());
  }

  @Test
  @Ignore
  public void creatingEvent_shouldSerializeProperly() {
    // TODO
    JSONObject payload = subject.getPayload().getValue();
    assertEquals(quoteId.getValue().toString(), payload.get("quoteId"));
    assertEquals(price.getValue().toString(), payload.get("price"));

    JSONObject identity = new JSONObject(payload.get("identity"));
    assertEquals(quoteForm.getIdentity().getFirstName(), identity.get("firstName"));
    assertEquals(quoteForm.getIdentity().getLastName(), identity.get("lastName"));
    assertEquals(
        quoteForm.getIdentity().getBirthDate().getValue().toString(), identity.get("birthDate"));
    assertEquals(quoteForm.getIdentity().getGender().toString(), identity.get("gender"));

    JSONObject location = new JSONObject(payload.get("location"));
    assertEquals(quoteForm.getLocation().getPostalCode().getValue(), location.get("postalCode"));
    assertEquals(quoteForm.getLocation().getStreetNumber(), location.get("streetNumber"));
    assertEquals(quoteForm.getLocation().getApartmentNumber(), location.get("apartmentNumber"));
    assertEquals(quoteForm.getLocation().getFloor().getValue(), location.get("floor"));
  }
}
