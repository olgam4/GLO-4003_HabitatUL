package ca.ulaval.glo4003.helper.coverage.coverage;

import ca.ulaval.glo4003.coverage.domain.coverage.detail.CivilLiabilityCoverageDetail;
import ca.ulaval.glo4003.coverage.domain.coverage.detail.CoverageDetail;
import ca.ulaval.glo4003.coverage.domain.coverage.detail.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.coverage.detail.PersonalPropertyCoverageDetail;
import ca.ulaval.glo4003.coverage.domain.form.civilliability.CivilLiabilityLimit;
import ca.ulaval.glo4003.shared.domain.money.Amount;

import java.util.ArrayList;
import java.util.List;

import static ca.ulaval.glo4003.helper.coverage.coverage.CoverageDetailsGenerator.createCivilLiabilityCoverageDetail;
import static ca.ulaval.glo4003.helper.coverage.coverage.CoverageDetailsGenerator.createPersonalPropertyCoverageDetail;

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

  public CoverageDetailsBuilder withPersonalPropertyCoverageDetail(Amount coverageAmount) {
    this.personalPropertyCoverageDetail = new PersonalPropertyCoverageDetail(coverageAmount);
    return this;
  }

  public CoverageDetailsBuilder withCivilLiabilityCoverageDetail(
      CivilLiabilityLimit civilLiabilityLimit) {
    this.civilLiabilityCoverageDetail = new CivilLiabilityCoverageDetail(civilLiabilityLimit);
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
