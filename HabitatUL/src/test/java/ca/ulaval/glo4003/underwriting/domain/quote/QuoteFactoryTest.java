package ca.ulaval.glo4003.underwriting.domain.quote;

import ca.ulaval.glo4003.calculator.domain.coverage.detail.BikeEndorsementCoverageDetail;
import ca.ulaval.glo4003.calculator.domain.coverage.detail.CivilLiabilityCoverageDetail;
import ca.ulaval.glo4003.calculator.domain.coverage.detail.CoverageDetails;
import ca.ulaval.glo4003.calculator.domain.coverage.detail.PersonalPropertyCoverageDetail;
import ca.ulaval.glo4003.calculator.domain.premium.detail.BikeEndorsementPremiumDetail;
import ca.ulaval.glo4003.calculator.domain.premium.detail.PremiumDetails;
import ca.ulaval.glo4003.helper.MoneyGenerator;
import ca.ulaval.glo4003.helper.TemporalGenerator;
import ca.ulaval.glo4003.helper.calculator.CoverageDetailsBuilder;
import ca.ulaval.glo4003.helper.calculator.PremiumDetailsBuilder;
import ca.ulaval.glo4003.helper.quote.form.QuoteFormGenerator;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import ca.ulaval.glo4003.shared.domain.temporal.DateTime;
import ca.ulaval.glo4003.shared.domain.temporal.Period;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Duration;
import java.time.LocalDateTime;

import static ca.ulaval.glo4003.helper.calculator.PremiumDetailsGenerator.createPremiumDetails;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class QuoteFactoryTest {
  private static final ClockProvider CLOCK_PROVIDER = TemporalGenerator.getClockProvider();
  private static final Duration VALIDITY_PERIOD = TemporalGenerator.createDuration();
  private static final java.time.Period COVERAGE_PERIOD = TemporalGenerator.createJavaTimePeriod();
  private static final PremiumDetails PREMIUM_DETAILS = createPremiumDetails();
  private static final QuoteForm QUOTE_FORM = QuoteFormGenerator.createQuoteForm();

  @Mock private QuoteValidityPeriodProvider quoteValidityPeriodProvider;
  @Mock private QuoteEffectivePeriodProvider quoteEffectivePeriodProvider;

  private QuoteFactory subject;

  @Before
  public void setUp() {
    when(quoteValidityPeriodProvider.getQuoteValidityPeriod()).thenReturn(VALIDITY_PERIOD);
    when(quoteEffectivePeriodProvider.getQuoteEffectivePeriod()).thenReturn(COVERAGE_PERIOD);
    subject =
        new QuoteFactory(quoteValidityPeriodProvider, quoteEffectivePeriodProvider, CLOCK_PROVIDER);
  }

  @Test
  public void creatingQuote_shouldComputeExpirationDate() {
    Quote quote = subject.create(PREMIUM_DETAILS, QUOTE_FORM);

    DateTime expectedExpirationDate =
        DateTime.from(LocalDateTime.now(CLOCK_PROVIDER.getClock()).plus(VALIDITY_PERIOD));
    assertEquals(expectedExpirationDate, quote.getExpirationDate());
  }

  @Test
  public void creatingQuote_shouldComputeEffectivePeriod() {
    Quote quote = subject.create(PREMIUM_DETAILS, QUOTE_FORM);

    Period expectedEffectivePeriod =
        new Period(
            QUOTE_FORM.getEffectiveDate(), QUOTE_FORM.getEffectiveDate().plus(COVERAGE_PERIOD));
    assertEquals(expectedEffectivePeriod, quote.getEffectivePeriod());
  }

  @Test
  public void creatingQuote_shouldSummarizeCoverageDetails() {
    PremiumDetails premiumDetails =
        PremiumDetailsBuilder.aPremiumDetails().withoutAdditionalPremiumDetail().build();

    Quote quote = subject.create(premiumDetails, QUOTE_FORM);

    CoverageDetails expectedCoverageDetails =
        CoverageDetailsBuilder.aCoverageDetails()
            .withPersonalPropertyCoverageDetail(
                new PersonalPropertyCoverageDetail(
                    QUOTE_FORM.getPersonalProperty().getCoverageAmount()))
            .withCivilLiabilityCoverageDetail(
                new CivilLiabilityCoverageDetail(QUOTE_FORM.getCivilLiability().getLimit()))
            .withoutAdditionalCoverageDetail()
            .build();
    assertEquals(expectedCoverageDetails, quote.getCoverageDetails());
  }

  @Test
  public void creatingQuote_withBikeEndorsement_shouldSummarizeBikeEndorsementCoverageDetail() {
    PremiumDetails premiumDetails =
        PremiumDetailsBuilder.aPremiumDetails()
            .withAdditionalPremiumDetail(
                new BikeEndorsementPremiumDetail(MoneyGenerator.createMoney()))
            .build();

    Quote quote = subject.create(premiumDetails, QUOTE_FORM);

    CoverageDetails expectedCoverageDetails =
        CoverageDetailsBuilder.aCoverageDetails()
            .withPersonalPropertyCoverageDetail(
                new PersonalPropertyCoverageDetail(
                    QUOTE_FORM.getPersonalProperty().getCoverageAmount()))
            .withCivilLiabilityCoverageDetail(
                new CivilLiabilityCoverageDetail(QUOTE_FORM.getCivilLiability().getLimit()))
            .withAdditionalCoverageDetail(
                new BikeEndorsementCoverageDetail(
                    QUOTE_FORM.getPersonalProperty().getBike().getPrice()))
            .build();
    assertEquals(expectedCoverageDetails, quote.getCoverageDetails());
  }

  @Test
  public void creatingQuote_shouldCreateNotYetPurchasedQuote() {
    Quote quote = subject.create(PREMIUM_DETAILS, QUOTE_FORM);

    assertFalse(quote.isPurchased());
  }
}
