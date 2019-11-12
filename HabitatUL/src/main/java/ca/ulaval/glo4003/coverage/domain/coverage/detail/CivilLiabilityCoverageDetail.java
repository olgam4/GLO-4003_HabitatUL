package ca.ulaval.glo4003.coverage.domain.coverage.detail;

import ca.ulaval.glo4003.coverage.domain.CoverageCategory;
import ca.ulaval.glo4003.coverage.domain.form.civilliability.CivilLiabilityLimit;

public class CivilLiabilityCoverageDetail extends CoverageDetail {
  public CivilLiabilityCoverageDetail(CivilLiabilityLimit civilLiabilityLimit) {
    super(CoverageCategory.CIVIL_LIABILITY, civilLiabilityLimit.getAmount());
  }
}
