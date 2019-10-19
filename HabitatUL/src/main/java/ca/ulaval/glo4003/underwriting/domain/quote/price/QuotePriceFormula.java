package ca.ulaval.glo4003.underwriting.domain.quote.price;

import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;
import ca.ulaval.glo4003.underwriting.domain.quote.price.part.QuotePriceFormulaPart;

import java.util.ArrayList;
import java.util.List;

public class QuotePriceFormula {
  private QuoteBasePriceCalculator quoteBasePriceCalculator;
  private List<QuotePriceFormulaPart> quotePriceFormulaParts = new ArrayList<>();

  public QuotePriceFormula(QuoteBasePriceCalculator quoteBasePriceCalculator) {
    this.quoteBasePriceCalculator = quoteBasePriceCalculator;
  }

  public void addFormulaPart(QuotePriceFormulaPart quotePriceFormulaPart) {
    quotePriceFormulaParts.add(quotePriceFormulaPart);
  }

  public Money compute(QuoteForm quoteForm) {
    Money basePrice = quoteBasePriceCalculator.computeQuoteBasePrice(quoteForm);
    Money price = basePrice;

    for (QuotePriceFormulaPart quotePriceFormulaPart : quotePriceFormulaParts) {
      Money priceAdjustmentAmount =
          quotePriceFormulaPart.computeAdjustmentAmount(quoteForm, basePrice);
      price = price.add(priceAdjustmentAmount);
    }

    return price;
  }
}
