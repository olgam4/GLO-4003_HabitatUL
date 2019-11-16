package ca.ulaval.glo4003.coverage.domain.coverage.detail;

import ca.ulaval.glo4003.coverage.domain.CoverageCategory;
import ca.ulaval.glo4003.shared.domain.money.Amount;

public class CivilLiabilityCoverageDetail extends CoverageDetail {
  public CivilLiabilityCoverageDetail(Amount coverageAmount) {
    super(CoverageCategory.CIVIL_LIABILITY, coverageAmount);
  }
}
