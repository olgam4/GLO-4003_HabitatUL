package ca.ulaval.glo4003.calculator.domain.premium.detail;

import ca.ulaval.glo4003.calculator.domain.CoverageCategory;
import ca.ulaval.glo4003.shared.domain.money.Money;

public class BikeEndorsementPremiumDetail extends PremiumDetail {
  public BikeEndorsementPremiumDetail(Money premium) {
    super(CoverageCategory.BIKE_ENDORSEMENT, premium);
  }
}
