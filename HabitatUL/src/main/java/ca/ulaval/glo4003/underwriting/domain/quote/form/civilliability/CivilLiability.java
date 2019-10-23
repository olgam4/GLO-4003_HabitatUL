package ca.ulaval.glo4003.underwriting.domain.quote.form.civilliability;

import ca.ulaval.glo4003.shared.domain.ValueObject;
import ca.ulaval.glo4003.shared.domain.money.Amount;

public class CivilLiability extends ValueObject {
  public static final CivilLiability UNFILLED_CIVIL_LIABILITY = new CivilLiability(null);

  static final int MIN_NB_UNITS_DEFAULT_HIGHER_CIVIL_LIABILITY_AMOUNT = 25;

  private final CivilLiabilityAmount coverageAmount;

  public CivilLiability(CivilLiabilityAmount coverageAmount) {
    this.coverageAmount = coverageAmount;
  }

  public CivilLiabilityAmount getCivilLiabilityAmount() {
    return coverageAmount;
  }

  public Amount getCoverageAmount() {
    return coverageAmount.getValue();
  }

  public CivilLiability completeWithDefaultValues(int numberOfUnits) {
    CivilLiabilityAmount newCoverageAmount = coverageAmount;
    if (!isFilled()) {
      newCoverageAmount =
          shouldUseHigherDefaultCivilLiability(numberOfUnits)
              ? CivilLiabilityAmount.TWO_MILLION
              : CivilLiabilityAmount.ONE_MILLION;
    }
    return new CivilLiability(newCoverageAmount);
  }

  public boolean isFilled() {
    return !equals(UNFILLED_CIVIL_LIABILITY);
  }

  private boolean shouldUseHigherDefaultCivilLiability(int numberOfUnits) {
    return numberOfUnits >= MIN_NB_UNITS_DEFAULT_HIGHER_CIVIL_LIABILITY_AMOUNT;
  }
}
