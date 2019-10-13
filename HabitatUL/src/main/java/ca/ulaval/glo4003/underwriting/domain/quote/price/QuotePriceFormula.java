package ca.ulaval.glo4003.underwriting.domain.quote.price;

import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;

import java.util.ArrayList;
import java.util.List;

public class QuotePriceFormula {
  private QuoteIndicatedPriceCalculator quoteIndicatedPriceCalculator;
  private List<QuotePriceFormulaPart> quotePriceFormulaParts = new ArrayList<>();

  public QuotePriceFormula(QuoteIndicatedPriceCalculator quoteIndicatedPriceCalculator) {
    this.quoteIndicatedPriceCalculator = quoteIndicatedPriceCalculator;
  }

  public void addFormulaPart(QuotePriceFormulaPart quotePriceFormulaPart) {
    quotePriceFormulaParts.add(quotePriceFormulaPart);
  }

  public Money compute(QuoteForm quoteForm) {
    Money price = quoteIndicatedPriceCalculator.computeIndicatedQuotePrice(quoteForm);

    for (QuotePriceFormulaPart quotePriceFormulaPart : quotePriceFormulaParts) {
      price = quotePriceFormulaPart.apply(quoteForm, price);
    }

    return price;
  }
}
