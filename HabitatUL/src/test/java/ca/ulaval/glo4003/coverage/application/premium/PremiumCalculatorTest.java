package ca.ulaval.glo4003.coverage.application.premium;

import ca.ulaval.glo4003.coverage.application.coverage.AdditionalCoverageResolver;
import ca.ulaval.glo4003.coverage.application.premium.assembler.PremiumAssembler;
import ca.ulaval.glo4003.coverage.domain.coverage.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.form.BicycleEndorsementForm;
import ca.ulaval.glo4003.coverage.domain.form.CoverageModificationForm;
import ca.ulaval.glo4003.coverage.domain.form.QuoteForm;
import ca.ulaval.glo4003.coverage.domain.premium.PremiumDetails;
import ca.ulaval.glo4003.coverage.domain.premium.formula.bicycleendorsement.BicycleEndorsementPremiumFormula;
import ca.ulaval.glo4003.coverage.domain.premium.formula.bicycleendorsement.BicycleEndorsementPremiumInput;
import ca.ulaval.glo4003.coverage.domain.premium.formula.coveragemodification.CoverageModificationPremiumFormula;
import ca.ulaval.glo4003.coverage.domain.premium.formula.coveragemodification.CoverageModificationPremiumInput;
import ca.ulaval.glo4003.coverage.domain.premium.formula.quote.QuoteBasicBlockPremiumFormula;
import ca.ulaval.glo4003.coverage.domain.premium.formula.quote.QuotePremiumInput;
import ca.ulaval.glo4003.helper.coverage.coverage.CoverageDetailsBuilder;
import ca.ulaval.glo4003.helper.coverage.form.BicycleEndorsementFormBuilder;
import ca.ulaval.glo4003.helper.coverage.form.CoverageModificationFormBuilder;
import ca.ulaval.glo4003.helper.coverage.premium.PremiumDetailsBuilder;
import ca.ulaval.glo4003.shared.domain.money.Money;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static ca.ulaval.glo4003.coverage.domain.form.civilliability.CivilLiabilityLimit.ONE_MILLION;
import static ca.ulaval.glo4003.coverage.domain.form.civilliability.CivilLiabilityLimit.TWO_MILLION;
import static ca.ulaval.glo4003.helper.coverage.form.QuoteFormGenerator.createQuoteForm;
import static ca.ulaval.glo4003.helper.coverage.premium.CoverageModificationPremiumInputGenerator.createCoverageModificationPremiumInput;
import static ca.ulaval.glo4003.helper.shared.MoneyGenerator.createMoney;
import static ca.ulaval.glo4003.matcher.CoverageMatcher.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
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

  @Mock private AdditionalCoverageResolver additionalCoverageResolver;
  @Mock private QuoteBasicBlockPremiumFormula quoteBasicBlockPremiumFormula;
  @Mock private BicycleEndorsementPremiumFormula bicycleEndorsementPremiumFormula;
  @Mock private CoverageModificationPremiumFormula coverageModificationPremiumFormula;

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
            coverageModificationPremiumFormula);
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
}
