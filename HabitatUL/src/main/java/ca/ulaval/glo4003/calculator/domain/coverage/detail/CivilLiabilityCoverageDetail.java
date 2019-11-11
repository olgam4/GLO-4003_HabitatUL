package ca.ulaval.glo4003.calculator.domain.coverage.detail;

import ca.ulaval.glo4003.calculator.domain.CoverageCategory;
import ca.ulaval.glo4003.underwriting.domain.quote.form.civilliability.CivilLiabilityLimit;

public class CivilLiabilityCoverageDetail extends CoverageDetail {
  public CivilLiabilityCoverageDetail(CivilLiabilityLimit civilLiabilityLimit) {
    super(CoverageCategory.CIVIL_LIABILITY, civilLiabilityLimit.getAmount());
  }
}
