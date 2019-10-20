package ca.ulaval.glo4003.underwriting.application.quote;

import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;
import ca.ulaval.glo4003.underwriting.domain.quote.price.AnimalsAdjustmentProvider;
import ca.ulaval.glo4003.underwriting.domain.quote.price.PreferentialProgramAdjustmentProvider;
import ca.ulaval.glo4003.underwriting.domain.quote.price.QuoteBasePriceCalculator;
import ca.ulaval.glo4003.underwriting.domain.quote.price.QuotePriceFormula;
import ca.ulaval.glo4003.underwriting.domain.quote.price.part.AnimalsFormulaPart;
import ca.ulaval.glo4003.underwriting.domain.quote.price.part.PreferentialProgramFormulaPart;

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
        new QuotePriceFormula(ServiceLocator.resolve(QuoteBasePriceCalculator.class));
    quotePriceFormula.addFormulaPart(
        new AnimalsFormulaPart(ServiceLocator.resolve(AnimalsAdjustmentProvider.class)));
    quotePriceFormula.addFormulaPart(
        new PreferentialProgramFormulaPart(
            ServiceLocator.resolve(PreferentialProgramAdjustmentProvider.class)));
    // TODO: add other formula parts
    return quotePriceFormula;
  }

  public Money compute(QuoteForm quoteForm) {
    return quotePriceFormula.compute(quoteForm);
  }
}
