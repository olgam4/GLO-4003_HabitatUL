package ca.ulaval.glo4003.calculator.infrastructure.premium.formulapart.civilliabilitylimit;

import ca.ulaval.glo4003.calculator.domain.premium.adjustment.MultiplicativePremiumAdjustment;
import ca.ulaval.glo4003.calculator.domain.premium.adjustment.NullPremiumAdjustment;
import ca.ulaval.glo4003.calculator.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.calculator.domain.premium.input.CivilLiabilityLimitInput;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HardCodedCivilLiabilityLimitAdjustmentProviderTest {
  private HardCodedCivilLiabilityLimitAdjustmentProvider subject;

  @Before
  public void setUp() {
    subject = new HardCodedCivilLiabilityLimitAdjustmentProvider();
  }

  @Test
  public void
      gettingAdjustment_withKnownCivilLiabilityLimit_shouldProvideCorrespondingAdjustment() {
    PremiumAdjustment adjustment = subject.getAdjustment(CivilLiabilityLimitInput.TWO_MILLION);

    PremiumAdjustment expectedAdjustment = new MultiplicativePremiumAdjustment(0.25f);
    assertEquals(expectedAdjustment, adjustment);
  }

  @Test
  public void gettingAdjustment_withUnknownCivilLiabilityLimit_shouldProvideNoAdjustment() {
    PremiumAdjustment adjustment = subject.getAdjustment(CivilLiabilityLimitInput.ONE_MILLION);

    PremiumAdjustment expectedAdjustment = new NullPremiumAdjustment();
    assertEquals(expectedAdjustment, adjustment);
  }
}
