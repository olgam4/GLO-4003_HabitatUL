package ca.ulaval.glo4003.coverage.application.premium;

import ca.ulaval.glo4003.coverage.application.PremiumAssembler;
import ca.ulaval.glo4003.coverage.application.coverage.AdditionalCoverageResolver;
import ca.ulaval.glo4003.coverage.domain.form.QuoteForm;
import ca.ulaval.glo4003.coverage.domain.premium.detail.BikeEndorsementPremiumDetail;
import ca.ulaval.glo4003.coverage.domain.premium.detail.PremiumDetails;
import ca.ulaval.glo4003.coverage.domain.premium.formula.bike.BikeEndorsementPremiumFormula;
import ca.ulaval.glo4003.coverage.domain.premium.formula.bike.BikePremiumInput;
import ca.ulaval.glo4003.coverage.domain.premium.formula.quote.QuoteBasicBlockPremiumFormula;
import ca.ulaval.glo4003.coverage.domain.premium.formula.quote.QuotePremiumInput;
import ca.ulaval.glo4003.helper.coverage.form.QuoteFormGenerator;
import ca.ulaval.glo4003.helper.coverage.premium.BikePremiumInputGenerator;
import ca.ulaval.glo4003.helper.coverage.premium.PremiumDetailsBuilder;
import ca.ulaval.glo4003.helper.shared.MoneyGenerator;
import ca.ulaval.glo4003.shared.domain.money.Money;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static ca.ulaval.glo4003.matcher.CoverageMatcher.matchesQuotePremiumInput;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.hamcrest.MockitoHamcrest.argThat;

@RunWith(MockitoJUnitRunner.class)
public class PremiumCalculatorTest {
  private static final QuoteForm QUOTE_FORM = QuoteFormGenerator.createQuoteForm();
  private static final BikePremiumInput BIKE_PREMIUM_INPUT = BikePremiumInputGenerator.create();
  private static final Money BASIC_BLOCK_PREMIUM = MoneyGenerator.createMoney();
  private static final Money BIKE_ENDORSEMENT_PREMIUM = MoneyGenerator.createMoney();

  @Mock private AdditionalCoverageResolver additionalCoverageResolver;
  @Mock private QuoteBasicBlockPremiumFormula quoteBasicBlockPremiumFormula;
  @Mock private BikeEndorsementPremiumFormula bikeEndorsementPremiumFormula;

  private PremiumCalculator subject;
  private PremiumAssembler premiumAssembler;

  @Before
  public void setUp() {
    when(additionalCoverageResolver.shouldIncludeBikeEndorsement(any(QuoteForm.class)))
        .thenReturn(false);
    when(quoteBasicBlockPremiumFormula.compute(any(QuotePremiumInput.class)))
        .thenReturn(BASIC_BLOCK_PREMIUM);
    when(bikeEndorsementPremiumFormula.compute(any(BikePremiumInput.class)))
        .thenReturn(BIKE_ENDORSEMENT_PREMIUM);
    premiumAssembler = new PremiumAssembler();
    subject =
        new PremiumCalculator(
            premiumAssembler,
            additionalCoverageResolver,
            quoteBasicBlockPremiumFormula,
            bikeEndorsementPremiumFormula);
  }

  @Test
  public void computingQuotePremium_shouldComputeQuoteBasicBlockPremiumFormula() {
    subject.computeQuotePremium(QUOTE_FORM);

    verify(quoteBasicBlockPremiumFormula).compute(argThat(matchesQuotePremiumInput(QUOTE_FORM)));
  }

  @Test
  public void
      computingQuotePremium_withoutBikeEndorsement_shouldReturnCorrespondingPremiumDetails() {
    PremiumDetails premiumDetails = subject.computeQuotePremium(QUOTE_FORM);

    PremiumDetails expectedPremiumDetails =
        PremiumDetailsBuilder.aPremiumDetails()
            .withBasicBlockCoveragePremiumDetail(BASIC_BLOCK_PREMIUM)
            .build();
    assertEquals(expectedPremiumDetails, premiumDetails);
  }

  @Test
  public void computingQuotePremium_withBikeEndorsement_shouldReturnCorrespondingPremiumDetails() {
    when(additionalCoverageResolver.shouldIncludeBikeEndorsement(any(QuoteForm.class)))
        .thenReturn(true);

    PremiumDetails premiumDetails = subject.computeQuotePremium(QUOTE_FORM);

    PremiumDetails expectedPremiumDetails =
        PremiumDetailsBuilder.aPremiumDetails()
            .withBasicBlockCoveragePremiumDetail(BASIC_BLOCK_PREMIUM)
            .withAdditionalPremiumDetail(new BikeEndorsementPremiumDetail(BIKE_ENDORSEMENT_PREMIUM))
            .build();
    assertEquals(expectedPremiumDetails, premiumDetails);
  }

  @Test
  public void computingBikeEndorsementPremium_shouldComputeBikeEndorsementPremiumFormula() {
    subject.computeBikeEndorsementPremium(BIKE_PREMIUM_INPUT);

    verify(bikeEndorsementPremiumFormula).compute(BIKE_PREMIUM_INPUT);
  }

  @Test
  public void computingBikeEndorsementPremium_shouldReturnComputedPremium() {
    Money endorsementPremium = subject.computeBikeEndorsementPremium(BIKE_PREMIUM_INPUT);

    assertEquals(BIKE_ENDORSEMENT_PREMIUM, endorsementPremium);
  }
}
