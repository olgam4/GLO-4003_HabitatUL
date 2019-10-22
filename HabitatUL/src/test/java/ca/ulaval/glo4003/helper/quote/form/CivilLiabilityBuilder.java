package ca.ulaval.glo4003.helper.quote.form;

import ca.ulaval.glo4003.underwriting.domain.quote.form.civilliability.CivilLiability;
import ca.ulaval.glo4003.underwriting.domain.quote.form.civilliability.CivilLiabilityAmount;

import static ca.ulaval.glo4003.helper.quote.form.CivilLiabilityGenerator.createCivilLiabilityAmount;

public class CivilLiabilityBuilder {
  private static final CivilLiabilityAmount DEFAULT_AMOUNT = createCivilLiabilityAmount();

  private CivilLiabilityAmount amount = DEFAULT_AMOUNT;

  private CivilLiabilityBuilder() {}

  public static CivilLiabilityBuilder aCivilLiability() {
    return new CivilLiabilityBuilder();
  }

  public CivilLiabilityBuilder withoutAmount() {
    this.amount = null;
    return this;
  }

  public CivilLiabilityBuilder withAmount(CivilLiabilityAmount amount) {
    this.amount = amount;
    return this;
  }

  public CivilLiability build() {
    return new CivilLiability(amount);
  }
}
