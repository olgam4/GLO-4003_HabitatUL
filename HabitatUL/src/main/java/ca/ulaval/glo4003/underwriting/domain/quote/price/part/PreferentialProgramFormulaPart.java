package ca.ulaval.glo4003.underwriting.domain.quote.price.part;

import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;
import ca.ulaval.glo4003.underwriting.domain.quote.form.identity.Identity;
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
    Money namedInsuredBasedAdjustment =
        computeNamedInsuredPriceAdjustmentAmount(quoteForm, basePrice);
    Optional<Money> additionalInsuredBasedAdjustment =
        computeAdditionalInsuredPriceAdjustmentAmount(quoteForm, basePrice);
    return additionalInsuredBasedAdjustment
        .map(x -> Money.min(namedInsuredBasedAdjustment, x))
        .orElse(namedInsuredBasedAdjustment);
  }

  private Money computeNamedInsuredPriceAdjustmentAmount(QuoteForm quoteForm, Money basePrice) {
    String program = quoteForm.getPersonalInformation().getUniversityProfile().getProgram();
    return computePriceAdjustmentAmount(program, basePrice);
  }

  private Optional<Money> computeAdditionalInsuredPriceAdjustmentAmount(
      QuoteForm quoteForm, Money basePrice) {
    Optional<Identity> additionalInsured = Optional.ofNullable(quoteForm.getAdditionalInsured());
    Optional<String> program =
        additionalInsured.flatMap(
            x -> Optional.ofNullable(x.getUniversityProfile()).map(UniversityProfile::getProgram));
    return program.map(x -> computePriceAdjustmentAmount(x, basePrice));
  }

  private Money computePriceAdjustmentAmount(String program, Money basePrice) {
    QuotePriceAdjustment adjustment = preferentialProgramAdjustmentProvider.getAdjustment(program);
    return adjustment.apply(basePrice);
  }
}
