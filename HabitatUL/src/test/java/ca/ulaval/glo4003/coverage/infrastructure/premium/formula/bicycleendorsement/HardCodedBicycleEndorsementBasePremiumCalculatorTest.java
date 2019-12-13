package ca.ulaval.glo4003.coverage.infrastructure.premium.formula.bicycleendorsement;

import ca.ulaval.glo4003.coverage.domain.premium.formula.bicycleendorsement.BicycleEndorsementPremiumInput;
import ca.ulaval.glo4003.shared.domain.money.Money;
import org.junit.Before;
import org.junit.Test;

import static ca.ulaval.glo4003.coverage.helper.premium.BicycleEndorsementPremiumInputGenerator.createBicycleEndorsementPremiumInput;
import static ca.ulaval.glo4003.coverage.infrastructure.premium.formula.bicycleendorsement.HardCodedBicycleEndorsementBasePremiumCalculator.HARDCODED_PREMIUM;
import static org.junit.Assert.assertEquals;

public class HardCodedBicycleEndorsementBasePremiumCalculatorTest {
  private static final BicycleEndorsementPremiumInput BICYCLE_PREMIUM_INPUT =
      createBicycleEndorsementPremiumInput();

  private HardCodedBicycleEndorsementBasePremiumCalculator subject;

  @Before
  public void setUp() {
    subject = new HardCodedBicycleEndorsementBasePremiumCalculator();
  }

  @Test
  public void computingBicycleBasePremium_shouldReturnHardCodedPremium() {
    Money premium = subject.compute(BICYCLE_PREMIUM_INPUT);

    assertEquals(HARDCODED_PREMIUM, premium);
  }
}
