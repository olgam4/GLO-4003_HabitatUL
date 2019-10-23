package ca.ulaval.glo4003.underwriting.domain.quote.price.formulapart;

import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;
import ca.ulaval.glo4003.underwriting.domain.quote.form.identity.Gender;
import ca.ulaval.glo4003.underwriting.domain.quote.price.RoommateAdjustmentProvider;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.NoQuotePriceAdjustment;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.QuotePriceAdjustment;

import java.util.Optional;

public class RoommateFormulaPart implements QuotePriceFormulaPart {
  private RoommateAdjustmentProvider roommateAdjustmentProvider;

  public RoommateFormulaPart(RoommateAdjustmentProvider roommateAdjustmentProvider) {
    this.roommateAdjustmentProvider = roommateAdjustmentProvider;
  }

  @Override
  public Money compute(QuoteForm quoteForm, Money basePrice) {
    Gender namedInsuredGender = quoteForm.getPersonalInformation().getGender();
    Optional<Gender> additionalInsuredGender =
        Optional.ofNullable(quoteForm.getAdditionalInsured().getGender());
    QuotePriceAdjustment adjustment =
        additionalInsuredGender
            .map(x -> roommateAdjustmentProvider.getAdjustment(namedInsuredGender, x))
            .orElse(new NoQuotePriceAdjustment());
    return adjustment.apply(basePrice);
  }
}
