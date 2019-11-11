package ca.ulaval.glo4003.calculator.domain.coverage.detail;

import ca.ulaval.glo4003.calculator.domain.CoverageCategory;
import ca.ulaval.glo4003.shared.domain.money.Amount;

public class BikeEndorsementCoverageDetail extends CoverageDetail {
  public BikeEndorsementCoverageDetail(Amount bikePrice) {
    super(CoverageCategory.BIKE_ENDORSEMENT, bikePrice);
  }
}
