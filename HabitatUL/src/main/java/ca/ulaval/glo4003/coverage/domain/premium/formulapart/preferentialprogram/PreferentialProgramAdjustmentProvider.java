package ca.ulaval.glo4003.coverage.domain.premium.formulapart.preferentialprogram;

import ca.ulaval.glo4003.coverage.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.shared.domain.identity.Cycle;
import ca.ulaval.glo4003.shared.domain.identity.Degree;

public interface PreferentialProgramAdjustmentProvider {
  PremiumAdjustment getAdjustment(Cycle cycle, Degree degree, String major);
}
