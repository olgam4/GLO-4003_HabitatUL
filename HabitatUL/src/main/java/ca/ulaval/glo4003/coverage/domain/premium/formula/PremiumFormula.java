package ca.ulaval.glo4003.coverage.domain.premium.formula;

import ca.ulaval.glo4003.shared.domain.money.Money;

import java.util.ArrayList;
import java.util.List;

public abstract class PremiumFormula<T> {
  private BasePremiumCalculator<T> basePremiumCalculator;
  private List<PremiumFormulaPart<T>> premiumFormulaParts = new ArrayList<>();

  public PremiumFormula(BasePremiumCalculator<T> basePremiumCalculator) {
    this.basePremiumCalculator = basePremiumCalculator;
  }

  public void addPremiumFormulaPart(PremiumFormulaPart<T> premiumFormulaPart) {
    premiumFormulaParts.add(premiumFormulaPart);
  }

  public Money compute(T premiumInput) {
    Money basePremium = basePremiumCalculator.compute(premiumInput);
    Money premium = basePremium;

    for (PremiumFormulaPart<T> formulaPart : premiumFormulaParts) {
      Money premiumAdjustment = formulaPart.compute(premiumInput, basePremium);
      premium = premium.add(premiumAdjustment);
    }

    return premium;
  }
}
