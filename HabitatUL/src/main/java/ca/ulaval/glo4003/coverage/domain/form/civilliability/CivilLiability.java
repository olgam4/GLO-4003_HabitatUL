package ca.ulaval.glo4003.coverage.domain.form.civilliability;

import ca.ulaval.glo4003.shared.domain.ValueObject;
import ca.ulaval.glo4003.shared.domain.money.Amount;

public class CivilLiability extends ValueObject {
  public static final CivilLiability UNFILLED_CIVIL_LIABILITY = new CivilLiability(null);

  static final int MIN_NB_UNITS_DEFAULT_HIGHER_CIVIL_LIABILITY_LIMIT = 25;

  private final CivilLiabilityLimit limit;

  public CivilLiability(CivilLiabilityLimit limit) {
    this.limit = limit;
  }

  public CivilLiabilityLimit getLimit() {
    return limit;
  }

  public Amount getCoverageAmount() {
    return limit.getAmount();
  }

  public CivilLiability completeWithDefaultValues(int numberOfUnits) {
    CivilLiabilityLimit newCoverageAmount = limit;
    if (!isFilled()) {
      newCoverageAmount =
          shouldUseHigherDefaultCivilLiability(numberOfUnits)
              ? CivilLiabilityLimit.TWO_MILLION
              : CivilLiabilityLimit.ONE_MILLION;
    }
    return new CivilLiability(newCoverageAmount);
  }

  public boolean isFilled() {
    return !equals(UNFILLED_CIVIL_LIABILITY);
  }

  private boolean shouldUseHigherDefaultCivilLiability(int numberOfUnits) {
    return numberOfUnits >= MIN_NB_UNITS_DEFAULT_HIGHER_CIVIL_LIABILITY_LIMIT;
  }
}
