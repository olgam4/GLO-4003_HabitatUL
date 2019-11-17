package ca.ulaval.glo4003.underwriting.domain.quote;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public abstract class QuoteValidityPeriodProviderIT {
  private QuoteValidityPeriodProvider subject;

  @Before
  public void setUp() {
    subject = createSubject();
  }

  @Test
  public void gettingQuoteValidityPeriod_shouldProvideValidityPeriod() {
    assertNotNull(subject.getQuoteValidityPeriod());
  }

  public abstract QuoteValidityPeriodProvider createSubject();
}
