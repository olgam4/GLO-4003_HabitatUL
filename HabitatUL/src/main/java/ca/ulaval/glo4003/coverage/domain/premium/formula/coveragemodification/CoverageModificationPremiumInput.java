package ca.ulaval.glo4003.coverage.domain.premium.formula.coveragemodification;

import ca.ulaval.glo4003.coverage.domain.form.civilliability.CivilLiabilityLimit;
import ca.ulaval.glo4003.shared.domain.ValueObject;

public class CoverageModificationPremiumInput extends ValueObject {
  private final CivilLiabilityLimit civilLiabilityLimit;

  public CoverageModificationPremiumInput(CivilLiabilityLimit civilLiabilityLimit) {
    this.civilLiabilityLimit = civilLiabilityLimit;
  }

  public CivilLiabilityLimit getCivilLiabilityLimit() {
    return civilLiabilityLimit;
  }
}
