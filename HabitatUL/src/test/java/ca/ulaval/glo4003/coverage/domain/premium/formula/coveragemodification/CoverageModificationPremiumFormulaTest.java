package ca.ulaval.glo4003.coverage.domain.premium.formula.coveragemodification;

import ca.ulaval.glo4003.coverage.domain.premium.formula.PremiumFormula;
import ca.ulaval.glo4003.coverage.domain.premium.formula.PremiumFormulaTest;
import ca.ulaval.glo4003.shared.domain.money.Money;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static ca.ulaval.glo4003.coverage.helper.premium.CoverageModificationPremiumInputGenerator.createCoverageModificationPremiumInput;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CoverageModificationPremiumFormulaTest
    extends PremiumFormulaTest<CoverageModificationPremiumInput> {
  private static final CoverageModificationPremiumInput PREMIUM_INPUT =
      createCoverageModificationPremiumInput();

  @Mock private CoverageModificationBasePremiumCalculator basePremiumCalculator;

  @Override
  public PremiumFormula<CoverageModificationPremiumInput> createSubject(Money basePremium) {
    when(basePremiumCalculator.compute(PREMIUM_INPUT)).thenReturn(basePremium);
    return new CoverageModificationPremiumFormula(basePremiumCalculator);
  }

  @Override
  public CoverageModificationPremiumInput createInput() {
    return PREMIUM_INPUT;
  }
}
