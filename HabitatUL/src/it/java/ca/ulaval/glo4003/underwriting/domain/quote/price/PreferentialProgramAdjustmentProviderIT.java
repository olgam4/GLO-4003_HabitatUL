package ca.ulaval.glo4003.underwriting.domain.quote.price;

import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.QuotePriceAdjustment;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public abstract class PreferentialProgramAdjustmentProviderIT {
  private static final String CYCLE = "cycle";
  private static final String DEGREE = "degree";
  private static final String PROGRAM = "program";

  private PreferentialProgramAdjustmentProvider subject;

  @Before
  public void setUp() {
    subject = createSubject();
  }

  @Test
  public void gettingAdjustment_shouldProvideAdjustment() {
    QuotePriceAdjustment adjustment =
        subject.getAdjustment(CYCLE, DEGREE, PROGRAM);

    assertNotNull(adjustment);
  }

  public abstract PreferentialProgramAdjustmentProvider createSubject();
}
