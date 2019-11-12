package ca.ulaval.glo4003.coverage.application.coverage;

import ca.ulaval.glo4003.coverage.domain.coverage.detail.BikeEndorsementCoverageDetail;
import ca.ulaval.glo4003.coverage.domain.coverage.detail.CivilLiabilityCoverageDetail;
import ca.ulaval.glo4003.coverage.domain.coverage.detail.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.coverage.detail.PersonalPropertyCoverageDetail;
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
    coverageDetails = addBikeEndorsementOnDemand(quoteForm, coverageDetails);
    return coverageDetails;
  }

  private PersonalPropertyCoverageDetail createPersonalPropertyCoverageDetail(QuoteForm quoteForm) {
    return new PersonalPropertyCoverageDetail(quoteForm.getPersonalProperty().getCoverageAmount());
  }

  private CivilLiabilityCoverageDetail createCivilLiabilityCoverageDetail(QuoteForm quoteForm) {
    return new CivilLiabilityCoverageDetail(quoteForm.getCivilLiability().getLimit());
  }

  private CoverageDetails addBikeEndorsementOnDemand(
      QuoteForm quoteForm, CoverageDetails coverageDetails) {
    if (additionalCoverageResolver.shouldIncludeBikeEndorsement(quoteForm)) {
      coverageDetails = addBikeEndorsement(quoteForm, coverageDetails);
    }
    return coverageDetails;
  }

  private CoverageDetails addBikeEndorsement(QuoteForm quoteForm, CoverageDetails coverageDetails) {
    Amount bikePrice = quoteForm.getPersonalProperty().getBike().getPrice();
    coverageDetails =
        coverageDetails.addCoverageDetail(new BikeEndorsementCoverageDetail(bikePrice));
    return coverageDetails;
  }
}
