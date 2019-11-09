package ca.ulaval.glo4003.calculator.domain.premium.formulapart.graduatestudent;

import ca.ulaval.glo4003.calculator.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.calculator.domain.premium.formula.quote.QuotePremiumFormulaPart;
import ca.ulaval.glo4003.calculator.domain.premium.formula.quote.QuotePremiumInput;
import ca.ulaval.glo4003.shared.domain.money.Money;

public class GraduateStudentFormulaPart implements QuotePremiumFormulaPart {
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
