package ca.ulaval.glo4003.calculator.domain.premium.formulapart.bikeprice;

import ca.ulaval.glo4003.calculator.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.calculator.domain.premium.formula.bike.BikePremiumFormulaPart;
import ca.ulaval.glo4003.calculator.domain.premium.formula.bike.BikePremiumInput;
import ca.ulaval.glo4003.shared.domain.money.Amount;
import ca.ulaval.glo4003.shared.domain.money.Money;

public class BikePriceFormulaPart implements BikePremiumFormulaPart {
  private BikePriceAdjustmentProvider bikePriceAdjustmentProvider;

  public BikePriceFormulaPart(BikePriceAdjustmentProvider bikePriceAdjustmentProvider) {
    this.bikePriceAdjustmentProvider = bikePriceAdjustmentProvider;
  }

  @Override
  public Money compute(BikePremiumInput bikePremiumInput, Money basePremium) {
    Amount bikePrice = bikePremiumInput.getPrice();
    PremiumAdjustment adjustment = bikePriceAdjustmentProvider.getAdjustment(bikePrice);
    return adjustment.apply(new Money(bikePrice));
  }
}
