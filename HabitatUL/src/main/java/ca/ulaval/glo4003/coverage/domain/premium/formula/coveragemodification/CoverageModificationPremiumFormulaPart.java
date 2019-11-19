package ca.ulaval.glo4003.coverage.domain.premium.formula.coveragemodification;

import ca.ulaval.glo4003.coverage.domain.premium.formula.PremiumFormulaPart;
import ca.ulaval.glo4003.shared.domain.money.Money;

public interface CoverageModificationPremiumFormulaPart
    extends PremiumFormulaPart<CoverageModificationPremiumInput> {
  Money compute(
      CoverageModificationPremiumInput coverageModificationPremiumInput, Money basePremium);
}
