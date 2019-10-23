package ca.ulaval.glo4003.underwriting.domain.quote.price.formulapart;

import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;
import ca.ulaval.glo4003.underwriting.domain.quote.form.civilliability.CivilLiabilityLimit;
import ca.ulaval.glo4003.underwriting.domain.quote.price.CivilLiabilityLimitAdjustmentProvider;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.QuotePriceAdjustment;

public class CivilLiabilityLimitFormulaPart implements QuotePriceFormulaPart {
  private CivilLiabilityLimitAdjustmentProvider civilLiabilityLimitAdjustmentProvider;

  public CivilLiabilityLimitFormulaPart(
      CivilLiabilityLimitAdjustmentProvider civilLiabilityLimitAdjustmentProvider) {
    this.civilLiabilityLimitAdjustmentProvider = civilLiabilityLimitAdjustmentProvider;
  }

  @Override
  public Money compute(QuoteForm quoteForm, Money basePrice) {
    CivilLiabilityLimit civilLiabilityLimit = quoteForm.getCivilLiability().getLimit();
    QuotePriceAdjustment adjustment =
        civilLiabilityLimitAdjustmentProvider.getAdjustment(civilLiabilityLimit);
    return adjustment.apply(basePrice);
  }
}
