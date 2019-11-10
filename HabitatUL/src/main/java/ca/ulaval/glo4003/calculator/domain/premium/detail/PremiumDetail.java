package ca.ulaval.glo4003.calculator.domain.premium.detail;

import ca.ulaval.glo4003.calculator.domain.Coverage;
import ca.ulaval.glo4003.shared.domain.ValueObject;
import ca.ulaval.glo4003.shared.domain.money.Money;

public class PremiumDetail extends ValueObject {
  private final Coverage coverage;
  private final Money premium;

  public PremiumDetail(Coverage coverage, Money premium) {
    this.coverage = coverage;
    this.premium = premium;
  }

  public Coverage getCoverage() {
    return coverage;
  }

  public Money getPremium() {
    return premium;
  }
}
