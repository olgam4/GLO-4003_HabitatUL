package ca.ulaval.glo4003.calculator.application.premium;

import ca.ulaval.glo4003.calculator.domain.premium.formula.bike.BikeBasePremiumCalculator;
import ca.ulaval.glo4003.calculator.domain.premium.formula.bike.BikePremiumFormula;
import ca.ulaval.glo4003.calculator.domain.premium.formula.bike.BikePremiumInput;
import ca.ulaval.glo4003.calculator.domain.premium.formula.quote.QuoteBasePremiumCalculator;
import ca.ulaval.glo4003.calculator.domain.premium.formula.quote.QuotePremiumFormula;
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
import ca.ulaval.glo4003.shared.domain.money.Money;

public class PremiumCalculator {
  private QuotePremiumFormula quotePremiumFormula;
  private BikePremiumFormula bikePremiumFormula;

  public PremiumCalculator() {
    this(assembleQuotePremiumFormula(), assembleBikePremiumFormula());
  }

  public PremiumCalculator(
      QuotePremiumFormula quotePremiumFormula, BikePremiumFormula bikePremiumFormula) {
    this.quotePremiumFormula = quotePremiumFormula;
    this.bikePremiumFormula = bikePremiumFormula;
  }

  private static QuotePremiumFormula assembleQuotePremiumFormula() {
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

  private static BikePremiumFormula assembleBikePremiumFormula() {
    BikePremiumFormula bikePremiumFormula =
        new BikePremiumFormula(ServiceLocator.resolve(BikeBasePremiumCalculator.class));
    bikePremiumFormula.addFormulaPart(
        new BikePriceFormulaPart(ServiceLocator.resolve(BikePriceAdjustmentProvider.class)));
    return bikePremiumFormula;
  }

  public Money computeQuotePremium(QuotePremiumInput quotePremiumInput) {
    return quotePremiumFormula.compute(quotePremiumInput);
  }

  public Money computeBikePremium(BikePremiumInput bikePremiumInput) {
    return bikePremiumFormula.compute(bikePremiumInput);
  }
}
