package ca.ulaval.glo4003.coverage.domain.premium.formulapart.graduatestudent;

import ca.ulaval.glo4003.coverage.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.coverage.domain.premium.formula.quote.QuoteBasicBlockPremiumFormulaPart;
import ca.ulaval.glo4003.coverage.domain.premium.formula.quote.QuotePremiumInput;
import ca.ulaval.glo4003.shared.domain.money.Money;

public class GraduateStudentFormulaPart implements QuoteBasicBlockPremiumFormulaPart {
  private GraduateStudentAdjustmentProvider graduateStudentAdjustmentProvider;

  public GraduateStudentFormulaPart(
      GraduateStudentAdjustmentProvider graduateStudentAdjustmentProvider) {
    this.graduateStudentAdjustmentProvider = graduateStudentAdjustmentProvider;
  }

  @Override
  public Money compute(QuotePremiumInput quotePremiumInput, Money basePremium) {
    String cycle = quotePremiumInput.getNamedInsuredUniversityProgram().getCycle();
    PremiumAdjustment adjustment = graduateStudentAdjustmentProvider.getAdjustment(cycle);
    return adjustment.apply(basePremium);
  }
}
