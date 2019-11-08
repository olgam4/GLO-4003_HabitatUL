package ca.ulaval.glo4003.calculator.domain.premium;

import ca.ulaval.glo4003.calculator.domain.premium.formulapart.QuotePremiumFormulaPart;
import ca.ulaval.glo4003.calculator.domain.premium.input.QuotePremiumInput;
import ca.ulaval.glo4003.shared.domain.money.Money;

import java.util.ArrayList;
import java.util.List;

public class QuotePremiumFormula {
  private QuoteBasePremiumCalculator quoteBasePremiumCalculator;
  private List<QuotePremiumFormulaPart> formulaParts = new ArrayList<>();

  public QuotePremiumFormula(QuoteBasePremiumCalculator quoteBasePremiumCalculator) {
    this.quoteBasePremiumCalculator = quoteBasePremiumCalculator;
  }

  public void addFormulaPart(QuotePremiumFormulaPart formulaPart) {
    formulaParts.add(formulaPart);
  }

  public Money compute(QuotePremiumInput quotePremiumInput) {
    Money basePremium = quoteBasePremiumCalculator.computeQuoteBasePremium(quotePremiumInput);
    Money premium = basePremium;

    for (QuotePremiumFormulaPart formulaPart : formulaParts) {
      Money premiumAdjustment = formulaPart.compute(quotePremiumInput, basePremium);
      premium = premium.add(premiumAdjustment);
    }

    return premium;
  }
}
