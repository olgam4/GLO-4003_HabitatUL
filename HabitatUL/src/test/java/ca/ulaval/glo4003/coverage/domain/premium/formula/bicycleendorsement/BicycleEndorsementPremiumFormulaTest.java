package ca.ulaval.glo4003.coverage.domain.premium.formula.bicycleendorsement;

import ca.ulaval.glo4003.coverage.domain.premium.formula.PremiumFormula;
import ca.ulaval.glo4003.coverage.domain.premium.formula.PremiumFormulaTest;
import ca.ulaval.glo4003.coverage.helper.premium.BicycleEndorsementPremiumInputGenerator;
import ca.ulaval.glo4003.shared.domain.money.Money;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BicycleEndorsementPremiumFormulaTest
    extends PremiumFormulaTest<BicycleEndorsementPremiumInput> {
  private static final BicycleEndorsementPremiumInput PREMIUM_INPUT =
      BicycleEndorsementPremiumInputGenerator.createBicycleEndorsementPremiumInput();

  @Mock private BicycleEndorsementBasePremiumCalculator basePremiumCalculator;

  @Override
  public PremiumFormula<BicycleEndorsementPremiumInput> createSubject(Money basePremium) {
    when(basePremiumCalculator.compute(eq(PREMIUM_INPUT))).thenReturn(basePremium);
    return new BicycleEndorsementPremiumFormula(basePremiumCalculator);
  }

  @Override
  public BicycleEndorsementPremiumInput createInput() {
    return PREMIUM_INPUT;
  }
}
