package ca.ulaval.glo4003.underwriting.application.quote;

import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;
import ca.ulaval.glo4003.underwriting.domain.quote.price.*;
import ca.ulaval.glo4003.underwriting.domain.quote.price.formulapart.AnimalsFormulaPart;
import ca.ulaval.glo4003.underwriting.domain.quote.price.formulapart.PreferentialProgramFormulaPart;
import ca.ulaval.glo4003.underwriting.domain.quote.price.formulapart.RoommateFormulaPart;

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
    quotePriceFormula.addFormulaPart(
        new RoommateFormulaPart(ServiceLocator.resolve(RoommateAdjustmentProvider.class)));
    // TODO: add other formula parts
    return quotePriceFormula;
  }

  public Money compute(QuoteForm quoteForm) {
    return quotePriceFormula.compute(quoteForm);
  }
}
