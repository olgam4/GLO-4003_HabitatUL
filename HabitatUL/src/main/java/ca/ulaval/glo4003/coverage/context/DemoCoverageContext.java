package ca.ulaval.glo4003.coverage.context;

import ca.ulaval.glo4003.coverage.domain.form.validation.part.UlRegistrarOffice;
import ca.ulaval.glo4003.coverage.domain.premium.formula.bicycleendorsement.BasicBlockCoverageMaximumBicyclePriceProvider;
import ca.ulaval.glo4003.coverage.domain.premium.formula.bicycleendorsement.BicycleEndorsementBasePremiumCalculator;
import ca.ulaval.glo4003.coverage.domain.premium.formula.coveragemodification.CoverageModificationBasePremiumCalculator;
import ca.ulaval.glo4003.coverage.domain.premium.formula.quote.QuoteBasicBlockBasePremiumCalculator;
import ca.ulaval.glo4003.coverage.domain.premium.formulapart.animals.AnimalsAdjustmentLimitsProvider;
import ca.ulaval.glo4003.coverage.domain.premium.formulapart.animals.AnimalsAdjustmentProvider;
import ca.ulaval.glo4003.coverage.domain.premium.formulapart.bicycleprice.BicyclePriceAdjustmentProvider;
import ca.ulaval.glo4003.coverage.domain.premium.formulapart.civilliabilitylimit.CivilLiabilityLimitAdjustmentProvider;
import ca.ulaval.glo4003.coverage.domain.premium.formulapart.graduatestudent.GraduateStudentAdjustmentProvider;
import ca.ulaval.glo4003.coverage.domain.premium.formulapart.preferentialprogram.PreferentialProgramAdjustmentProvider;
import ca.ulaval.glo4003.coverage.domain.premium.formulapart.roommate.RoommateAdjustmentProvider;
import ca.ulaval.glo4003.coverage.infrastructure.form.validation.DummyUlRegistrarOffice;
import ca.ulaval.glo4003.coverage.infrastructure.premium.formula.bicycleendorsement.HardCodedBasicBlockCoverageMaximumBicyclePriceProvider;
import ca.ulaval.glo4003.coverage.infrastructure.premium.formula.bicycleendorsement.HardCodedBicycleEndorsementBasePremiumCalculator;
import ca.ulaval.glo4003.coverage.infrastructure.premium.formula.coveragemodification.HardCodedCoverageModificationBasePremiumCalculator;
import ca.ulaval.glo4003.coverage.infrastructure.premium.formula.quote.HardCodedQuoteBasicBlockBasePremiumCalculator;
import ca.ulaval.glo4003.coverage.infrastructure.premium.formulapart.animals.HardCodedAnimalsAdjustmentLimitsProvider;
import ca.ulaval.glo4003.coverage.infrastructure.premium.formulapart.animals.HardCodedAnimalsAdjustmentProvider;
import ca.ulaval.glo4003.coverage.infrastructure.premium.formulapart.bicycleprice.HardCodedBicyclePriceAdjustmentProvider;
import ca.ulaval.glo4003.coverage.infrastructure.premium.formulapart.civilliabilitylimit.HardCodedCivilLiabilityLimitAdjustmentProvider;
import ca.ulaval.glo4003.coverage.infrastructure.premium.formulapart.graduatestudent.HardCodedGraduateStudentAdjustmentProvider;
import ca.ulaval.glo4003.coverage.infrastructure.premium.formulapart.preferentialprogram.JsonPreferentialProgramAdjustmentProvider;
import ca.ulaval.glo4003.coverage.infrastructure.premium.formulapart.roommate.HardCodedRoommateAdjustmentProvider;
import ca.ulaval.glo4003.shared.domain.handling.InvalidArgumentException;

import static ca.ulaval.glo4003.context.ServiceLocator.register;

public class DemoCoverageContext {
  public void execute() {
    register(UlRegistrarOffice.class, new DummyUlRegistrarOffice());
    register(
        QuoteBasicBlockBasePremiumCalculator.class,
        new HardCodedQuoteBasicBlockBasePremiumCalculator());
    register(
        CivilLiabilityLimitAdjustmentProvider.class,
        new HardCodedCivilLiabilityLimitAdjustmentProvider());
    register(AnimalsAdjustmentProvider.class, new HardCodedAnimalsAdjustmentProvider());
    register(AnimalsAdjustmentLimitsProvider.class, new HardCodedAnimalsAdjustmentLimitsProvider());
    try {
      register(
          PreferentialProgramAdjustmentProvider.class,
          new JsonPreferentialProgramAdjustmentProvider());
    } catch (InvalidArgumentException e) {
      e.printStackTrace();
    }
    register(RoommateAdjustmentProvider.class, new HardCodedRoommateAdjustmentProvider());
    register(
        GraduateStudentAdjustmentProvider.class, new HardCodedGraduateStudentAdjustmentProvider());
    register(
        BicycleEndorsementBasePremiumCalculator.class,
        new HardCodedBicycleEndorsementBasePremiumCalculator());
    register(BicyclePriceAdjustmentProvider.class, new HardCodedBicyclePriceAdjustmentProvider());
    register(
        BasicBlockCoverageMaximumBicyclePriceProvider.class,
        new HardCodedBasicBlockCoverageMaximumBicyclePriceProvider());
    register(
        CoverageModificationBasePremiumCalculator.class,
        new HardCodedCoverageModificationBasePremiumCalculator());
  }
}
