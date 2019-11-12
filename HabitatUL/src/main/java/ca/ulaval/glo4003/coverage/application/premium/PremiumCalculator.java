package ca.ulaval.glo4003.coverage.application.premium;

import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.coverage.application.PremiumAssembler;
import ca.ulaval.glo4003.coverage.application.coverage.AdditionalCoverageResolver;
import ca.ulaval.glo4003.coverage.domain.form.QuoteForm;
import ca.ulaval.glo4003.coverage.domain.premium.detail.BasicBlockCoveragePremiumDetail;
import ca.ulaval.glo4003.coverage.domain.premium.detail.BikeEndorsementPremiumDetail;
import ca.ulaval.glo4003.coverage.domain.premium.detail.PremiumDetails;
import ca.ulaval.glo4003.coverage.domain.premium.formula.bike.BikeBasePremiumCalculator;
import ca.ulaval.glo4003.coverage.domain.premium.formula.bike.BikeEndorsementPremiumFormula;
import ca.ulaval.glo4003.coverage.domain.premium.formula.bike.BikePremiumInput;
import ca.ulaval.glo4003.coverage.domain.premium.formula.quote.QuoteBasePremiumCalculator;
import ca.ulaval.glo4003.coverage.domain.premium.formula.quote.QuoteBasicBlockPremiumFormula;
import ca.ulaval.glo4003.coverage.domain.premium.formula.quote.QuotePremiumInput;
import ca.ulaval.glo4003.coverage.domain.premium.formulapart.animals.AnimalsAdjustmentLimitsProvider;
import ca.ulaval.glo4003.coverage.domain.premium.formulapart.animals.AnimalsAdjustmentProvider;
import ca.ulaval.glo4003.coverage.domain.premium.formulapart.animals.AnimalsFormulaPart;
import ca.ulaval.glo4003.coverage.domain.premium.formulapart.bikeprice.BikePriceAdjustmentProvider;
import ca.ulaval.glo4003.coverage.domain.premium.formulapart.bikeprice.BikePriceFormulaPart;
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
  private QuoteBasicBlockPremiumFormula quoteBasicBlockPremiumFormula;
  private BikeEndorsementPremiumFormula bikeEndorsementPremiumFormula;

  public PremiumCalculator() {
    this(
        new PremiumAssembler(),
        new AdditionalCoverageResolver(),
        assembleQuoteBasicBlockPremiumFormula(),
        assembleBikeEndorsementPremiumFormula());
  }

  public PremiumCalculator(
      PremiumAssembler premiumAssembler,
      AdditionalCoverageResolver additionalCoverageResolver,
      QuoteBasicBlockPremiumFormula quoteBasicBlockPremiumFormula,
      BikeEndorsementPremiumFormula bikeEndorsementPremiumFormula) {
    this.premiumAssembler = premiumAssembler;
    this.additionalCoverageResolver = additionalCoverageResolver;
    this.quoteBasicBlockPremiumFormula = quoteBasicBlockPremiumFormula;
    this.bikeEndorsementPremiumFormula = bikeEndorsementPremiumFormula;
  }

  private static QuoteBasicBlockPremiumFormula assembleQuoteBasicBlockPremiumFormula() {
    QuoteBasicBlockPremiumFormula quoteBasicBlockPremiumFormula =
        new QuoteBasicBlockPremiumFormula(ServiceLocator.resolve(QuoteBasePremiumCalculator.class));
    quoteBasicBlockPremiumFormula.addFormulaPart(
        new CivilLiabilityLimitFormulaPart(
            ServiceLocator.resolve(CivilLiabilityLimitAdjustmentProvider.class)));
    quoteBasicBlockPremiumFormula.addFormulaPart(
        new AnimalsFormulaPart(
            ServiceLocator.resolve(AnimalsAdjustmentProvider.class),
            ServiceLocator.resolve(AnimalsAdjustmentLimitsProvider.class)));
    quoteBasicBlockPremiumFormula.addFormulaPart(
        new PreferentialProgramFormulaPart(
            ServiceLocator.resolve(PreferentialProgramAdjustmentProvider.class)));
    quoteBasicBlockPremiumFormula.addFormulaPart(
        new RoommateFormulaPart(ServiceLocator.resolve(RoommateAdjustmentProvider.class)));
    quoteBasicBlockPremiumFormula.addFormulaPart(
        new GraduateStudentFormulaPart(
            ServiceLocator.resolve(GraduateStudentAdjustmentProvider.class)));
    return quoteBasicBlockPremiumFormula;
  }

  private static BikeEndorsementPremiumFormula assembleBikeEndorsementPremiumFormula() {
    BikeEndorsementPremiumFormula bikeEndorsementPremiumFormula =
        new BikeEndorsementPremiumFormula(ServiceLocator.resolve(BikeBasePremiumCalculator.class));
    bikeEndorsementPremiumFormula.addFormulaPart(
        new BikePriceFormulaPart(ServiceLocator.resolve(BikePriceAdjustmentProvider.class)));
    return bikeEndorsementPremiumFormula;
  }

  public PremiumDetails computeQuotePremium(QuoteForm quoteForm) {
    QuotePremiumInput quotePremiumInput = premiumAssembler.toQuotePremiumInput(quoteForm);
    Money basicBlockPremium = quoteBasicBlockPremiumFormula.compute(quotePremiumInput);
    PremiumDetails premiumDetails =
        new PremiumDetails(new BasicBlockCoveragePremiumDetail(basicBlockPremium));
    premiumDetails = addBikeEndorsementOnDemand(quoteForm, premiumDetails);
    return premiumDetails;
  }

  private PremiumDetails addBikeEndorsementOnDemand(
      QuoteForm quoteForm, PremiumDetails premiumDetails) {
    if (additionalCoverageResolver.shouldIncludeBikeEndorsement(quoteForm)) {
      BikePremiumInput bikePremiumInput = premiumAssembler.toBikePremiumInput(quoteForm);
      premiumDetails = addBikeEndorsement(bikePremiumInput, premiumDetails);
    }
    return premiumDetails;
  }

  private PremiumDetails addBikeEndorsement(
      BikePremiumInput bikePremiumInput, PremiumDetails premiumDetails) {
    Money bikeEndorsementPremium = computeBikeEndorsementPremium(bikePremiumInput);
    premiumDetails =
        premiumDetails.addPremiumDetail(new BikeEndorsementPremiumDetail(bikeEndorsementPremium));
    return premiumDetails;
  }

  public Money computeBikeEndorsementPremium(BikePremiumInput bikePremiumInput) {
    return bikeEndorsementPremiumFormula.compute(bikePremiumInput);
  }
}
