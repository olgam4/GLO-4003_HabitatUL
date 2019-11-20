package ca.ulaval.glo4003.coverage.application.premium;

import ca.ulaval.glo4003.coverage.application.coverage.AdditionalCoverageResolver;
import ca.ulaval.glo4003.coverage.application.premium.assembler.BicycleEndorsementPremiumFormulaAssembler;
import ca.ulaval.glo4003.coverage.application.premium.assembler.CoverageModificationPremiumFormulaAssembler;
import ca.ulaval.glo4003.coverage.application.premium.assembler.PremiumAssembler;
import ca.ulaval.glo4003.coverage.application.premium.assembler.QuoteBasicBlockPremiumFormulaAssembler;
import ca.ulaval.glo4003.coverage.domain.form.BicycleEndorsementForm;
import ca.ulaval.glo4003.coverage.domain.form.CoverageModificationForm;
import ca.ulaval.glo4003.coverage.domain.form.CoverageRenewalForm;
import ca.ulaval.glo4003.coverage.domain.form.QuoteForm;
import ca.ulaval.glo4003.coverage.domain.premium.PremiumCategory;
import ca.ulaval.glo4003.coverage.domain.premium.PremiumDetails;
import ca.ulaval.glo4003.coverage.domain.premium.detail.BasicBlockCoveragePremiumDetail;
import ca.ulaval.glo4003.coverage.domain.premium.detail.BicycleEndorsementPremiumDetail;
import ca.ulaval.glo4003.coverage.domain.premium.detail.PremiumDetail;
import ca.ulaval.glo4003.coverage.domain.premium.formula.bicycleendorsement.BicycleEndorsementPremiumFormula;
import ca.ulaval.glo4003.coverage.domain.premium.formula.bicycleendorsement.BicycleEndorsementPremiumInput;
import ca.ulaval.glo4003.coverage.domain.premium.formula.coveragemodification.CoverageModificationPremiumFormula;
import ca.ulaval.glo4003.coverage.domain.premium.formula.coveragemodification.CoverageModificationPremiumInput;
import ca.ulaval.glo4003.coverage.domain.premium.formula.quote.QuoteBasicBlockPremiumFormula;
import ca.ulaval.glo4003.coverage.domain.premium.formula.quote.QuotePremiumInput;
import ca.ulaval.glo4003.shared.domain.handling.InvalidArgumentException;
import ca.ulaval.glo4003.shared.domain.money.Money;

import static ca.ulaval.glo4003.coverage.domain.premium.PremiumCategory.BASIC_BLOCK;

public class PremiumCalculator {
  static final float RENEWAL_PREMIUM_ADJUSTMENT_FACTOR = 1.05f;

  private PremiumAssembler premiumAssembler;
  private AdditionalCoverageResolver additionalCoverageResolver;
  private QuoteBasicBlockPremiumFormula quoteBasicBlockPremiumFormula;
  private BicycleEndorsementPremiumFormula bicycleEndorsementPremiumFormula;
  private CoverageModificationPremiumFormula coverageModificationPremiumFormula;

  public PremiumCalculator() {
    this(
        new PremiumAssembler(),
        new AdditionalCoverageResolver(),
        QuoteBasicBlockPremiumFormulaAssembler.assemble(),
        BicycleEndorsementPremiumFormulaAssembler.assemble(),
        CoverageModificationPremiumFormulaAssembler.assemble());
  }

  public PremiumCalculator(
      PremiumAssembler premiumAssembler,
      AdditionalCoverageResolver additionalCoverageResolver,
      QuoteBasicBlockPremiumFormula quoteBasicBlockPremiumFormula,
      BicycleEndorsementPremiumFormula bicycleEndorsementPremiumFormula,
      CoverageModificationPremiumFormula coverageModificationPremiumFormula) {
    this.premiumAssembler = premiumAssembler;
    this.additionalCoverageResolver = additionalCoverageResolver;
    this.quoteBasicBlockPremiumFormula = quoteBasicBlockPremiumFormula;
    this.bicycleEndorsementPremiumFormula = bicycleEndorsementPremiumFormula;
    this.coverageModificationPremiumFormula = coverageModificationPremiumFormula;
  }

  public PremiumDetails computeQuotePremium(QuoteForm quoteForm) {
    QuotePremiumInput quotePremiumInput = premiumAssembler.toQuotePremiumInput(quoteForm);
    Money basicBlockPremium = quoteBasicBlockPremiumFormula.compute(quotePremiumInput);
    PremiumDetails premiumDetails =
        new PremiumDetails(new BasicBlockCoveragePremiumDetail(basicBlockPremium));
    premiumDetails = addBicycleEndorsementOnDemand(quoteForm, premiumDetails);
    return premiumDetails;
  }

  private PremiumDetails addBicycleEndorsementOnDemand(
      QuoteForm quoteForm, PremiumDetails premiumDetails) {
    if (additionalCoverageResolver.shouldIncludeBicycleEndorsement(quoteForm)) {
      BicycleEndorsementPremiumInput bicycleEndorsementPremiumInput =
          premiumAssembler.toBicycleEndorsementPremiumInput(quoteForm);
      premiumDetails = addBicycleEndorsement(bicycleEndorsementPremiumInput, premiumDetails);
    }
    return premiumDetails;
  }

  private PremiumDetails addBicycleEndorsement(
      BicycleEndorsementPremiumInput bicycleEndorsementPremiumInput,
      PremiumDetails premiumDetails) {
    Money bicycleEndorsementPremium =
        computeBicycleEndorsementPremium(bicycleEndorsementPremiumInput);
    premiumDetails =
        premiumDetails.addPremiumDetail(
            new BicycleEndorsementPremiumDetail(bicycleEndorsementPremium));
    return premiumDetails;
  }

  public PremiumDetails computeBicycleEndorsementPremium(
      BicycleEndorsementForm bicycleEndorsementForm) {
    BicycleEndorsementPremiumInput bicycleEndorsementPremiumInput =
        premiumAssembler.toBicycleEndorsementPremiumInput(bicycleEndorsementForm);
    Money bicycleEndorsementPremium =
        computeBicycleEndorsementPremium(bicycleEndorsementPremiumInput);
    PremiumDetails currentPremiumDetails = bicycleEndorsementForm.getCurrentPremiumDetails();
    BicycleEndorsementPremiumDetail updatedEndorsementPremiumDetail =
        new BicycleEndorsementPremiumDetail(bicycleEndorsementPremium);
    return currentPremiumDetails.update(updatedEndorsementPremiumDetail);
  }

  private Money computeBicycleEndorsementPremium(
      BicycleEndorsementPremiumInput bicycleEndorsementPremiumInput) {
    return bicycleEndorsementPremiumFormula.compute(bicycleEndorsementPremiumInput);
  }

  public PremiumDetails computeCoverageModificationPremium(
      CoverageModificationForm coverageModificationForm) {
    Money premiumAdjustment =
        computeCoverageModificationPremiumAdjustmentSafely(coverageModificationForm);
    PremiumDetails currentPremiumDetails = coverageModificationForm.getCurrentPremiumDetails();
    BasicBlockCoveragePremiumDetail basicBlockCoveragePremiumDetail =
        createUpdatedBasicBlockCoveragePremiumDetail(premiumAdjustment, currentPremiumDetails);
    return currentPremiumDetails.update(basicBlockCoveragePremiumDetail);
  }

  private Money computeCoverageModificationPremiumAdjustmentSafely(
      CoverageModificationForm coverageModificationForm) {
    try {
      return computeCoverageModificationPremiumAdjustment(coverageModificationForm);
    } catch (InvalidArgumentException e) {
      // TODO: should never happen, log the exception
      // TODO: throw invalid civil liability limit error
      e.printStackTrace();
      return null;
    }
  }

  private Money computeCoverageModificationPremiumAdjustment(
      CoverageModificationForm coverageModificationForm) throws InvalidArgumentException {
    CoverageModificationPremiumInput currentCoverageModificationPremiumInput =
        premiumAssembler.toCurrentCoverageModificationPremiumInput(coverageModificationForm);
    CoverageModificationPremiumInput updatedCoverageModificationPremiumInput =
        premiumAssembler.toUpdatedCoverageModificationPremiumInput(coverageModificationForm);
    Money currentViewPremium =
        coverageModificationPremiumFormula.compute(currentCoverageModificationPremiumInput);
    Money updatedViewPremium =
        coverageModificationPremiumFormula.compute(updatedCoverageModificationPremiumInput);
    return updatedViewPremium.subtract(currentViewPremium);
  }

  private BasicBlockCoveragePremiumDetail createUpdatedBasicBlockCoveragePremiumDetail(
      Money premiumAdjustment, PremiumDetails currentPremiumDetails) {
    Money currentBasicBlockPremium = currentPremiumDetails.getCoveragePremium(BASIC_BLOCK);
    Money proposedPremium = currentBasicBlockPremium.add(premiumAdjustment);
    return new BasicBlockCoveragePremiumDetail(proposedPremium);
  }

  public PremiumDetails computeCoverageRenewalPremium(CoverageRenewalForm coverageRenewalForm) {
    PremiumDetails currentPremiumDetails = coverageRenewalForm.getCurrentPremiumDetails();
    PremiumDetails updatedPremiumDetails = computeBaseCoverageRenewalPremium(currentPremiumDetails);
    return computeAdditionalCoveragesRenewalPremium(currentPremiumDetails, updatedPremiumDetails);
  }

  private PremiumDetails computeBaseCoverageRenewalPremium(PremiumDetails currentPremiumDetails) {
    Money renewalPremium = computeRenewalPremium(BASIC_BLOCK, currentPremiumDetails);
    return new PremiumDetails(new BasicBlockCoveragePremiumDetail(renewalPremium));
  }

  private PremiumDetails computeAdditionalCoveragesRenewalPremium(
      PremiumDetails currentPremiumDetails, PremiumDetails updatedPremiumDetails) {
    for (PremiumCategory premiumCategory : PremiumCategory.values()) {
      if (isIncludedAdditionalCoverage(premiumCategory, currentPremiumDetails)) {
        updatedPremiumDetails =
            computeIncludedAdditionalCoverageRenewalPremium(
                currentPremiumDetails, updatedPremiumDetails, premiumCategory);
      }
    }
    return updatedPremiumDetails;
  }

  private boolean isIncludedAdditionalCoverage(
      PremiumCategory premiumCategory, PremiumDetails currentPremiumDetails) {
    return currentPremiumDetails.includes(premiumCategory) && premiumCategory != BASIC_BLOCK;
  }

  private PremiumDetails computeIncludedAdditionalCoverageRenewalPremium(
      PremiumDetails currentPremiumDetails,
      PremiumDetails updatedPremiumDetails,
      PremiumCategory premiumCategory) {
    Money renewalPremium = computeRenewalPremium(premiumCategory, currentPremiumDetails);
    updatedPremiumDetails =
        updatedPremiumDetails.addPremiumDetail(new PremiumDetail(premiumCategory, renewalPremium));
    return updatedPremiumDetails;
  }

  private Money computeRenewalPremium(
      PremiumCategory premiumCategory, PremiumDetails currentPremiumDetails) {
    Money currentPremium = currentPremiumDetails.getCoveragePremium(premiumCategory);
    return currentPremium.multiply(RENEWAL_PREMIUM_ADJUSTMENT_FACTOR);
  }
}
