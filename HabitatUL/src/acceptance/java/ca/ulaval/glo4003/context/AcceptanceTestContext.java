package ca.ulaval.glo4003.context;

import ca.ulaval.glo4003.context.service.*;
import ca.ulaval.glo4003.coverage.domain.form.validationpart.UlRegistrarOffice;
import ca.ulaval.glo4003.coverage.domain.premium.formula.bicycleendorsement.BasicBlockCoverageMaximumBicyclePriceProvider;
import ca.ulaval.glo4003.coverage.domain.premium.formula.bicycleendorsement.BicycleEndorsementBasePremiumCalculator;
import ca.ulaval.glo4003.coverage.domain.premium.formula.quote.QuoteBasePremiumCalculator;
import ca.ulaval.glo4003.coverage.domain.premium.formulapart.animals.AnimalsAdjustmentLimitsProvider;
import ca.ulaval.glo4003.coverage.domain.premium.formulapart.animals.AnimalsAdjustmentProvider;
import ca.ulaval.glo4003.coverage.domain.premium.formulapart.bicycleprice.BicyclePriceAdjustmentProvider;
import ca.ulaval.glo4003.coverage.domain.premium.formulapart.civilliabilitylimit.CivilLiabilityLimitAdjustmentProvider;
import ca.ulaval.glo4003.coverage.domain.premium.formulapart.graduatestudent.GraduateStudentAdjustmentProvider;
import ca.ulaval.glo4003.coverage.domain.premium.formulapart.preferentialprogram.PreferentialProgramAdjustmentProvider;
import ca.ulaval.glo4003.coverage.domain.premium.formulapart.roommate.RoommateAdjustmentProvider;
import ca.ulaval.glo4003.coverage.infrastructure.form.validation.DummyUlRegistrarOffice;
import ca.ulaval.glo4003.helper.shared.TemporalGenerator;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteEffectivePeriodProvider;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteRepository;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteValidityPeriodProvider;
import ca.ulaval.glo4003.underwriting.persistence.quote.InMemoryQuoteRepository;

import static ca.ulaval.glo4003.context.ServiceLocator.register;
import static ca.ulaval.glo4003.context.ServiceLocator.reset;

public class AcceptanceTestContext {
  public AcceptanceTestContext() {
    reset();
  }

  public void execute() {
    register(ClockProvider.class, TemporalGenerator.getClockProvider());
    register(QuoteEffectivePeriodProvider.class, new DummyQuoteEffectivePeriodProvider());
    register(UlRegistrarOffice.class, new DummyUlRegistrarOffice());
    registerCalculationServices();
    register(QuoteValidityPeriodProvider.class, new DummyQuoteValidityPeriodProvider());
    register(QuoteRepository.class, new InMemoryQuoteRepository());
  }

  private void registerCalculationServices() {
    register(QuoteBasePremiumCalculator.class, new DummyQuoteBasePremiumCalculator());
    register(
        CivilLiabilityLimitAdjustmentProvider.class,
        new DummyCivilLiabilityLimitAdjustmentProvider());
    register(AnimalsAdjustmentProvider.class, new DummyAnimalsAdjustmentProvider());
    register(AnimalsAdjustmentLimitsProvider.class, new DummyAnimalsAdjustmentLimitsProvider());
    register(
        PreferentialProgramAdjustmentProvider.class,
        new DummyPreferentialProgramAdjustmentProvider());
    register(RoommateAdjustmentProvider.class, new DummyRoommateAdjustmentProvider());
    register(GraduateStudentAdjustmentProvider.class, new DummyGraduateStudentAdjustmentProvider());
    register(
        BicycleEndorsementBasePremiumCalculator.class,
        new DummyBicycleEndorsementBasePremiumCalculator());
    register(BicyclePriceAdjustmentProvider.class, new DummyBicyclePriceAdjustmentProvider());
    register(
        BasicBlockCoverageMaximumBicyclePriceProvider.class,
        new DummyBasicBlockCoverageMaximumBicyclePriceProvider());
  }
}
