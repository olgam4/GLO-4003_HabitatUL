package ca.ulaval.glo4003.calculator.application.premium;

import ca.ulaval.glo4003.calculator.domain.premium.detail.BaseCoveragePremiumDetail;
import ca.ulaval.glo4003.calculator.domain.premium.detail.BikeEndorsementPremiumDetail;
import ca.ulaval.glo4003.calculator.domain.premium.detail.PremiumDetails;
import ca.ulaval.glo4003.calculator.domain.premium.formula.bike.BaseCoverageMaximumBikePriceProvider;
import ca.ulaval.glo4003.calculator.domain.premium.formula.bike.BikeBasePremiumCalculator;
import ca.ulaval.glo4003.calculator.domain.premium.formula.bike.BikeEndorsementPremiumFormula;
import ca.ulaval.glo4003.calculator.domain.premium.formula.bike.BikePremiumInput;
import ca.ulaval.glo4003.calculator.domain.premium.formula.quote.QuoteBaseCoveragePremiumFormula;
import ca.ulaval.glo4003.calculator.domain.premium.formula.quote.QuoteBasePremiumCalculator;
import ca.ulaval.glo4003.calculator.domain.premium.formula.quote.QuotePremiumInput;
import ca.ulaval.glo4003.calculator.domain.premium.formulapart.animals.AnimalsAdjustmentLimitsProvider;
import ca.ulaval.glo4003.calculator.domain.premium.formulapart.animals.AnimalsAdjustmentProvider;
import ca.ulaval.glo4003.calculator.domain.premium.formulapart.animals.AnimalsFormulaPart;
import ca.ulaval.glo4003.calculator.domain.premium.formulapart.bikeprice.BikePriceAdjustmentProvider;
import ca.ulaval.glo4003.calculator.domain.premium.formulapart.bikeprice.BikePriceFormulaPart;
import ca.ulaval.glo4003.calculator.domain.premium.formulapart.civilliabilitylimit.CivilLiabilityLimitAdjustmentProvider;
import ca.ulaval.glo4003.calculator.domain.premium.formulapart.civilliabilitylimit.CivilLiabilityLimitFormulaPart;
import ca.ulaval.glo4003.calculator.domain.premium.formulapart.graduatestudent.GraduateStudentAdjustmentProvider;
import ca.ulaval.glo4003.calculator.domain.premium.formulapart.graduatestudent.GraduateStudentFormulaPart;
import ca.ulaval.glo4003.calculator.domain.premium.formulapart.preferentialprogram.PreferentialProgramAdjustmentProvider;
import ca.ulaval.glo4003.calculator.domain.premium.formulapart.preferentialprogram.PreferentialProgramFormulaPart;
import ca.ulaval.glo4003.calculator.domain.premium.formulapart.roommate.RoommateAdjustmentProvider;
import ca.ulaval.glo4003.calculator.domain.premium.formulapart.roommate.RoommateFormulaPart;
import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.shared.domain.money.Amount;
import ca.ulaval.glo4003.shared.domain.money.Money;

import java.util.Optional;

public class PremiumCalculator {
  private QuoteBaseCoveragePremiumFormula quoteBaseCoveragePremiumFormula;
  private BikeEndorsementPremiumFormula bikeEndorsementPremiumFormula;
  private BaseCoverageMaximumBikePriceProvider baseCoverageMaximumBikePriceProvider;

  public PremiumCalculator() {
    this(
        assembleQuoteBaseCoveragePremiumFormula(),
        assembleBikeEndorsementPremiumFormula(),
        ServiceLocator.resolve(BaseCoverageMaximumBikePriceProvider.class));
  }

  public PremiumCalculator(
      QuoteBaseCoveragePremiumFormula quoteBaseCoveragePremiumFormula,
      BikeEndorsementPremiumFormula bikeEndorsementPremiumFormula,
      BaseCoverageMaximumBikePriceProvider baseCoverageMaximumBikePriceProvider) {
    this.quoteBaseCoveragePremiumFormula = quoteBaseCoveragePremiumFormula;
    this.bikeEndorsementPremiumFormula = bikeEndorsementPremiumFormula;
    this.baseCoverageMaximumBikePriceProvider = baseCoverageMaximumBikePriceProvider;
  }

  private static QuoteBaseCoveragePremiumFormula assembleQuoteBaseCoveragePremiumFormula() {
    QuoteBaseCoveragePremiumFormula quoteBaseCoveragePremiumFormula =
        new QuoteBaseCoveragePremiumFormula(
            ServiceLocator.resolve(QuoteBasePremiumCalculator.class));
    quoteBaseCoveragePremiumFormula.addFormulaPart(
        new CivilLiabilityLimitFormulaPart(
            ServiceLocator.resolve(CivilLiabilityLimitAdjustmentProvider.class)));
    quoteBaseCoveragePremiumFormula.addFormulaPart(
        new AnimalsFormulaPart(
            ServiceLocator.resolve(AnimalsAdjustmentProvider.class),
            ServiceLocator.resolve(AnimalsAdjustmentLimitsProvider.class)));
    quoteBaseCoveragePremiumFormula.addFormulaPart(
        new PreferentialProgramFormulaPart(
            ServiceLocator.resolve(PreferentialProgramAdjustmentProvider.class)));
    quoteBaseCoveragePremiumFormula.addFormulaPart(
        new RoommateFormulaPart(ServiceLocator.resolve(RoommateAdjustmentProvider.class)));
    quoteBaseCoveragePremiumFormula.addFormulaPart(
        new GraduateStudentFormulaPart(
            ServiceLocator.resolve(GraduateStudentAdjustmentProvider.class)));
    return quoteBaseCoveragePremiumFormula;
  }

  private static BikeEndorsementPremiumFormula assembleBikeEndorsementPremiumFormula() {
    BikeEndorsementPremiumFormula bikeEndorsementPremiumFormula =
        new BikeEndorsementPremiumFormula(ServiceLocator.resolve(BikeBasePremiumCalculator.class));
    bikeEndorsementPremiumFormula.addFormulaPart(
        new BikePriceFormulaPart(ServiceLocator.resolve(BikePriceAdjustmentProvider.class)));
    return bikeEndorsementPremiumFormula;
  }

  public PremiumDetails computeQuotePremium(QuotePremiumInput quotePremiumInput) {
    Money baseCoveragePremium = quoteBaseCoveragePremiumFormula.compute(quotePremiumInput);
    PremiumDetails premiumDetails =
        new PremiumDetails(new BaseCoveragePremiumDetail(baseCoveragePremium));
    premiumDetails = addBikeEndorsementOnDemand(quotePremiumInput, premiumDetails);
    return premiumDetails;
  }

  private PremiumDetails addBikeEndorsementOnDemand(
      QuotePremiumInput quotePremiumInput, PremiumDetails premiumDetails) {
    return Optional.ofNullable(quotePremiumInput.getBikePrice())
        .map(x -> addBikeEndorsement(premiumDetails, x))
        .orElse(premiumDetails);
  }

  private PremiumDetails addBikeEndorsement(PremiumDetails premiumDetails, Amount bikePrice) {
    Amount baseCoverageMaximumBikePrice =
        baseCoverageMaximumBikePriceProvider.getMaximumBikePrice();
    if (bikePrice.isGreaterThan(baseCoverageMaximumBikePrice)) {
      Money bikeEndorsementPremium = computeBikeEndorsementPremium(new BikePremiumInput(bikePrice));
      premiumDetails =
          premiumDetails.addPremiumDetail(new BikeEndorsementPremiumDetail(bikeEndorsementPremium));
    }
    return premiumDetails;
  }

  public Money computeBikeEndorsementPremium(BikePremiumInput bikePremiumInput) {
    return bikeEndorsementPremiumFormula.compute(bikePremiumInput);
  }
}
