package ca.ulaval.glo4003.calculator.infrastructure.premium.formula.bike;

import ca.ulaval.glo4003.calculator.domain.premium.formula.bike.BikePremiumInput;
import ca.ulaval.glo4003.helper.calculator.BikePremiumInputGenerator;
import ca.ulaval.glo4003.shared.domain.money.Money;
import org.junit.Before;
import org.junit.Test;

import static ca.ulaval.glo4003.calculator.infrastructure.premium.formula.bike.HardCodedBikeBasePremiumCalculator.HARDCODED_PREMIUM;
import static org.junit.Assert.assertEquals;

public class HardCodedBikeBasePremiumCalculatorTest {
  private static final BikePremiumInput BIKE_PREMIUM_INPUT = BikePremiumInputGenerator.create();

  private HardCodedBikeBasePremiumCalculator subject;

  @Before
  public void setUp() {
    subject = new HardCodedBikeBasePremiumCalculator();
  }

  @Test
  public void computingBikeBasePremium_shouldReturnHardCodedPremium() {
    Money premium = subject.compute(BIKE_PREMIUM_INPUT);

    assertEquals(HARDCODED_PREMIUM, premium);
  }
}
