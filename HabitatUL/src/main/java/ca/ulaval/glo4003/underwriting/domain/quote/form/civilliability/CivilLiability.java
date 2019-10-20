package ca.ulaval.glo4003.underwriting.domain.quote.form.civilliability;

import ca.ulaval.glo4003.shared.domain.money.Amount;

public class CivilLiability {
  static final int MIN_NB_UNITS_DEFAULT_HIGHER_CIVIL_LIABILITY_AMOUNT = 25;

  private CivilLiabilityAmount coverageAmount;

  public CivilLiability(CivilLiabilityAmount coverageAmount) {
    this.coverageAmount = coverageAmount;
  }

  public CivilLiabilityAmount getCivilLiabilityAmount() {
    return coverageAmount;
  }

  public Amount getCoverageAmount() {
    return coverageAmount.getValue();
  }

  public void completeWithDefaultValues(int numberOfUnits) {
    if (shouldUseDefaultCivilLiabilityAmount()) {
      coverageAmount =
          shouldUseHigherDefaultCivilLiability(numberOfUnits)
              ? CivilLiabilityAmount.TWO_MILLION
              : CivilLiabilityAmount.ONE_MILLION;
    }
  }

  private boolean shouldUseDefaultCivilLiabilityAmount() {
    return coverageAmount == null;
  }

  private boolean shouldUseHigherDefaultCivilLiability(int numberOfUnits) {
    return numberOfUnits >= MIN_NB_UNITS_DEFAULT_HIGHER_CIVIL_LIABILITY_AMOUNT;
  }
}
