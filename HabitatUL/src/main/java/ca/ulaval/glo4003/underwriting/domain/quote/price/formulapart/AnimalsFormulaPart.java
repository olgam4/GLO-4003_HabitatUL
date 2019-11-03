package ca.ulaval.glo4003.underwriting.domain.quote.price.formulapart;

import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;
import ca.ulaval.glo4003.underwriting.domain.quote.form.personalproperty.AnimalBreed;
import ca.ulaval.glo4003.underwriting.domain.quote.price.AnimalsAdjustmentLimitsProvider;
import ca.ulaval.glo4003.underwriting.domain.quote.price.AnimalsAdjustmentProvider;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.QuotePriceAdjustment;

import java.util.Map;

public class AnimalsFormulaPart implements QuotePriceFormulaPart {
  private AnimalsAdjustmentProvider animalsAdjustmentProvider;
  private AnimalsAdjustmentLimitsProvider animalsAdjustmentLimitsProvider;

  public AnimalsFormulaPart(
      AnimalsAdjustmentProvider animalsAdjustmentProvider,
      AnimalsAdjustmentLimitsProvider animalsAdjustmentLimitsProvider) {
    this.animalsAdjustmentProvider = animalsAdjustmentProvider;
    this.animalsAdjustmentLimitsProvider = animalsAdjustmentLimitsProvider;
  }

  @Override
  public Money compute(QuoteForm quoteForm, Money basePrice) {
    Map<AnimalBreed, Integer> animals =
        quoteForm.getPersonalProperty().getAnimals().getCollection();
    Money totalAdjustment = computeAdjustment(basePrice, animals);
    return capAdjustment(totalAdjustment, basePrice);
  }

  private Money computeAdjustment(Money basePrice, Map<AnimalBreed, Integer> ownedAnimals) {
    Money totalAdjustment = Money.ZERO;
    for (Map.Entry<AnimalBreed, Integer> entry : ownedAnimals.entrySet()) {
      QuotePriceAdjustment adjustment =
          animalsAdjustmentProvider.getAdjustment(entry.getKey(), entry.getValue());
      totalAdjustment = totalAdjustment.add(adjustment.apply(basePrice));
    }
    return totalAdjustment;
  }

  private Money capAdjustment(Money totalAdjustment, Money basePrice) {
    QuotePriceAdjustment minimumAdjustment =
        animalsAdjustmentLimitsProvider.getMinimumAdjustment(basePrice);
    QuotePriceAdjustment maximumAdjustment =
        animalsAdjustmentLimitsProvider.getMaximumAdjustment(basePrice);
    return maximumAdjustment.apply(minimumAdjustment.apply(totalAdjustment));
  }
}
