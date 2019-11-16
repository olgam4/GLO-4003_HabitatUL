package ca.ulaval.glo4003.coverage.domain.coverage.detail;

import ca.ulaval.glo4003.coverage.domain.CoverageCategory;
import ca.ulaval.glo4003.shared.domain.money.Amount;

public class PersonalPropertyCoverageDetail extends CoverageDetail {
  public PersonalPropertyCoverageDetail(Amount coverageAmount) {
    super(CoverageCategory.PERSONAL_PROPERTY, coverageAmount);
  }
}
