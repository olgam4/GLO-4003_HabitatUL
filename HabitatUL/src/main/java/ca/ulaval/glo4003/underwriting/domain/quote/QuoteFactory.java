package ca.ulaval.glo4003.underwriting.domain.quote;

import ca.ulaval.glo4003.calculator.domain.CoverageCategory;
import ca.ulaval.glo4003.calculator.domain.coverage.detail.BikeEndorsementCoverageDetail;
import ca.ulaval.glo4003.calculator.domain.coverage.detail.CivilLiabilityCoverageDetail;
import ca.ulaval.glo4003.calculator.domain.coverage.detail.CoverageDetails;
import ca.ulaval.glo4003.calculator.domain.coverage.detail.PersonalPropertyCoverageDetail;
import ca.ulaval.glo4003.calculator.domain.premium.detail.PremiumDetails;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import ca.ulaval.glo4003.shared.domain.temporal.Date;
import ca.ulaval.glo4003.shared.domain.temporal.DateTime;
import ca.ulaval.glo4003.shared.domain.temporal.Period;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;

public class QuoteFactory {
  private QuoteValidityPeriodProvider quoteValidityPeriodProvider;
  private QuoteEffectivePeriodProvider quoteEffectivePeriodProvider;
  private ClockProvider clockProvider;

  public QuoteFactory(
      QuoteValidityPeriodProvider quoteValidityPeriodProvider,
      QuoteEffectivePeriodProvider quoteEffectivePeriodProvider,
      ClockProvider clockProvider) {
    this.quoteValidityPeriodProvider = quoteValidityPeriodProvider;
    this.quoteEffectivePeriodProvider = quoteEffectivePeriodProvider;
    this.clockProvider = clockProvider;
  }

  public Quote create(PremiumDetails premiumDetails, QuoteForm quoteForm) {
    QuoteId quoteId = new QuoteId();
    DateTime expirationDate = createExpirationDate();
    Period effectivePeriod = createEffectivePeriod(quoteForm);
    CoverageDetails coverageDetails = createCoverageDetails(premiumDetails, quoteForm);

    return new Quote(
        quoteId,
        quoteForm,
        expirationDate,
        effectivePeriod,
        coverageDetails,
        premiumDetails,
        false,
        clockProvider);
  }

  private DateTime createExpirationDate() {
    return DateTime.now(clockProvider.getClock())
        .plus(quoteValidityPeriodProvider.getQuoteValidityPeriod());
  }

  private Period createEffectivePeriod(QuoteForm quoteForm) {
    Date effectivePeriodStartDate = quoteForm.getEffectiveDate();
    Date effectivePeriodEndDate =
        quoteForm.getEffectiveDate().plus(quoteEffectivePeriodProvider.getQuoteEffectivePeriod());
    return new Period(effectivePeriodStartDate, effectivePeriodEndDate);
  }

  private CoverageDetails createCoverageDetails(
      PremiumDetails premiumDetails, QuoteForm quoteForm) {
    CoverageDetails coverageDetails =
        new CoverageDetails(
            createPersonalPropertyCoverageDetail(quoteForm),
            createCivilLiabilityCoverageDetail(quoteForm));
    coverageDetails =
        addBikeEndorsementCoverageDetailOnDemand(premiumDetails, quoteForm, coverageDetails);
    return coverageDetails;
  }

  private PersonalPropertyCoverageDetail createPersonalPropertyCoverageDetail(QuoteForm quoteForm) {
    return new PersonalPropertyCoverageDetail(quoteForm.getPersonalProperty().getCoverageAmount());
  }

  private CivilLiabilityCoverageDetail createCivilLiabilityCoverageDetail(QuoteForm quoteForm) {
    return new CivilLiabilityCoverageDetail(quoteForm.getCivilLiability().getLimit());
  }

  private CoverageDetails addBikeEndorsementCoverageDetailOnDemand(
      PremiumDetails premiumDetails, QuoteForm quoteForm, CoverageDetails coverageDetails) {
    if (premiumDetails.includes(CoverageCategory.BIKE_ENDORSEMENT)) {
      coverageDetails =
          coverageDetails.addCoverageDetail(createBikeEndorsementCoverageDetail(quoteForm));
    }
    return coverageDetails;
  }

  private BikeEndorsementCoverageDetail createBikeEndorsementCoverageDetail(QuoteForm quoteForm) {
    return new BikeEndorsementCoverageDetail(quoteForm.getPersonalProperty().getBike().getPrice());
  }
}
