package ca.ulaval.glo4003.coverage.domain.coverage.detail;

import ca.ulaval.glo4003.shared.domain.money.Amount;

import static ca.ulaval.glo4003.coverage.domain.coverage.CoverageCategory.BICYCLE_ENDORSEMENT;

public class BicycleEndorsementCoverageDetail extends CoverageDetail {
  public BicycleEndorsementCoverageDetail(Amount coverageAmount) {
    super(BICYCLE_ENDORSEMENT, coverageAmount);
  }
}
