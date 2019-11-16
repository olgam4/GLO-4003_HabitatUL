package ca.ulaval.glo4003.coverage.application.coverage;

import ca.ulaval.glo4003.coverage.domain.coverage.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.coverage.detail.BicycleEndorsementCoverageDetail;
import ca.ulaval.glo4003.coverage.domain.coverage.detail.CivilLiabilityCoverageDetail;
import ca.ulaval.glo4003.coverage.domain.coverage.detail.PersonalPropertyCoverageDetail;
import ca.ulaval.glo4003.coverage.domain.form.BicycleEndorsementForm;
import ca.ulaval.glo4003.coverage.domain.form.QuoteForm;
import ca.ulaval.glo4003.shared.domain.money.Amount;

public class CoverageSummarizer {
  private AdditionalCoverageResolver additionalCoverageResolver;

  public CoverageSummarizer() {
    this(new AdditionalCoverageResolver());
  }

  public CoverageSummarizer(AdditionalCoverageResolver additionalCoverageResolver) {
    this.additionalCoverageResolver = additionalCoverageResolver;
  }

  public CoverageDetails summarizeQuoteCoverage(QuoteForm quoteForm) {
    CoverageDetails coverageDetails =
        new CoverageDetails(
            createPersonalPropertyCoverageDetail(quoteForm),
            createCivilLiabilityCoverageDetail(quoteForm));
    coverageDetails = addBicycleEndorsementOnDemand(quoteForm, coverageDetails);
    return coverageDetails;
  }

  private PersonalPropertyCoverageDetail createPersonalPropertyCoverageDetail(QuoteForm quoteForm) {
    return new PersonalPropertyCoverageDetail(quoteForm.getPersonalProperty().getCoverageAmount());
  }

  private CivilLiabilityCoverageDetail createCivilLiabilityCoverageDetail(QuoteForm quoteForm) {
    return new CivilLiabilityCoverageDetail(quoteForm.getCivilLiability().getCoverageAmount());
  }

  private CoverageDetails addBicycleEndorsementOnDemand(
      QuoteForm quoteForm, CoverageDetails coverageDetails) {
    if (additionalCoverageResolver.shouldIncludeBicycleEndorsement(quoteForm)) {
      coverageDetails = addBicycleEndorsement(quoteForm, coverageDetails);
    }
    return coverageDetails;
  }

  private CoverageDetails addBicycleEndorsement(
      QuoteForm quoteForm, CoverageDetails coverageDetails) {
    Amount bicyclePrice = quoteForm.getPersonalProperty().getBicycle().getPrice();
    coverageDetails =
        coverageDetails.addCoverageDetail(new BicycleEndorsementCoverageDetail(bicyclePrice));
    return coverageDetails;
  }

  public CoverageDetails summarizeBicycleEndorsementCoverage(
      BicycleEndorsementForm bicycleEndorsementForm) {
    Amount bicyclePrice = bicycleEndorsementForm.getBicycle().getPrice();
    CoverageDetails currentCoverageDetails = bicycleEndorsementForm.getCurrentCoverageDetails();
    BicycleEndorsementCoverageDetail updatedBicycleEndorsementCoverageDetail =
        new BicycleEndorsementCoverageDetail(bicyclePrice);
    return currentCoverageDetails.update(updatedBicycleEndorsementCoverageDetail);
  }
}
