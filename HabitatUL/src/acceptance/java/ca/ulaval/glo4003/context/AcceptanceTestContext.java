package ca.ulaval.glo4003.context;

import ca.ulaval.glo4003.calculator.domain.premium.formula.bike.BikeBasePremiumCalculator;
import ca.ulaval.glo4003.calculator.domain.premium.formula.quote.QuoteBasePremiumCalculator;
import ca.ulaval.glo4003.calculator.domain.premium.formulapart.animals.AnimalsAdjustmentLimitsProvider;
import ca.ulaval.glo4003.calculator.domain.premium.formulapart.animals.AnimalsAdjustmentProvider;
import ca.ulaval.glo4003.calculator.domain.premium.formulapart.bikeprice.BikePriceAdjustmentProvider;
import ca.ulaval.glo4003.calculator.domain.premium.formulapart.civilliabilitylimit.CivilLiabilityLimitAdjustmentProvider;
import ca.ulaval.glo4003.calculator.domain.premium.formulapart.graduatestudent.GraduateStudentAdjustmentProvider;
import ca.ulaval.glo4003.calculator.domain.premium.formulapart.preferentialprogram.PreferentialProgramAdjustmentProvider;
import ca.ulaval.glo4003.calculator.domain.premium.formulapart.roommate.RoommateAdjustmentProvider;
import ca.ulaval.glo4003.context.service.*;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import ca.ulaval.glo4003.shared.infrastructure.FixedClockProvider;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteEffectivePeriodProvider;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteRepository;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteValidityPeriodProvider;
import ca.ulaval.glo4003.underwriting.domain.quote.form.validation.UlRegistrarOffice;
import ca.ulaval.glo4003.underwriting.infrastructure.quote.form.validation.DummyUlRegistrarOffice;
import ca.ulaval.glo4003.underwriting.persistence.quote.InMemoryQuoteRepository;

public class AcceptanceTestContext implements Context {
  public AcceptanceTestContext() {
    ServiceLocator.reset();
  }

  @Override
  public void execute() {
    ServiceLocator.register(ClockProvider.class, new FixedClockProvider());
    ServiceLocator.register(
        QuoteEffectivePeriodProvider.class, new DummyQuoteEffectivePeriodProvider());
    ServiceLocator.register(UlRegistrarOffice.class, new DummyUlRegistrarOffice());
    registerCalculationServices();
    ServiceLocator.register(
        QuoteValidityPeriodProvider.class, new DummyQuoteValidityPeriodProvider());
    ServiceLocator.register(QuoteRepository.class, new InMemoryQuoteRepository());
  }

  private void registerCalculationServices() {
    ServiceLocator.register(
        QuoteBasePremiumCalculator.class, new DummyQuoteBasePremiumCalculator());
    ServiceLocator.register(
        CivilLiabilityLimitAdjustmentProvider.class,
        new DummyCivilLiabilityLimitAdjustmentProvider());
    ServiceLocator.register(AnimalsAdjustmentProvider.class, new DummyAnimalsAdjustmentProvider());
    ServiceLocator.register(
        AnimalsAdjustmentLimitsProvider.class, new DummyAnimalsAdjustmentLimitsProvider());
    ServiceLocator.register(
        PreferentialProgramAdjustmentProvider.class,
        new DummyPreferentialProgramAdjustmentProvider());
    ServiceLocator.register(
        RoommateAdjustmentProvider.class, new DummyRoommateAdjustmentProvider());
    ServiceLocator.register(
        GraduateStudentAdjustmentProvider.class, new DummyGraduateStudentAdjustmentProvider());
    ServiceLocator.register(BikeBasePremiumCalculator.class, new DummyBikeBasePremiumCalculator());
    ServiceLocator.register(
        BikePriceAdjustmentProvider.class, new DummyBikePriceAdjustmentProvider());
  }
}
