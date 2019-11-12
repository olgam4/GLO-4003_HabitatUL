package ca.ulaval.glo4003.coverage.domain.coverage.detail;

import ca.ulaval.glo4003.coverage.domain.CoverageCategory;
import ca.ulaval.glo4003.shared.domain.money.Amount;

public class BicycleEndorsementCoverageDetail extends CoverageDetail {
  public BicycleEndorsementCoverageDetail(Amount bicyclePrice) {
    super(CoverageCategory.BICYCLE_ENDORSEMENT, bicyclePrice);
  }
}
