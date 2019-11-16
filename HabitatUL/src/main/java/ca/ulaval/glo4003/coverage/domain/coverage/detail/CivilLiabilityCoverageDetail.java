package ca.ulaval.glo4003.coverage.domain.coverage.detail;

import ca.ulaval.glo4003.shared.domain.money.Amount;

import static ca.ulaval.glo4003.coverage.domain.coverage.CoverageCategory.CIVIL_LIABILITY;

public class CivilLiabilityCoverageDetail extends CoverageDetail {
  public CivilLiabilityCoverageDetail(Amount coverageAmount) {
    super(CIVIL_LIABILITY, coverageAmount);
  }
}
