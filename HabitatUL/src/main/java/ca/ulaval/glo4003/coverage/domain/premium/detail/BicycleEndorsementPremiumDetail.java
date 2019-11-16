package ca.ulaval.glo4003.coverage.domain.premium.detail;

import ca.ulaval.glo4003.shared.domain.money.Money;

import static ca.ulaval.glo4003.coverage.domain.premium.PremiumCategory.BICYCLE_ENDORSEMENT;

public class BicycleEndorsementPremiumDetail extends PremiumDetail {
  public BicycleEndorsementPremiumDetail(Money premium) {
    super(BICYCLE_ENDORSEMENT, premium);
  }
}
