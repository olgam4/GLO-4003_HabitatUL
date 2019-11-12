package ca.ulaval.glo4003.coverage.domain.coverage.detail;

import ca.ulaval.glo4003.coverage.domain.CoverageCategory;
import ca.ulaval.glo4003.shared.domain.money.Amount;

public class PersonalPropertyCoverageDetail extends CoverageDetail {
  public PersonalPropertyCoverageDetail(Amount personalPropertyCoverageAmount) {
    super(CoverageCategory.PERSONAL_PROPERTY, personalPropertyCoverageAmount);
  }
}