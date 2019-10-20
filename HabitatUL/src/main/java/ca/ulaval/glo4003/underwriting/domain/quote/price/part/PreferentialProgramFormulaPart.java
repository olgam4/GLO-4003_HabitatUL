package ca.ulaval.glo4003.underwriting.domain.quote.price.part;

import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;
import ca.ulaval.glo4003.underwriting.domain.quote.price.PreferentialProgramAdjustmentProvider;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.QuotePriceAdjustment;

public class PreferentialProgramFormulaPart implements QuotePriceFormulaPart {
  private PreferentialProgramAdjustmentProvider preferentialProgramAdjustmentProvider;

  public PreferentialProgramFormulaPart(
      PreferentialProgramAdjustmentProvider preferentialProgramAdjustmentProvider) {
    this.preferentialProgramAdjustmentProvider = preferentialProgramAdjustmentProvider;
  }

  @Override
  public Money compute(QuoteForm quoteForm, Money basePrice) {
    QuotePriceAdjustment adjustment =
        preferentialProgramAdjustmentProvider.getAdjustment(
            quoteForm.getStudentInformation().getProgram());
    return adjustment.apply(basePrice);
  }
}
