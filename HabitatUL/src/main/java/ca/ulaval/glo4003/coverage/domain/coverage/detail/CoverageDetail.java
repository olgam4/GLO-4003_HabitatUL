package ca.ulaval.glo4003.coverage.domain.coverage.detail;

import ca.ulaval.glo4003.coverage.domain.coverage.CoverageCategory;
import ca.ulaval.glo4003.shared.domain.ValueObject;
import ca.ulaval.glo4003.shared.domain.money.Amount;

public class CoverageDetail extends ValueObject {
  private final CoverageCategory coverage;
  private final Amount amount;

  public CoverageDetail(CoverageCategory coverage, Amount amount) {
    this.coverage = coverage;
    this.amount = amount;
  }

  public CoverageCategory getCoverage() {
    return coverage;
  }

  public Amount getAmount() {
    return amount;
  }
}
