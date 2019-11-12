package ca.ulaval.glo4003.coverage.domain.premium.formula;

import ca.ulaval.glo4003.shared.domain.money.Money;

import java.util.ArrayList;
import java.util.List;

public class PremiumFormula<T> {
  private BasePremiumCalculator<T> basePremiumCalculator;
  private List<PremiumFormulaPart<T>> premiumFormulaParts = new ArrayList<>();

  public PremiumFormula(BasePremiumCalculator<T> basePremiumCalculator) {
    this.basePremiumCalculator = basePremiumCalculator;
  }

  public void addFormulaPart(PremiumFormulaPart<T> formulaPart) {
    premiumFormulaParts.add(formulaPart);
  }

  public Money compute(T premiumInput) {
    Money basePremium = basePremiumCalculator.compute(premiumInput);
    Money premium = basePremium;

    for (PremiumFormulaPart formulaPart : premiumFormulaParts) {
      Money premiumAdjustment = formulaPart.compute(premiumInput, basePremium);
      premium = premium.add(premiumAdjustment);
    }

    return premium;
  }
}
