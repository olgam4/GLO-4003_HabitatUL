package ca.ulaval.glo4003.helper.calculator;

import ca.ulaval.glo4003.calculator.domain.coverage.detail.CivilLiabilityCoverageDetail;
import ca.ulaval.glo4003.calculator.domain.coverage.detail.CoverageDetail;
import ca.ulaval.glo4003.calculator.domain.coverage.detail.CoverageDetails;
import ca.ulaval.glo4003.calculator.domain.coverage.detail.PersonalPropertyCoverageDetail;

import java.util.ArrayList;
import java.util.List;

import static ca.ulaval.glo4003.helper.calculator.CoverageDetailsGenerator.createCivilLiabilityCoverageDetail;
import static ca.ulaval.glo4003.helper.calculator.CoverageDetailsGenerator.createPersonalPropertyCoverageDetail;

public class CoverageDetailsBuilder {
  private static final PersonalPropertyCoverageDetail DEFAULT_PERSONAL_PROPERTY_COVERAGE_DETAIL =
      createPersonalPropertyCoverageDetail();
  private static final CivilLiabilityCoverageDetail DEFAULT_CIVIL_LIABILITY_COVERAGE_DETAIL =
      createCivilLiabilityCoverageDetail();

  private PersonalPropertyCoverageDetail personalPropertyCoverageDetail =
      DEFAULT_PERSONAL_PROPERTY_COVERAGE_DETAIL;
  private CivilLiabilityCoverageDetail civilLiabilityCoverageDetail =
      DEFAULT_CIVIL_LIABILITY_COVERAGE_DETAIL;
  private List<CoverageDetail> additionalCoverageDetails = new ArrayList<>();

  private CoverageDetailsBuilder() {}

  public static CoverageDetailsBuilder aCoverageDetails() {
    return new CoverageDetailsBuilder();
  }

  public CoverageDetailsBuilder withPersonalPropertyCoverageDetail(
      PersonalPropertyCoverageDetail personalPropertyCoverageDetail) {
    this.personalPropertyCoverageDetail = personalPropertyCoverageDetail;
    return this;
  }

  public CoverageDetailsBuilder withCivilLiabilityCoverageDetail(
      CivilLiabilityCoverageDetail civilLiabilityCoverageDetail) {
    this.civilLiabilityCoverageDetail = civilLiabilityCoverageDetail;
    return this;
  }

  public CoverageDetailsBuilder withAdditionalCoverageDetail(CoverageDetail coverageDetail) {
    this.additionalCoverageDetails.add(coverageDetail);
    return this;
  }

  public CoverageDetailsBuilder withoutAdditionalCoverageDetail() {
    this.additionalCoverageDetails = new ArrayList<>();
    return this;
  }

  public CoverageDetails build() {
    CoverageDetails coverageDetails =
        new CoverageDetails(personalPropertyCoverageDetail, civilLiabilityCoverageDetail);
    for (CoverageDetail coverageDetail : additionalCoverageDetails) {
      coverageDetails = coverageDetails.addCoverageDetail(coverageDetail);
    }
    return coverageDetails;
  }
}
