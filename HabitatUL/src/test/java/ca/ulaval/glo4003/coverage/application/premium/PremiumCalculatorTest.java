package ca.ulaval.glo4003.coverage.application.premium;

import ca.ulaval.glo4003.coverage.application.coverage.AdditionalCoverageResolver;
import ca.ulaval.glo4003.coverage.application.premium.assembler.PremiumAssembler;
import ca.ulaval.glo4003.coverage.application.premium.error.CannotComputeModificationPremiumAdjustmentError;
import ca.ulaval.glo4003.coverage.domain.coverage.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.form.BicycleEndorsementForm;
import ca.ulaval.glo4003.coverage.domain.form.CoverageModificationForm;
import ca.ulaval.glo4003.coverage.domain.form.CoverageRenewalForm;
import ca.ulaval.glo4003.coverage.domain.form.QuoteForm;
import ca.ulaval.glo4003.coverage.domain.premium.PremiumDetails;
import ca.ulaval.glo4003.coverage.domain.premium.formula.bicycleendorsement.BicycleEndorsementPremiumFormula;
import ca.ulaval.glo4003.coverage.domain.premium.formula.bicycleendorsement.BicycleEndorsementPremiumInput;
import ca.ulaval.glo4003.coverage.domain.premium.formula.coveragemodification.CoverageModificationPremiumFormula;
import ca.ulaval.glo4003.coverage.domain.premium.formula.coveragemodification.CoverageModificationPremiumInput;
import ca.ulaval.glo4003.coverage.domain.premium.formula.quote.QuoteBasicBlockPremiumFormula;
import ca.ulaval.glo4003.coverage.domain.premium.formula.quote.QuotePremiumInput;
import ca.ulaval.glo4003.coverage.helper.coverage.CoverageDetailsBuilder;
import ca.ulaval.glo4003.coverage.helper.form.BicycleEndorsementFormBuilder;
import ca.ulaval.glo4003.coverage.helper.form.CoverageModificationFormBuilder;
import ca.ulaval.glo4003.coverage.helper.form.CoverageRenewalFormBuilder;
import ca.ulaval.glo4003.coverage.helper.premium.PremiumDetailsBuilder;
import ca.ulaval.glo4003.shared.application.logging.Logger;
import ca.ulaval.glo4003.shared.domain.money.Money;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static ca.ulaval.glo4003.coverage.application.premium.PremiumCalculator.RENEWAL_PREMIUM_ADJUSTMENT_FACTOR;
import static ca.ulaval.glo4003.coverage.domain.form.civilliability.CivilLiabilityLimit.ONE_MILLION;
import static ca.ulaval.glo4003.coverage.domain.form.civilliability.CivilLiabilityLimit.TWO_MILLION;
import static ca.ulaval.glo4003.coverage.helper.form.QuoteFormGenerator.createQuoteForm;
import static ca.ulaval.glo4003.coverage.helper.premium.CoverageModificationPremiumInputGenerator.createCoverageModificationPremiumInput;
import static ca.ulaval.glo4003.coverage.matcher.CoverageMatcher.*;
import static ca.ulaval.glo4003.shared.helper.MoneyGenerator.createAmount;
import static ca.ulaval.glo4003.shared.helper.MoneyGenerator.createMoney;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.hamcrest.MockitoHamcrest.argThat;

@RunWith(MockitoJUnitRunner.class)
public class PremiumCalculatorTest {
  private static final QuoteForm QUOTE_FORM = createQuoteForm();
  private static final Money BASIC_BLOCK_PREMIUM = createMoney();
  private static final Money BICYCLE_ENDORSEMENT_PREMIUM = createMoney();
  private static final PremiumDetails CURRENT_PREMIUM_DETAILS =
      PremiumDetailsBuilder.aPremiumDetails()
          .withBasicBlockPremiumDetail(BASIC_BLOCK_PREMIUM)
          .build();
  private static final BicycleEndorsementForm BICYCLE_ENDORSEMENT_FORM =
      BicycleEndorsementFormBuilder.aBicycleEndorsementForm()
          .withCurrentPremiumDetails(CURRENT_PREMIUM_DETAILS)
          .build();
  private static final CoverageModificationPremiumInput
      COVERAGE_MODIFICATION_CURRENT_VIEW_PREMIUM_INPUT =
          createCoverageModificationPremiumInput(ONE_MILLION);
  private static final CoverageModificationPremiumInput
      COVERAGE_MODIFICATION_UPDATED_VIEW_PREMIUM_INPUT =
          createCoverageModificationPremiumInput(TWO_MILLION);
  private static final Money COVERAGE_MODIFICATION_CURRENT_VIEW_PREMIUM = createMoney();
  private static final Money COVERAGE_MODIFICATION_UPDATED_VIEW_PREMIUM = createMoney();
  private static final CoverageDetails CURRENT_COVERAGE_DETAILS =
      CoverageDetailsBuilder.aCoverageDetails()
          .withCivilLiabilityCoverageDetail(
              COVERAGE_MODIFICATION_CURRENT_VIEW_PREMIUM_INPUT.getCivilLiabilityLimit().getAmount())
          .build();
  private static final CoverageModificationForm COVERAGE_MODIFICATION_FORM =
      CoverageModificationFormBuilder.aCoverageModificationForm()
          .withCivilLiabilityLimit(
              COVERAGE_MODIFICATION_UPDATED_VIEW_PREMIUM_INPUT.getCivilLiabilityLimit())
          .withCurrentCoverageDetails(CURRENT_COVERAGE_DETAILS)
          .withCurrentPremiumDetails(CURRENT_PREMIUM_DETAILS)
          .build();
  private static final CoverageRenewalForm COVERAGE_RENEWAL_FORM =
      CoverageRenewalFormBuilder.aCoverageRenewalForm()
          .withCurrentPremiumDetails(CURRENT_PREMIUM_DETAILS)
          .build();
  private static final PremiumDetails CURRENT_PREMIUM_DETAILS_WITH_ADDITIONAL_COVERAGE =
      PremiumDetailsBuilder.aPremiumDetails()
          .withBasicBlockPremiumDetail(BASIC_BLOCK_PREMIUM)
          .withBicycleEndorsementPremiumDetail(BICYCLE_ENDORSEMENT_PREMIUM)
          .build();
  private static final CoverageRenewalForm COVERAGE_RENEWAL_FORM_WITH_ADDITIONAL_COVERAGE =
      CoverageRenewalFormBuilder.aCoverageRenewalForm()
          .withCurrentPremiumDetails(CURRENT_PREMIUM_DETAILS_WITH_ADDITIONAL_COVERAGE)
          .build();

  @Mock private AdditionalCoverageResolver additionalCoverageResolver;
  @Mock private QuoteBasicBlockPremiumFormula quoteBasicBlockPremiumFormula;
  @Mock private BicycleEndorsementPremiumFormula bicycleEndorsementPremiumFormula;
  @Mock private CoverageModificationPremiumFormula coverageModificationPremiumFormula;
  @Mock private Logger logger;

  private PremiumCalculator subject;
  private PremiumAssembler premiumAssembler;

  @Before
  public void setUp() {
    when(additionalCoverageResolver.shouldIncludeBicycleEndorsement(any(QuoteForm.class)))
        .thenReturn(false);
    when(quoteBasicBlockPremiumFormula.compute(any(QuotePremiumInput.class)))
        .thenReturn(BASIC_BLOCK_PREMIUM);
    when(bicycleEndorsementPremiumFormula.compute(any(BicycleEndorsementPremiumInput.class)))
        .thenReturn(BICYCLE_ENDORSEMENT_PREMIUM);
    when(coverageModificationPremiumFormula.compute(
            COVERAGE_MODIFICATION_CURRENT_VIEW_PREMIUM_INPUT))
        .thenReturn(COVERAGE_MODIFICATION_CURRENT_VIEW_PREMIUM);
    when(coverageModificationPremiumFormula.compute(
            COVERAGE_MODIFICATION_UPDATED_VIEW_PREMIUM_INPUT))
        .thenReturn(COVERAGE_MODIFICATION_UPDATED_VIEW_PREMIUM);
    premiumAssembler = new PremiumAssembler();
    subject =
        new PremiumCalculator(
            premiumAssembler,
            additionalCoverageResolver,
            quoteBasicBlockPremiumFormula,
            bicycleEndorsementPremiumFormula,
            coverageModificationPremiumFormula,
            logger);
  }

  @Test
  public void computingQuotePremium_shouldComputeQuoteBasicBlockPremiumFormula() {
    subject.computeQuotePremium(QUOTE_FORM);

    verify(quoteBasicBlockPremiumFormula).compute(argThat(matchesQuotePremiumInput(QUOTE_FORM)));
  }

  @Test
  public void
      computingQuotePremium_withoutBicycleEndorsement_shouldReturnCorrespondingPremiumDetails() {
    PremiumDetails premiumDetails = subject.computeQuotePremium(QUOTE_FORM);

    PremiumDetails expectedPremiumDetails =
        PremiumDetailsBuilder.aPremiumDetails()
            .withBasicBlockPremiumDetail(BASIC_BLOCK_PREMIUM)
            .build();
    assertEquals(expectedPremiumDetails, premiumDetails);
  }

  @Test
  public void
      computingQuotePremium_withBicycleEndorsement_shouldReturnCorrespondingPremiumDetails() {
    when(additionalCoverageResolver.shouldIncludeBicycleEndorsement(any(QuoteForm.class)))
        .thenReturn(true);

    PremiumDetails premiumDetails = subject.computeQuotePremium(QUOTE_FORM);

    PremiumDetails expectedPremiumDetails =
        PremiumDetailsBuilder.aPremiumDetails()
            .withBasicBlockPremiumDetail(BASIC_BLOCK_PREMIUM)
            .withBicycleEndorsementPremiumDetail(BICYCLE_ENDORSEMENT_PREMIUM)
            .build();
    assertEquals(expectedPremiumDetails, premiumDetails);
  }

  @Test
  public void computingBicycleEndorsementPremium_shouldComputeBicycleEndorsementPremiumFormula() {
    subject.computeBicycleEndorsementPremium(BICYCLE_ENDORSEMENT_FORM);

    verify(bicycleEndorsementPremiumFormula)
        .compute(argThat(matchesBicycleEndorsementPremiumInput(BICYCLE_ENDORSEMENT_FORM)));
  }

  @Test
  public void computingBicycleEndorsementPremium_shouldReturnUpdatedPremiumDetails() {
    PremiumDetails premiumDetails =
        subject.computeBicycleEndorsementPremium(BICYCLE_ENDORSEMENT_FORM);

    PremiumDetails expectedPremiumDetails =
        PremiumDetailsBuilder.aPremiumDetails()
            .withBasicBlockPremiumDetail(BASIC_BLOCK_PREMIUM)
            .withBicycleEndorsementPremiumDetail(BICYCLE_ENDORSEMENT_PREMIUM)
            .build();
    assertEquals(expectedPremiumDetails, premiumDetails);
  }

  @Test
  public void
      computingCoverageModificationPremium_shouldComputeCoverageModificationPremiumFormulaForCurrentView() {
    subject.computeCoverageModificationPremium(COVERAGE_MODIFICATION_FORM);

    verify(coverageModificationPremiumFormula)
        .compute(
            argThat(matchesCurrentCoverageModificationPremiumInput(COVERAGE_MODIFICATION_FORM)));
  }

  @Test
  public void
      computingCoverageModificationPremium_shouldComputeCoverageModificationPremiumFormulaForUpdatedView() {
    subject.computeCoverageModificationPremium(COVERAGE_MODIFICATION_FORM);

    verify(coverageModificationPremiumFormula)
        .compute(
            argThat(matchesUpdatedCoverageModificationPremiumInput(COVERAGE_MODIFICATION_FORM)));
  }

  @Test
  public void computingCoverageModificationPremium_shouldReturnUpdatedPremiumDetails() {
    PremiumDetails premiumDetails =
        subject.computeCoverageModificationPremium(COVERAGE_MODIFICATION_FORM);

    PremiumDetails expectedPremiumDetails =
        PremiumDetailsBuilder.aPremiumDetails()
            .withBasicBlockPremiumDetail(
                BASIC_BLOCK_PREMIUM.add(
                    COVERAGE_MODIFICATION_UPDATED_VIEW_PREMIUM.subtract(
                        COVERAGE_MODIFICATION_CURRENT_VIEW_PREMIUM)))
            .build();
    assertEquals(expectedPremiumDetails, premiumDetails);
  }

  @Test(expected = CannotComputeModificationPremiumAdjustmentError.class)
  public void computingCoverageModificationPremium_withInvalidCivilLiabilityAmount_shouldThrow() {
    CoverageDetails coverageDetails =
        CoverageDetailsBuilder.aCoverageDetails()
            .withCivilLiabilityCoverageDetail(createAmount())
            .build();
    CoverageModificationForm coverageModificationForm =
        CoverageModificationFormBuilder.aCoverageModificationForm()
            .withCurrentCoverageDetails(coverageDetails)
            .build();

    subject.computeCoverageModificationPremium(coverageModificationForm);
  }

  @Test
  public void
      computingCoverageModificationPremium_withInvalidCivilLiabilityAmount_shouldLogErrorAsSevere() {
    CoverageDetails coverageDetails =
        CoverageDetailsBuilder.aCoverageDetails()
            .withCivilLiabilityCoverageDetail(createAmount())
            .build();
    CoverageModificationForm coverageModificationForm =
        CoverageModificationFormBuilder.aCoverageModificationForm()
            .withCurrentCoverageDetails(coverageDetails)
            .build();

    try {
      subject.computeCoverageModificationPremium(coverageModificationForm);
    } catch (Exception e) {
      verify(logger).severe(anyString());
    }
  }

  @Test
  public void
      computingCoverageRenewalPremium_withoutAdditionalCoverage_shouldReturnUpdatedPremiumDetails() {
    PremiumDetails premiumDetails = subject.computeCoverageRenewalPremium(COVERAGE_RENEWAL_FORM);

    PremiumDetails expectedPremiumDetails =
        PremiumDetailsBuilder.aPremiumDetails()
            .withBasicBlockPremiumDetail(
                BASIC_BLOCK_PREMIUM.multiply(RENEWAL_PREMIUM_ADJUSTMENT_FACTOR))
            .build();
    assertEquals(expectedPremiumDetails, premiumDetails);
  }

  @Test
  public void
      computingCoverageRenewalPremium_withAdditionalCoverage_shouldReturnUpdatedPremiumDetails() {
    PremiumDetails premiumDetails =
        subject.computeCoverageRenewalPremium(COVERAGE_RENEWAL_FORM_WITH_ADDITIONAL_COVERAGE);

    PremiumDetails expectedPremiumDetails =
        PremiumDetailsBuilder.aPremiumDetails()
            .withBasicBlockPremiumDetail(
                BASIC_BLOCK_PREMIUM.multiply(RENEWAL_PREMIUM_ADJUSTMENT_FACTOR))
            .withBicycleEndorsementPremiumDetail(
                BICYCLE_ENDORSEMENT_PREMIUM.multiply(RENEWAL_PREMIUM_ADJUSTMENT_FACTOR))
            .build();
    assertEquals(expectedPremiumDetails, premiumDetails);
  }
}
