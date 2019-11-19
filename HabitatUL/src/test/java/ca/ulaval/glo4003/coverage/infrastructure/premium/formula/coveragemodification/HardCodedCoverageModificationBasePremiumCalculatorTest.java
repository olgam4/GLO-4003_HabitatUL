package ca.ulaval.glo4003.coverage.infrastructure.premium.formula.coveragemodification;

import ca.ulaval.glo4003.coverage.domain.premium.formula.coveragemodification.CoverageModificationPremiumInput;
import ca.ulaval.glo4003.shared.domain.money.Money;
import org.junit.Before;
import org.junit.Test;

import static ca.ulaval.glo4003.coverage.infrastructure.premium.formula.coveragemodification.HardCodedCoverageModificationBasePremiumCalculator.HARDCODED_PREMIUM;
import static ca.ulaval.glo4003.helper.coverage.premium.CoverageModificationPremiumInputGenerator.createCoverageModificationPremiumInput;
import static org.junit.Assert.assertEquals;

public class HardCodedCoverageModificationBasePremiumCalculatorTest {
  private static final CoverageModificationPremiumInput COVERAGE_MODIFICATION_PREMIUM_INPUT =
      createCoverageModificationPremiumInput();

  private HardCodedCoverageModificationBasePremiumCalculator subject;

  @Before
  public void setUp() {
    subject = new HardCodedCoverageModificationBasePremiumCalculator();
  }

  @Test
  public void computingCoverageModificationBasePremium_shouldReturnHardCodedPremium() {
    Money premium = subject.compute(COVERAGE_MODIFICATION_PREMIUM_INPUT);

    assertEquals(HARDCODED_PREMIUM, premium);
  }
}
