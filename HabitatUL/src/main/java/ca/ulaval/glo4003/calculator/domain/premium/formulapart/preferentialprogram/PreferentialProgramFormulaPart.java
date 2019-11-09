package ca.ulaval.glo4003.calculator.domain.premium.formulapart.preferentialprogram;

import ca.ulaval.glo4003.calculator.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.calculator.domain.premium.formula.quote.QuotePremiumFormulaPart;
import ca.ulaval.glo4003.calculator.domain.premium.formula.quote.QuotePremiumInput;
import ca.ulaval.glo4003.calculator.domain.premium.formula.quote.input.UniversityProgramInput;
import ca.ulaval.glo4003.shared.domain.money.Money;

import java.util.Optional;

public class PreferentialProgramFormulaPart implements QuotePremiumFormulaPart {
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
    UniversityProgramInput namedInsuredUniversityProgram =
        quotePremiumInput.getNamedInsuredUniversityProgram();
    Money premiumAdjustment = computePremiumAdjustment(namedInsuredUniversityProgram, basePremium);
    return Optional.ofNullable(premiumAdjustment);
  }

  private Optional<Money> computeAdditionalInsuredPremiumAdjustment(
      QuotePremiumInput quotePremiumInput, Money basePremium) {
    UniversityProgramInput additionalInsuredUniversityProgramInput =
        quotePremiumInput.getAdditionalInsuredUniversityProgram();
    Money premiumAdjustment =
        computePremiumAdjustment(additionalInsuredUniversityProgramInput, basePremium);
    return Optional.ofNullable(premiumAdjustment);
  }

  private Money computePremiumAdjustment(
      UniversityProgramInput universityProgramInput, Money basePremium) {
    if (!universityProgramInput.isCompleted()) return null;

    String cycle = universityProgramInput.getCycle();
    String degree = universityProgramInput.getDegree();
    String program = universityProgramInput.getProgram();

    PremiumAdjustment adjustment =
        preferentialProgramAdjustmentProvider.getAdjustment(cycle, degree, program);
    return adjustment.apply(basePremium);
  }
}
