package ca.ulaval.glo4003.coverage.application.premium.assembler;

import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.coverage.domain.premium.formula.coveragemodification.CoverageModificationBasePremiumCalculator;
import ca.ulaval.glo4003.coverage.domain.premium.formula.coveragemodification.CoverageModificationPremiumFormula;
import ca.ulaval.glo4003.coverage.domain.premium.formulapart.civilliabilitylimit.CivilLiabilityLimitAdjustmentProvider;
import ca.ulaval.glo4003.coverage.domain.premium.formulapart.civilliabilitylimit.CivilLiabilityLimitCoverageModificationPremiumFormulaPart;

public class CoverageModificationPremiumFormulaAssembler {
  private CoverageModificationPremiumFormulaAssembler() {}

  public static CoverageModificationPremiumFormula assemble() {
    CoverageModificationPremiumFormula premiumFormula =
        new CoverageModificationPremiumFormula(
            ServiceLocator.resolve(CoverageModificationBasePremiumCalculator.class));
    premiumFormula.addPremiumFormulaPart(
        new CivilLiabilityLimitCoverageModificationPremiumFormulaPart(
            ServiceLocator.resolve(CivilLiabilityLimitAdjustmentProvider.class)));
    return premiumFormula;
  }
}
