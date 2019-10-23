package ca.ulaval.glo4003.underwriting.domain.quote.price;

import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.QuotePriceAdjustment;
import org.junit.Before;
import org.junit.Test;

import static ca.ulaval.glo4003.helper.quote.form.UniversityProfileGenerator.createCycle;
import static org.junit.Assert.assertNotNull;

public abstract class GraduateStudentAdjustmentProviderIT {
  private static final String CYCLE = createCycle();

  private GraduateStudentAdjustmentProvider subject;

  @Before
  public void setUp() {
    subject = createSubject();
  }

  @Test
  public void gettingAdjustment_shouldProvideAdjustment() {
    QuotePriceAdjustment adjustment = subject.getAdjustment(CYCLE);

    assertNotNull(adjustment);
  }

  public abstract GraduateStudentAdjustmentProvider createSubject();
}
