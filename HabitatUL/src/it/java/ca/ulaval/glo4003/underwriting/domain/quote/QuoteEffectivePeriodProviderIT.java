package ca.ulaval.glo4003.underwriting.domain.quote;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public abstract class QuoteEffectivePeriodProviderIT {
  private QuoteEffectivePeriodProvider subject;

  @Before
  public void setUp() {
    subject = createSubject();
  }

  @Test
  public void gettingQuoteEffectivePeriod_shouldProvidePeriod() {
    assertNotNull(subject.getQuoteEffectivePeriod());
  }

  public abstract QuoteEffectivePeriodProvider createSubject();
}
