package ca.ulaval.glo4003.coverage.domain.premium.formulapart.preferentialprogram;

import ca.ulaval.glo4003.coverage.domain.form.identity.UniversityProgram;
import ca.ulaval.glo4003.coverage.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.coverage.domain.premium.formula.quote.QuoteBasicBlockPremiumFormulaPart;
import ca.ulaval.glo4003.coverage.domain.premium.formula.quote.QuotePremiumInput;
import ca.ulaval.glo4003.shared.domain.money.Money;

import java.util.Optional;

public class PreferentialProgramFormulaPart implements QuoteBasicBlockPremiumFormulaPart {
  private PreferentialProgramAdjustmentProvider preferentialProgramAdjustmentProvider;

  public PreferentialProgramFormulaPart(
      PreferentialProgramAdjustmentProvider preferentialProgramAdjustmentProvider) {
    this.preferentialProgramAdjustmentProvider = preferentialProgramAdjustmentProvider;
  }

  @Override
  public Money compute(QuotePremiumInput quotePremiumInput, Money basePremium) {
    Optional<Money> namedInsuredPremiumAdjustment =
        computeNamedInsuredPremiumAdjustment(quotePremiumInput, basePremium);
    Optional<Money> additionalInsuredPremiumAdjustment =
        computeAdditionalInsuredPremiumAdjustment(quotePremiumInput, basePremium);

    if (namedInsuredPremiumAdjustment.isPresent()
        && additionalInsuredPremiumAdjustment.isPresent()) {
      return Money.min(
          namedInsuredPremiumAdjustment.get(), additionalInsuredPremiumAdjustment.get());
    }

    return namedInsuredPremiumAdjustment.orElseGet(
        () -> additionalInsuredPremiumAdjustment.orElse(Money.ZERO));
  }

  private Optional<Money> computeNamedInsuredPremiumAdjustment(
      QuotePremiumInput quotePremiumInput, Money basePremium) {
    UniversityProgram namedInsuredUniversityProgram =
        quotePremiumInput.getNamedInsuredUniversityProgram();
    Money premiumAdjustment = computePremiumAdjustment(namedInsuredUniversityProgram, basePremium);
    return Optional.ofNullable(premiumAdjustment);
  }

  private Optional<Money> computeAdditionalInsuredPremiumAdjustment(
      QuotePremiumInput quotePremiumInput, Money basePremium) {
    UniversityProgram additionalInsuredUniversityProgram =
        quotePremiumInput.getAdditionalInsuredUniversityProgram();
    Money premiumAdjustment =
        computePremiumAdjustment(additionalInsuredUniversityProgram, basePremium);
    return Optional.ofNullable(premiumAdjustment);
  }

  private Money computePremiumAdjustment(UniversityProgram universityProgram, Money basePremium) {
    if (!universityProgram.isFilled()) return null;

    PremiumAdjustment adjustment =
        preferentialProgramAdjustmentProvider.getAdjustment(
            universityProgram.getCycle(),
            universityProgram.getDegree(),
            universityProgram.getMajor());
    return adjustment.apply(basePremium);
  }
}
