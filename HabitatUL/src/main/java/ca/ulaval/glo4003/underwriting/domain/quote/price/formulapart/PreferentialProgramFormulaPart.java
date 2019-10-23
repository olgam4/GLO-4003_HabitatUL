package ca.ulaval.glo4003.underwriting.domain.quote.price.formulapart;

import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;
import ca.ulaval.glo4003.underwriting.domain.quote.form.identity.UniversityProfile;
import ca.ulaval.glo4003.underwriting.domain.quote.price.PreferentialProgramAdjustmentProvider;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.QuotePriceAdjustment;

import java.util.Optional;

public class PreferentialProgramFormulaPart implements QuotePriceFormulaPart {
  private PreferentialProgramAdjustmentProvider preferentialProgramAdjustmentProvider;

  public PreferentialProgramFormulaPart(
      PreferentialProgramAdjustmentProvider preferentialProgramAdjustmentProvider) {
    this.preferentialProgramAdjustmentProvider = preferentialProgramAdjustmentProvider;
  }

  @Override
  public Money compute(QuoteForm quoteForm, Money basePrice) {
    Optional<Money> namedInsuredBasedAdjustment =
        computeNamedInsuredPriceAdjustmentAmount(quoteForm, basePrice);
    Optional<Money> additionalInsuredBasedAdjustment =
        computeAdditionalInsuredPriceAdjustmentAmount(quoteForm, basePrice);

    if (namedInsuredBasedAdjustment.isPresent() && additionalInsuredBasedAdjustment.isPresent()) {
      return Money.min(namedInsuredBasedAdjustment.get(), additionalInsuredBasedAdjustment.get());
    }

    return namedInsuredBasedAdjustment.orElseGet(
        () -> additionalInsuredBasedAdjustment.orElse(Money.ZERO));
  }

  private Optional<Money> computeNamedInsuredPriceAdjustmentAmount(
      QuoteForm quoteForm, Money basePrice) {
    UniversityProfile namedInsuredUniversityProfile =
        quoteForm.getPersonalInformation().getUniversityProfile();
    Money adjustmentAmount = computePriceAdjustmentAmount(namedInsuredUniversityProfile, basePrice);
    return Optional.ofNullable(adjustmentAmount);
  }

  private Optional<Money> computeAdditionalInsuredPriceAdjustmentAmount(
      QuoteForm quoteForm, Money basePrice) {
    UniversityProfile additionalInsuredUniversityProfile =
        quoteForm.getAdditionalInsured().getUniversityProfile();
    Money adjustmentAmount =
        computePriceAdjustmentAmount(additionalInsuredUniversityProfile, basePrice);
    return Optional.ofNullable(adjustmentAmount);
  }

  private Money computePriceAdjustmentAmount(UniversityProfile universityProfile, Money basePrice) {
    if (!universityProfile.isFilled()) return null;

    String program = universityProfile.getProgram();
    QuotePriceAdjustment adjustment = preferentialProgramAdjustmentProvider.getAdjustment(program);
    return adjustment.apply(basePrice);
  }
}
