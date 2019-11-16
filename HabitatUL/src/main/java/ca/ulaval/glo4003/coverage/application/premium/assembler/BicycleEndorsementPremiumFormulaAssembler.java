package ca.ulaval.glo4003.coverage.application.premium.assembler;

import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.coverage.domain.premium.formula.bicycleendorsement.BicycleEndorsementBasePremiumCalculator;
import ca.ulaval.glo4003.coverage.domain.premium.formula.bicycleendorsement.BicycleEndorsementPremiumFormula;
import ca.ulaval.glo4003.coverage.domain.premium.formulapart.bicycleprice.BicycleEndorsementPriceFormulaPart;
import ca.ulaval.glo4003.coverage.domain.premium.formulapart.bicycleprice.BicyclePriceAdjustmentProvider;

public class BicycleEndorsementPremiumFormulaAssembler {
  public static BicycleEndorsementPremiumFormula assemble() {
    BicycleEndorsementPremiumFormula bicycleEndorsementPremiumFormula =
        new BicycleEndorsementPremiumFormula(
            ServiceLocator.resolve(BicycleEndorsementBasePremiumCalculator.class));
    bicycleEndorsementPremiumFormula.addFormulaPart(
        new BicycleEndorsementPriceFormulaPart(
            ServiceLocator.resolve(BicyclePriceAdjustmentProvider.class)));
    return bicycleEndorsementPremiumFormula;
  }
}
