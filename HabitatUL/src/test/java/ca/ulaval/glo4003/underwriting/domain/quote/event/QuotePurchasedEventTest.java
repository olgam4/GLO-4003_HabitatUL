package ca.ulaval.glo4003.underwriting.domain.quote.event;

import ca.ulaval.glo4003.generator.premium.PremiumGenerator;
import ca.ulaval.glo4003.generator.quote.form.QuoteFormGenerator;
import ca.ulaval.glo4003.mediator.EventChannel;
import ca.ulaval.glo4003.shared.infrastructure.FixedClockProvider;
import ca.ulaval.glo4003.underwriting.domain.premium.Premium;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteId;
import ca.ulaval.glo4003.underwriting.domain.quote.QuotePurchasedEvent;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class QuotePurchasedEventTest {
  private static final FixedClockProvider FIXED_CLOCK_PROVIDER = new FixedClockProvider();

  private QuotePurchasedEvent subject;
  private QuoteForm quoteForm;
  private Premium premium;
  private QuoteId quoteId;

  @Before
  public void setUp() {
    quoteId = new QuoteId();
    premium = PremiumGenerator.create();
    quoteForm = QuoteFormGenerator.createValidQuoteForm();
    subject = new QuotePurchasedEvent(quoteId, premium, quoteForm, FIXED_CLOCK_PROVIDER);
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
  public void creatingEvent_shouldSerializeProperly() {
    Map<String, Object> payload = subject.getPayload();
    assertEquals(quoteId.getValue().toString(), payload.get("quoteId"));
    assertEquals(premium.getValue().toString(), payload.get("premium"));

    Map<String, Object> identity = (Map<String, Object>) payload.get("identity");
    assertEquals(quoteForm.getIdentity().getFirstName(), identity.get("firstName"));
    assertEquals(quoteForm.getIdentity().getLastName(), identity.get("lastName"));
    assertEquals(
        quoteForm.getIdentity().getBirthDate().getValue().toString(), identity.get("birthDate"));
    assertEquals(quoteForm.getIdentity().getGender().toString(), identity.get("gender"));

    Map<String, Object> location = (Map<String, Object>) payload.get("location");
    assertEquals(quoteForm.getLocation().getPostalCode().getValue(), location.get("postalCode"));
    assertEquals(quoteForm.getLocation().getStreetNumber(), location.get("streetNumber"));
    assertEquals(quoteForm.getLocation().getApartmentNumber(), location.get("apartmentNumber"));
    assertEquals(quoteForm.getLocation().getFloor().getValue(), location.get("floor"));
  }
}
