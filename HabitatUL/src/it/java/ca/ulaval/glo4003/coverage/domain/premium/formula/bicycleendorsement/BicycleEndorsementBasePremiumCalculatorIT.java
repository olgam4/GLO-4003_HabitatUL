package ca.ulaval.glo4003.coverage.domain.premium.formula.bicycleendorsement;

import ca.ulaval.glo4003.shared.domain.money.Money;
import org.junit.Before;
import org.junit.Test;

import static ca.ulaval.glo4003.helper.coverage.premium.BicycleEndorsementPremiumInputGenerator.createBicycleEndorsementPremiumInput;
import static org.junit.Assert.assertNotNull;

public abstract class BicycleEndorsementBasePremiumCalculatorIT {
  private static final BicycleEndorsementPremiumInput BICYCLE_PREMIUM_INPUT =
      createBicycleEndorsementPremiumInput();

  private BicycleEndorsementBasePremiumCalculator subject;

  @Before
  public void setUp() {
    subject = createSubject();
  }

  @Test
  public void computingBicycleEndorsementBasePremium_shouldReturnBasePremium() {
    Money premium = subject.compute(BICYCLE_PREMIUM_INPUT);

    assertNotNull(premium);
  }

  public abstract BicycleEndorsementBasePremiumCalculator createSubject();
}
