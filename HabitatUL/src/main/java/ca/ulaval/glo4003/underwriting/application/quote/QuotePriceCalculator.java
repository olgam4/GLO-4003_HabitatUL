package ca.ulaval.glo4003.underwriting.application.quote;

import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;
import ca.ulaval.glo4003.underwriting.domain.quote.price.QuoteIndicatedPriceCalculator;
import ca.ulaval.glo4003.underwriting.domain.quote.price.QuotePriceFormula;

public class QuotePriceCalculator {
  private QuotePriceFormula quotePriceFormula;

  public QuotePriceCalculator() {
    this(assembleFormula());
  }

  public QuotePriceCalculator(QuotePriceFormula quotePriceFormula) {
    this.quotePriceFormula = quotePriceFormula;
  }

  private static QuotePriceFormula assembleFormula() {
    QuotePriceFormula quotePriceFormula =
        new QuotePriceFormula(ServiceLocator.resolve(QuoteIndicatedPriceCalculator.class));
    // TODO: add formula parts here
    return quotePriceFormula;
  }

  public Money compute(QuoteForm quoteForm) {
    return quotePriceFormula.compute(quoteForm);
  }
}
