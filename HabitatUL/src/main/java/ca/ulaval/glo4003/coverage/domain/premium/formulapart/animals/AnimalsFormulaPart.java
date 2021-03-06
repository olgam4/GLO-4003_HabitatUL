package ca.ulaval.glo4003.coverage.domain.premium.formulapart.animals;

import ca.ulaval.glo4003.coverage.domain.form.personalproperty.AnimalBreed;
import ca.ulaval.glo4003.coverage.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.coverage.domain.premium.formula.quote.QuoteBasicBlockPremiumFormulaPart;
import ca.ulaval.glo4003.coverage.domain.premium.formula.quote.QuotePremiumInput;
import ca.ulaval.glo4003.shared.domain.money.Money;

import java.util.Map;

public class AnimalsFormulaPart implements QuoteBasicBlockPremiumFormulaPart {
  private AnimalsAdjustmentProvider animalsAdjustmentProvider;
  private AnimalsAdjustmentLimitsProvider animalsAdjustmentLimitsProvider;

  public AnimalsFormulaPart(
      AnimalsAdjustmentProvider animalsAdjustmentProvider,
      AnimalsAdjustmentLimitsProvider animalsAdjustmentLimitsProvider) {
    this.animalsAdjustmentProvider = animalsAdjustmentProvider;
    this.animalsAdjustmentLimitsProvider = animalsAdjustmentLimitsProvider;
  }

  @Override
  public Money compute(QuotePremiumInput quotePremiumInput, Money basePremium) {
    Map<AnimalBreed, Integer> animals = quotePremiumInput.getAnimals().getCollection();
    Money totalAdjustment = computeAdjustment(animals, basePremium);
    return capAdjustment(totalAdjustment, basePremium);
  }

  private Money computeAdjustment(Map<AnimalBreed, Integer> ownedAnimals, Money basePremium) {
    Money totalAdjustment = Money.ZERO;
    for (Map.Entry<AnimalBreed, Integer> entry : ownedAnimals.entrySet()) {
      PremiumAdjustment adjustment =
          animalsAdjustmentProvider.getAdjustment(entry.getKey(), entry.getValue());
      totalAdjustment = totalAdjustment.add(adjustment.apply(basePremium));
    }
    return totalAdjustment;
  }

  private Money capAdjustment(Money totalAdjustment, Money basePremium) {
    PremiumAdjustment minimumAdjustment =
        animalsAdjustmentLimitsProvider.getMinimumAdjustment(basePremium);
    PremiumAdjustment maximumAdjustment =
        animalsAdjustmentLimitsProvider.getMaximumAdjustment(basePremium);
    return maximumAdjustment.apply(minimumAdjustment.apply(totalAdjustment));
  }
}
