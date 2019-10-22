package ca.ulaval.glo4003.underwriting.domain.quote.price.formulapart;

import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;

public interface QuotePriceFormulaPart {
  Money compute(QuoteForm quoteForm, Money basePrice);
}
