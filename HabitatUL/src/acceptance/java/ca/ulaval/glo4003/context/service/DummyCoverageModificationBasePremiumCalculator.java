package ca.ulaval.glo4003.context.service;

import ca.ulaval.glo4003.coverage.domain.premium.formula.coveragemodification.CoverageModificationBasePremiumCalculator;
import ca.ulaval.glo4003.coverage.domain.premium.formula.coveragemodification.CoverageModificationPremiumInput;
import ca.ulaval.glo4003.shared.domain.money.Money;

import static ca.ulaval.glo4003.helper.shared.MoneyGenerator.createMoney;

public class DummyCoverageModificationBasePremiumCalculator
    implements CoverageModificationBasePremiumCalculator {
  @Override
  public Money compute(CoverageModificationPremiumInput coverageModificationPremiumInput) {
    return createMoney();
  }
}
