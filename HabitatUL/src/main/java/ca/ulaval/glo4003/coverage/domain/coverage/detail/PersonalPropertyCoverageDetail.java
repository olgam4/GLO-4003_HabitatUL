package ca.ulaval.glo4003.coverage.domain.coverage.detail;

import ca.ulaval.glo4003.shared.domain.money.Amount;

import static ca.ulaval.glo4003.coverage.domain.coverage.CoverageCategory.PERSONAL_PROPERTY;

public class PersonalPropertyCoverageDetail extends CoverageDetail {
  public PersonalPropertyCoverageDetail(Amount coverageAmount) {
    super(PERSONAL_PROPERTY, coverageAmount);
  }
}
