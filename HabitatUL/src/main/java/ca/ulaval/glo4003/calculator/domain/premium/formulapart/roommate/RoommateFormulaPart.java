package ca.ulaval.glo4003.calculator.domain.premium.formulapart.roommate;

import ca.ulaval.glo4003.calculator.domain.premium.adjustment.NullPremiumAdjustment;
import ca.ulaval.glo4003.calculator.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.calculator.domain.premium.formula.quote.QuotePremiumFormulaPart;
import ca.ulaval.glo4003.calculator.domain.premium.formula.quote.QuotePremiumInput;
import ca.ulaval.glo4003.shared.domain.identity.Gender;
import ca.ulaval.glo4003.shared.domain.money.Money;

import java.util.Optional;

public class RoommateFormulaPart implements QuotePremiumFormulaPart {
  private RoommateAdjustmentProvider roommateAdjustmentProvider;

  public RoommateFormulaPart(RoommateAdjustmentProvider roommateAdjustmentProvider) {
    this.roommateAdjustmentProvider = roommateAdjustmentProvider;
  }

  @Override
  public Money compute(QuotePremiumInput quotePremiumInput, Money basePremium) {
    Gender namedInsuredGender = quotePremiumInput.getNamedInsuredGender();
    Optional<Gender> additionalInsuredGender =
        Optional.ofNullable(quotePremiumInput.getAdditionalInsuredGender());
    PremiumAdjustment adjustment =
        additionalInsuredGender
            .map(x -> roommateAdjustmentProvider.getAdjustment(namedInsuredGender, x))
            .orElse(new NullPremiumAdjustment());
    return adjustment.apply(basePremium);
  }
}
