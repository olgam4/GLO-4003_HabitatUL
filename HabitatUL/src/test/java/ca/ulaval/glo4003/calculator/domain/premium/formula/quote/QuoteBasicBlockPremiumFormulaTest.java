package ca.ulaval.glo4003.calculator.domain.premium.formula.quote;

import ca.ulaval.glo4003.calculator.domain.premium.formula.PremiumFormulaTest;
import ca.ulaval.glo4003.helper.calculator.QuotePremiumInputGenerator;

public class QuoteBasicBlockPremiumFormulaTest extends PremiumFormulaTest<QuotePremiumInput> {
  @Override
  public QuotePremiumInput createInput() {
    return QuotePremiumInputGenerator.create();
  }
}
