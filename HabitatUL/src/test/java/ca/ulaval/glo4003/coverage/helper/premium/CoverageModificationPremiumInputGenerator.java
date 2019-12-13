package ca.ulaval.glo4003.coverage.helper.premium;

import ca.ulaval.glo4003.coverage.domain.form.civilliability.CivilLiabilityLimit;
import ca.ulaval.glo4003.coverage.domain.premium.formula.coveragemodification.CoverageModificationPremiumInput;

import static ca.ulaval.glo4003.coverage.helper.premium.QuotePremiumInputGenerator.createCivilLiabilityLimit;

public class CoverageModificationPremiumInputGenerator {
  private CoverageModificationPremiumInputGenerator() {}

  public static CoverageModificationPremiumInput createCoverageModificationPremiumInput() {
    return new CoverageModificationPremiumInput(createCivilLiabilityLimit());
  }

  public static CoverageModificationPremiumInput createCoverageModificationPremiumInput(
      CivilLiabilityLimit civilLiabilityLimit) {
    return new CoverageModificationPremiumInput(civilLiabilityLimit);
  }
}
