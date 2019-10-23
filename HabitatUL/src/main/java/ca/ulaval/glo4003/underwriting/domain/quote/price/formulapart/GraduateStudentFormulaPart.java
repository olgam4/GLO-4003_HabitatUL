package ca.ulaval.glo4003.underwriting.domain.quote.price.formulapart;

import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;
import ca.ulaval.glo4003.underwriting.domain.quote.price.GraduateStudentAdjustmentProvider;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.QuotePriceAdjustment;

public class GraduateStudentFormulaPart implements QuotePriceFormulaPart {
  private GraduateStudentAdjustmentProvider graduateStudentAdjustmentProvider;

  public GraduateStudentFormulaPart(
      GraduateStudentAdjustmentProvider graduateStudentAdjustmentProvider) {
    this.graduateStudentAdjustmentProvider = graduateStudentAdjustmentProvider;
  }

  @Override
  public Money compute(QuoteForm quoteForm, Money basePrice) {
    String cycle = quoteForm.getPersonalInformation().getUniversityProfile().getCycle();
    QuotePriceAdjustment adjustment = graduateStudentAdjustmentProvider.getAdjustment(cycle);
    return adjustment.apply(basePrice);
  }
}
