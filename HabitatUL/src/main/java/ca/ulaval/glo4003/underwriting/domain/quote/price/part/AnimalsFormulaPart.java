package ca.ulaval.glo4003.underwriting.domain.quote.price.part;

import ca.ulaval.glo4003.shared.domain.money.Amount;
import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;
import ca.ulaval.glo4003.underwriting.domain.quote.form.personalproperty.AnimalBreed;
import ca.ulaval.glo4003.underwriting.domain.quote.price.AnimalsAdjustmentProvider;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.MultiplicativeQuotePriceAdjustment;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.QuotePriceAdjustment;

import java.util.Map;

public class AnimalsFormulaPart implements QuotePriceFormulaPart {
  static final QuotePriceAdjustment MAXIMUM_ADJUSTMENT = new MultiplicativeQuotePriceAdjustment(1f);
  static final QuotePriceAdjustment MINIMUM_ADJUSTMENT =
      new MultiplicativeQuotePriceAdjustment(-0.1f);

  private AnimalsAdjustmentProvider animalsAdjustmentProvider;

  public AnimalsFormulaPart(AnimalsAdjustmentProvider animalsAdjustmentProvider) {
    this.animalsAdjustmentProvider = animalsAdjustmentProvider;
  }

  @Override
  public Money compute(QuoteForm quoteForm, Money basePrice) {
    Map<AnimalBreed, Integer> ownedAnimals =
        quoteForm.getPersonalProperty().getAnimals().getCollection();

    Money totalAdjustment = new Money(Amount.ZERO);
    for (Map.Entry<AnimalBreed, Integer> entry : ownedAnimals.entrySet()) {
      QuotePriceAdjustment adjustment =
          animalsAdjustmentProvider.getAdjustment(entry.getKey(), entry.getValue());
      totalAdjustment = totalAdjustment.add(adjustment.apply(basePrice));
    }

    return capAdjustment(totalAdjustment, basePrice);
  }

  private Money capAdjustment(Money totalAdjustment, Money basePrice) {
    Money maximumAdjustment = MAXIMUM_ADJUSTMENT.apply(basePrice);
    Money minimumAdjustment = MINIMUM_ADJUSTMENT.apply(basePrice);
    return Money.max(minimumAdjustment, Money.min(maximumAdjustment, totalAdjustment));
  }
}
