package ca.ulaval.glo4003.calculator.domain.premium.formula.quote;

import ca.ulaval.glo4003.calculator.domain.premium.formula.PremiumFormulaTest;
import ca.ulaval.glo4003.helper.premium.QuotePremiumInputGenerator;

public class QuoteBaseCoveragePremiumFormulaTest extends PremiumFormulaTest<QuotePremiumInput> {
  @Override
  public QuotePremiumInput createInput() {
    return QuotePremiumInputGenerator.create();
  }
}
