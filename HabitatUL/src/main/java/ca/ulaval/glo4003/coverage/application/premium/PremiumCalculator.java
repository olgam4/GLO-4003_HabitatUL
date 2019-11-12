package ca.ulaval.glo4003.coverage.application.premium;

import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.coverage.application.AdditionalCoverageResolver;
import ca.ulaval.glo4003.coverage.domain.form.QuoteForm;
import ca.ulaval.glo4003.coverage.domain.premium.detail.BasicBlockCoveragePremiumDetail;
import ca.ulaval.glo4003.coverage.domain.premium.detail.BicycleEndorsementPremiumDetail;
import ca.ulaval.glo4003.coverage.domain.premium.detail.PremiumDetails;
import ca.ulaval.glo4003.coverage.domain.premium.formula.bicycleendorsement.BicycleEndorsementBasePremiumCalculator;
import ca.ulaval.glo4003.coverage.domain.premium.formula.bicycleendorsement.BicycleEndorsementPremiumFormula;
import ca.ulaval.glo4003.coverage.domain.premium.formula.bicycleendorsement.BicycleEndorsementPremiumInput;
import ca.ulaval.glo4003.coverage.domain.premium.formula.quote.QuoteBasePremiumCalculator;
import ca.ulaval.glo4003.coverage.domain.premium.formula.quote.QuotePremiumFormula;
import ca.ulaval.glo4003.coverage.domain.premium.formula.quote.QuotePremiumInput;
import ca.ulaval.glo4003.coverage.domain.premium.formulapart.animals.AnimalsAdjustmentLimitsProvider;
import ca.ulaval.glo4003.coverage.domain.premium.formulapart.animals.AnimalsAdjustmentProvider;
import ca.ulaval.glo4003.coverage.domain.premium.formulapart.animals.AnimalsFormulaPart;
import ca.ulaval.glo4003.coverage.domain.premium.formulapart.bicycleprice.BicycleEndorsementPriceFormulaPart;
import ca.ulaval.glo4003.coverage.domain.premium.formulapart.bicycleprice.BicyclePriceAdjustmentProvider;
import ca.ulaval.glo4003.coverage.domain.premium.formulapart.civilliabilitylimit.CivilLiabilityLimitAdjustmentProvider;
import ca.ulaval.glo4003.coverage.domain.premium.formulapart.civilliabilitylimit.CivilLiabilityLimitFormulaPart;
import ca.ulaval.glo4003.coverage.domain.premium.formulapart.graduatestudent.GraduateStudentAdjustmentProvider;
import ca.ulaval.glo4003.coverage.domain.premium.formulapart.graduatestudent.GraduateStudentFormulaPart;
import ca.ulaval.glo4003.coverage.domain.premium.formulapart.preferentialprogram.PreferentialProgramAdjustmentProvider;
import ca.ulaval.glo4003.coverage.domain.premium.formulapart.preferentialprogram.PreferentialProgramFormulaPart;
import ca.ulaval.glo4003.coverage.domain.premium.formulapart.roommate.RoommateAdjustmentProvider;
import ca.ulaval.glo4003.coverage.domain.premium.formulapart.roommate.RoommateFormulaPart;
import ca.ulaval.glo4003.shared.domain.money.Money;

public class PremiumCalculator {
  private PremiumAssembler premiumAssembler;
  private AdditionalCoverageResolver additionalCoverageResolver;
  private QuotePremiumFormula quotePremiumFormula;
  private BicycleEndorsementPremiumFormula bicycleEndorsementPremiumFormula;

  public PremiumCalculator() {
    this(
        new PremiumAssembler(),
        new AdditionalCoverageResolver(),
        assembleQuoteBasicBlockPremiumFormula(),
        assembleBicycleEndorsementPremiumFormula());
  }

  public PremiumCalculator(
      PremiumAssembler premiumAssembler,
      AdditionalCoverageResolver additionalCoverageResolver,
      QuotePremiumFormula quotePremiumFormula,
      BicycleEndorsementPremiumFormula bicycleEndorsementPremiumFormula) {
    this.premiumAssembler = premiumAssembler;
    this.additionalCoverageResolver = additionalCoverageResolver;
    this.quotePremiumFormula = quotePremiumFormula;
    this.bicycleEndorsementPremiumFormula = bicycleEndorsementPremiumFormula;
  }

  private static QuotePremiumFormula assembleQuoteBasicBlockPremiumFormula() {
    QuotePremiumFormula quotePremiumFormula =
        new QuotePremiumFormula(ServiceLocator.resolve(QuoteBasePremiumCalculator.class));
    quotePremiumFormula.addFormulaPart(
        new CivilLiabilityLimitFormulaPart(
            ServiceLocator.resolve(CivilLiabilityLimitAdjustmentProvider.class)));
    quotePremiumFormula.addFormulaPart(
        new AnimalsFormulaPart(
            ServiceLocator.resolve(AnimalsAdjustmentProvider.class),
            ServiceLocator.resolve(AnimalsAdjustmentLimitsProvider.class)));
    quotePremiumFormula.addFormulaPart(
        new PreferentialProgramFormulaPart(
            ServiceLocator.resolve(PreferentialProgramAdjustmentProvider.class)));
    quotePremiumFormula.addFormulaPart(
        new RoommateFormulaPart(ServiceLocator.resolve(RoommateAdjustmentProvider.class)));
    quotePremiumFormula.addFormulaPart(
        new GraduateStudentFormulaPart(
            ServiceLocator.resolve(GraduateStudentAdjustmentProvider.class)));
    return quotePremiumFormula;
  }

  private static BicycleEndorsementPremiumFormula assembleBicycleEndorsementPremiumFormula() {
    BicycleEndorsementPremiumFormula bicycleEndorsementPremiumFormula =
        new BicycleEndorsementPremiumFormula(
            ServiceLocator.resolve(BicycleEndorsementBasePremiumCalculator.class));
    bicycleEndorsementPremiumFormula.addFormulaPart(
        new BicycleEndorsementPriceFormulaPart(
            ServiceLocator.resolve(BicyclePriceAdjustmentProvider.class)));
    return bicycleEndorsementPremiumFormula;
  }

  public PremiumDetails computeQuotePremium(QuoteForm quoteForm) {
    QuotePremiumInput quotePremiumInput = premiumAssembler.toQuotePremiumInput(quoteForm);
    Money basicBlockPremium = quotePremiumFormula.compute(quotePremiumInput);
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

  public Money computeBicycleEndorsementPremium(
      BicycleEndorsementPremiumInput bicycleEndorsementPremiumInput) {
    return bicycleEndorsementPremiumFormula.compute(bicycleEndorsementPremiumInput);
  }
}
