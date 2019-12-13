package ca.ulaval.glo4003.coverage.helper.form.civilliability;

import ca.ulaval.glo4003.coverage.domain.form.civilliability.CivilLiability;
import ca.ulaval.glo4003.coverage.domain.form.civilliability.CivilLiabilityLimit;

import static ca.ulaval.glo4003.coverage.helper.premium.QuotePremiumInputGenerator.createCivilLiabilityLimit;

public class CivilLiabilityBuilder {
  private final CivilLiabilityLimit DEFAULT_AMOUNT = createCivilLiabilityLimit();

  private CivilLiabilityLimit amount = DEFAULT_AMOUNT;

  private CivilLiabilityBuilder() {}

  public static CivilLiabilityBuilder aCivilLiability() {
    return new CivilLiabilityBuilder();
  }

  public CivilLiabilityBuilder withAmount(CivilLiabilityLimit amount) {
    this.amount = amount;
    return this;
  }

  public CivilLiability build() {
    return new CivilLiability(amount);
  }
}
