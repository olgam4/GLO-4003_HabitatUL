package ca.ulaval.glo4003.calculator.domain.premium.detail;

import ca.ulaval.glo4003.calculator.domain.CoverageCategory;
import ca.ulaval.glo4003.shared.domain.ValueObject;
import ca.ulaval.glo4003.shared.domain.money.Money;

public abstract class PremiumDetail extends ValueObject {
  private final CoverageCategory coverage;
  private final Money premium;

  public PremiumDetail(CoverageCategory coverage, Money premium) {
    this.coverage = coverage;
    this.premium = premium;
  }

  public CoverageCategory getCoverage() {
    return coverage;
  }

  public Money getPremium() {
    return premium;
  }
}
