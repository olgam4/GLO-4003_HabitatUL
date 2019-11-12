package ca.ulaval.glo4003.coverage.domain.premium.formulapart.bicycleprice;

import ca.ulaval.glo4003.coverage.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.coverage.domain.premium.formula.bicycleendorsement.BicycleEndorsementPremiumFormulaPart;
import ca.ulaval.glo4003.coverage.domain.premium.formula.bicycleendorsement.BicycleEndorsementPremiumInput;
import ca.ulaval.glo4003.shared.domain.money.Amount;
import ca.ulaval.glo4003.shared.domain.money.Money;

public class BicycleEndorsementPriceFormulaPart implements BicycleEndorsementPremiumFormulaPart {
  private BicyclePriceAdjustmentProvider bicyclePriceAdjustmentProvider;

  public BicycleEndorsementPriceFormulaPart(
      BicyclePriceAdjustmentProvider bicyclePriceAdjustmentProvider) {
    this.bicyclePriceAdjustmentProvider = bicyclePriceAdjustmentProvider;
  }

  @Override
  public Money compute(
      BicycleEndorsementPremiumInput bicycleEndorsementPremiumInput, Money basePremium) {
    Amount bicyclePrice = bicycleEndorsementPremiumInput.getPrice();
    PremiumAdjustment adjustment = bicyclePriceAdjustmentProvider.getAdjustment(bicyclePrice);
    return adjustment.apply(new Money(bicyclePrice));
  }
}
