package ca.ulaval.glo4003.coverage.domain.premium.formula.coveragemodification;

import ca.ulaval.glo4003.coverage.domain.premium.formula.BasePremiumCalculator;
import ca.ulaval.glo4003.shared.domain.money.Money;

public interface CoverageModificationBasePremiumCalculator
    extends BasePremiumCalculator<CoverageModificationPremiumInput> {
  Money compute(CoverageModificationPremiumInput coverageModificationPremiumInput);
}
