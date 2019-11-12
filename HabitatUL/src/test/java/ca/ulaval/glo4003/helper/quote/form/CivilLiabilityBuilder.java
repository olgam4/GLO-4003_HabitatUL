package ca.ulaval.glo4003.helper.quote.form;

import ca.ulaval.glo4003.calculator.domain.input.CivilLiabilityLimit;
import ca.ulaval.glo4003.underwriting.domain.quote.form.civilliability.CivilLiability;

import static ca.ulaval.glo4003.helper.calculator.QuotePremiumInputGenerator.createCivilLiabilityLimit;

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
