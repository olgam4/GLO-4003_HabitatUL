package ca.ulaval.glo4003.calculator.context;

import ca.ulaval.glo4003.calculator.domain.premium.formula.bike.BikeBasePremiumCalculator;
import ca.ulaval.glo4003.calculator.domain.premium.formula.quote.QuoteBasePremiumCalculator;
import ca.ulaval.glo4003.calculator.domain.premium.formulapart.animals.AnimalsAdjustmentLimitsProvider;
import ca.ulaval.glo4003.calculator.domain.premium.formulapart.animals.AnimalsAdjustmentProvider;
import ca.ulaval.glo4003.calculator.domain.premium.formulapart.bikeprice.BikePriceAdjustmentProvider;
import ca.ulaval.glo4003.calculator.domain.premium.formulapart.civilliabilitylimit.CivilLiabilityLimitAdjustmentProvider;
import ca.ulaval.glo4003.calculator.domain.premium.formulapart.graduatestudent.GraduateStudentAdjustmentProvider;
import ca.ulaval.glo4003.calculator.domain.premium.formulapart.preferentialprogram.PreferentialProgramAdjustmentProvider;
import ca.ulaval.glo4003.calculator.domain.premium.formulapart.roommate.RoommateAdjustmentProvider;
import ca.ulaval.glo4003.calculator.infrastructure.premium.formula.bike.HardCodedBikeBasePremiumCalculator;
import ca.ulaval.glo4003.calculator.infrastructure.premium.formula.quote.HardCodedQuoteBasePremiumCalculator;
import ca.ulaval.glo4003.calculator.infrastructure.premium.formulapart.animals.HardCodedAnimalsAdjustmentLimitsProvider;
import ca.ulaval.glo4003.calculator.infrastructure.premium.formulapart.animals.HardCodedAnimalsAdjustmentProvider;
import ca.ulaval.glo4003.calculator.infrastructure.premium.formulapart.bikeprice.HardCodedBikePriceAdjustmentProvider;
import ca.ulaval.glo4003.calculator.infrastructure.premium.formulapart.civilliabilitylimit.HardCodedCivilLiabilityLimitAdjustmentProvider;
import ca.ulaval.glo4003.calculator.infrastructure.premium.formulapart.graduatestudent.HardCodedGraduateStudentAdjustmentProvider;
import ca.ulaval.glo4003.calculator.infrastructure.premium.formulapart.preferentialprogram.JsonPreferentialProgramAdjustmentProvider;
import ca.ulaval.glo4003.calculator.infrastructure.premium.formulapart.roommate.HardCodedRoommateAdjustmentProvider;

import static ca.ulaval.glo4003.context.ServiceLocator.register;

public class DemoCalculatorContext {
  public void execute() {
    register(QuoteBasePremiumCalculator.class, new HardCodedQuoteBasePremiumCalculator());
    register(
        CivilLiabilityLimitAdjustmentProvider.class,
        new HardCodedCivilLiabilityLimitAdjustmentProvider());
    register(AnimalsAdjustmentProvider.class, new HardCodedAnimalsAdjustmentProvider());
    register(AnimalsAdjustmentLimitsProvider.class, new HardCodedAnimalsAdjustmentLimitsProvider());
    register(
        PreferentialProgramAdjustmentProvider.class,
        new JsonPreferentialProgramAdjustmentProvider());
    register(RoommateAdjustmentProvider.class, new HardCodedRoommateAdjustmentProvider());
    register(
        GraduateStudentAdjustmentProvider.class, new HardCodedGraduateStudentAdjustmentProvider());
    register(BikeBasePremiumCalculator.class, new HardCodedBikeBasePremiumCalculator());
    register(BikePriceAdjustmentProvider.class, new HardCodedBikePriceAdjustmentProvider());
  }
}
