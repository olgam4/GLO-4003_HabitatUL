package ca.ulaval.glo4003.calculator.domain.premium.formula;

import ca.ulaval.glo4003.shared.domain.money.Money;

import java.util.ArrayList;
import java.util.List;

public class PremiumFormula<T> {
  private BasePremiumCalculator<T> basePremiumCalculator;
  private List<PremiumFormulaPart<T>> formulaParts = new ArrayList<>();

  public PremiumFormula(BasePremiumCalculator<T> basePremiumCalculator) {
    this.basePremiumCalculator = basePremiumCalculator;
  }

  public void addFormulaPart(PremiumFormulaPart<T> formulaPart) {
    formulaParts.add(formulaPart);
  }

  public Money compute(T premiumInput) {
    Money basePremium = basePremiumCalculator.compute(premiumInput);
    Money premium = basePremium;

    for (PremiumFormulaPart formulaPart : formulaParts) {
      Money premiumAdjustment = formulaPart.compute(premiumInput, basePremium);
      premium = premium.add(premiumAdjustment);
    }

    return premium;
  }
}
