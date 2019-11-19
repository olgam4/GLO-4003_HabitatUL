package ca.ulaval.glo4003.coverage.infrastructure.premium.formula.coveragemodification;

import ca.ulaval.glo4003.coverage.domain.premium.formula.coveragemodification.CoverageModificationBasePremiumCalculator;
import ca.ulaval.glo4003.coverage.domain.premium.formula.coveragemodification.CoverageModificationPremiumInput;
import ca.ulaval.glo4003.shared.domain.money.Amount;
import ca.ulaval.glo4003.shared.domain.money.Money;

import java.math.BigDecimal;

public class HardCodedCoverageModificationBasePremiumCalculator
    implements CoverageModificationBasePremiumCalculator {
  static final Money HARDCODED_PREMIUM = new Money(new Amount(BigDecimal.valueOf(200)));

  @Override
  public Money compute(CoverageModificationPremiumInput coverageModificationPremiumInput) {
    return HARDCODED_PREMIUM;
  }
}
