package ca.ulaval.glo4003.coverage.domain.premium.formula.coveragemodification;

import ca.ulaval.glo4003.shared.domain.money.Money;
import org.junit.Before;
import org.junit.Test;

import static ca.ulaval.glo4003.coverage.helper.premium.CoverageModificationPremiumInputGenerator.createCoverageModificationPremiumInput;
import static org.junit.Assert.assertNotNull;

public abstract class CoverageModificationBasePremiumCalculatorIT {
  private static final CoverageModificationPremiumInput COVERAGE_MODIFICATION_PREMIUM_INPUT =
      createCoverageModificationPremiumInput();

  private CoverageModificationBasePremiumCalculator subject;

  @Before
  public void setUp() {
    subject = createSubject();
  }

  @Test
  public void computingCoverageModificationBasePremium_shouldReturnBasePremium() {
    Money premium = subject.compute(COVERAGE_MODIFICATION_PREMIUM_INPUT);

    assertNotNull(premium);
  }

  public abstract CoverageModificationBasePremiumCalculator createSubject();
}
