package ca.ulaval.glo4003.coverage.domain.premium.formula.quote;

import ca.ulaval.glo4003.coverage.domain.premium.formula.PremiumFormulaTest;
import ca.ulaval.glo4003.helper.calculator.premium.QuotePremiumInputGenerator;

public class QuoteBasicBlockPremiumFormulaTest extends PremiumFormulaTest<QuotePremiumInput> {
  @Override
  public QuotePremiumInput createInput() {
    return QuotePremiumInputGenerator.create();
  }
}
