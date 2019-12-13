package ca.ulaval.glo4003.coverage.domain.premium.formula.quote;

import ca.ulaval.glo4003.coverage.domain.premium.formula.PremiumFormula;
import ca.ulaval.glo4003.coverage.domain.premium.formula.PremiumFormulaTest;
import ca.ulaval.glo4003.shared.domain.money.Money;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static ca.ulaval.glo4003.coverage.helper.premium.QuotePremiumInputGenerator.createQuotePremiumInput;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class QuoteBasicBlockPremiumFormulaTest extends PremiumFormulaTest<QuotePremiumInput> {
  private static final QuotePremiumInput PREMIUM_INPUT = createQuotePremiumInput();

  @Mock private QuoteBasicBlockBasePremiumCalculator basePremiumCalculator;

  @Override
  public PremiumFormula<QuotePremiumInput> createSubject(Money basePremium) {
    when(basePremiumCalculator.compute(eq(PREMIUM_INPUT))).thenReturn(basePremium);
    return new QuoteBasicBlockPremiumFormula(basePremiumCalculator);
  }

  @Override
  public QuotePremiumInput createInput() {
    return PREMIUM_INPUT;
  }
}
