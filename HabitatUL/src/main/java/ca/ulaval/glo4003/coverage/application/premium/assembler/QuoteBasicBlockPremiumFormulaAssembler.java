package ca.ulaval.glo4003.coverage.application.premium.assembler;

import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.coverage.domain.premium.formula.quote.QuoteBasicBlockBasePremiumCalculator;
import ca.ulaval.glo4003.coverage.domain.premium.formula.quote.QuoteBasicBlockPremiumFormula;
import ca.ulaval.glo4003.coverage.domain.premium.formulapart.animals.AnimalsAdjustmentLimitsProvider;
import ca.ulaval.glo4003.coverage.domain.premium.formulapart.animals.AnimalsAdjustmentProvider;
import ca.ulaval.glo4003.coverage.domain.premium.formulapart.animals.AnimalsFormulaPart;
import ca.ulaval.glo4003.coverage.domain.premium.formulapart.civilliabilitylimit.CivilLiabilityLimitAdjustmentProvider;
import ca.ulaval.glo4003.coverage.domain.premium.formulapart.civilliabilitylimit.CivilLiabilityLimitFormulaPart;
import ca.ulaval.glo4003.coverage.domain.premium.formulapart.graduatestudent.GraduateStudentAdjustmentProvider;
import ca.ulaval.glo4003.coverage.domain.premium.formulapart.graduatestudent.GraduateStudentFormulaPart;
import ca.ulaval.glo4003.coverage.domain.premium.formulapart.preferentialprogram.PreferentialProgramAdjustmentProvider;
import ca.ulaval.glo4003.coverage.domain.premium.formulapart.preferentialprogram.PreferentialProgramFormulaPart;
import ca.ulaval.glo4003.coverage.domain.premium.formulapart.roommate.RoommateAdjustmentProvider;
import ca.ulaval.glo4003.coverage.domain.premium.formulapart.roommate.RoommateFormulaPart;

public class QuoteBasicBlockPremiumFormulaAssembler {
  private QuoteBasicBlockPremiumFormulaAssembler() {}

  public static QuoteBasicBlockPremiumFormula assemble() {
    QuoteBasicBlockPremiumFormula quoteBasicBlockPremiumFormula =
        new QuoteBasicBlockPremiumFormula(
            ServiceLocator.resolve(QuoteBasicBlockBasePremiumCalculator.class));
    quoteBasicBlockPremiumFormula.addPremiumFormulaPart(
        new CivilLiabilityLimitFormulaPart(
            ServiceLocator.resolve(CivilLiabilityLimitAdjustmentProvider.class)));
    quoteBasicBlockPremiumFormula.addPremiumFormulaPart(
        new AnimalsFormulaPart(
            ServiceLocator.resolve(AnimalsAdjustmentProvider.class),
            ServiceLocator.resolve(AnimalsAdjustmentLimitsProvider.class)));
    quoteBasicBlockPremiumFormula.addPremiumFormulaPart(
        new PreferentialProgramFormulaPart(
            ServiceLocator.resolve(PreferentialProgramAdjustmentProvider.class)));
    quoteBasicBlockPremiumFormula.addPremiumFormulaPart(
        new RoommateFormulaPart(ServiceLocator.resolve(RoommateAdjustmentProvider.class)));
    quoteBasicBlockPremiumFormula.addPremiumFormulaPart(
        new GraduateStudentFormulaPart(
            ServiceLocator.resolve(GraduateStudentAdjustmentProvider.class)));
    return quoteBasicBlockPremiumFormula;
  }
}
