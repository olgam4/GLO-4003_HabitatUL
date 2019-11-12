package ca.ulaval.glo4003.helper.calculator.form.civilliability;

import ca.ulaval.glo4003.calculator.domain.form.civilliability.CivilLiability;
import ca.ulaval.glo4003.calculator.domain.form.civilliability.CivilLiabilityLimit;

import static ca.ulaval.glo4003.helper.calculator.premium.QuotePremiumInputGenerator.createCivilLiabilityLimit;

public class CivilLiabilityBuilder {
  private static final CivilLiabilityLimit DEFAULT_AMOUNT = createCivilLiabilityLimit();

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
