package ca.ulaval.glo4003.calculator.domain.premium.formula.bike;

import ca.ulaval.glo4003.helper.premium.BikePremiumInputGenerator;
import ca.ulaval.glo4003.shared.domain.money.Money;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public abstract class BikeBasePremiumCalculatorIT {
  private static final BikePremiumInput BIKE_PREMIUM_INPUT = BikePremiumInputGenerator.create();

  private BikeBasePremiumCalculator subject;

  @Before
  public void setUp() {
    subject = createSubject();
  }

  @Test
  public void computingQuoteBasePremium_shouldReturnBasePremium() {
    Money premium = subject.compute(BIKE_PREMIUM_INPUT);

    assertNotNull(premium);
  }

  public abstract BikeBasePremiumCalculator createSubject();
}
