package ca.ulaval.glo4003.coverage.domain.premium.detail;

import ca.ulaval.glo4003.coverage.domain.CoverageCategory;
import ca.ulaval.glo4003.shared.domain.money.Money;

public class BicycleEndorsementPremiumDetail extends PremiumDetail {
  public BicycleEndorsementPremiumDetail(Money premium) {
    super(CoverageCategory.BICYCLE_ENDORSEMENT, premium);
  }
}
