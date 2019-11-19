package ca.ulaval.glo4003.coverage.application.coverage;

import ca.ulaval.glo4003.coverage.domain.coverage.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.coverage.detail.BicycleEndorsementCoverageDetail;
import ca.ulaval.glo4003.coverage.domain.coverage.detail.CivilLiabilityCoverageDetail;
import ca.ulaval.glo4003.coverage.domain.coverage.detail.CoverageDetail;
import ca.ulaval.glo4003.coverage.domain.coverage.detail.PersonalPropertyCoverageDetail;
import ca.ulaval.glo4003.coverage.domain.form.BicycleEndorsementForm;
import ca.ulaval.glo4003.coverage.domain.form.CoverageModificationForm;
import ca.ulaval.glo4003.coverage.domain.form.QuoteForm;
import ca.ulaval.glo4003.coverage.domain.form.civilliability.CivilLiabilityLimit;
import ca.ulaval.glo4003.shared.domain.money.Amount;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    BicycleEndorsementCoverageDetail updatedBicycleEndorsementCoverageDetail =
        new BicycleEndorsementCoverageDetail(bicyclePrice);
    CoverageDetails currentCoverageDetails = bicycleEndorsementForm.getCurrentCoverageDetails();
    return currentCoverageDetails.update(updatedBicycleEndorsementCoverageDetail);
  }

  public CoverageDetails summarizeCoverageModification(
      CoverageModificationForm coverageModificationForm) {
    List<CoverageDetail> updatedCoverageDetails = new ArrayList<>();
    Amount coverageAmount = coverageModificationForm.getCoverageAmount();
    updatePersonalPropertyCoverageDetailOnDemand(updatedCoverageDetails, coverageAmount);
    updateCivilLiabilityLimitCoverageDetailOnDemand(
        coverageModificationForm, updatedCoverageDetails);
    CoverageDetails currentCoverageDetails = coverageModificationForm.getCurrentCoverageDetails();
    return currentCoverageDetails.update(updatedCoverageDetails);
  }

  private void updatePersonalPropertyCoverageDetailOnDemand(
      List<CoverageDetail> updatedCoverageDetails, Amount coverageAmount) {
    Optional.ofNullable(coverageAmount)
        .ifPresent(x -> updatedCoverageDetails.add(new PersonalPropertyCoverageDetail(x)));
  }

  private void updateCivilLiabilityLimitCoverageDetailOnDemand(
      CoverageModificationForm coverageModificationForm,
      List<CoverageDetail> updatedCoverageDetails) {
    CivilLiabilityLimit civilLiabilityLimit = coverageModificationForm.getCivilLiabilityLimit();
    Optional.ofNullable(civilLiabilityLimit)
        .ifPresent(
            x -> updatedCoverageDetails.add(new CivilLiabilityCoverageDetail(x.getAmount())));
  }
}
