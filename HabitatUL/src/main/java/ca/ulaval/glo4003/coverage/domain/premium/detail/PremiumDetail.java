package ca.ulaval.glo4003.coverage.domain.premium.detail;

import ca.ulaval.glo4003.coverage.domain.premium.PremiumCategory;
import ca.ulaval.glo4003.shared.domain.ValueObject;
import ca.ulaval.glo4003.shared.domain.money.Money;

public abstract class PremiumDetail extends ValueObject {
  private final PremiumCategory coverage;
  private final Money premium;

  public PremiumDetail(PremiumCategory coverage, Money premium) {
    this.coverage = coverage;
    this.premium = premium;
  }

  public PremiumCategory getCoverage() {
    return coverage;
  }

  public Money getPremium() {
    return premium;
  }
}
