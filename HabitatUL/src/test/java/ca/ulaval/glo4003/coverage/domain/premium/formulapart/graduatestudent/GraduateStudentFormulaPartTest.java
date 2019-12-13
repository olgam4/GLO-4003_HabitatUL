package ca.ulaval.glo4003.coverage.domain.premium.formulapart.graduatestudent;

import ca.ulaval.glo4003.coverage.domain.form.identity.UniversityProgram;
import ca.ulaval.glo4003.coverage.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.coverage.domain.premium.formula.quote.QuotePremiumInput;
import ca.ulaval.glo4003.coverage.helper.form.identity.UniversityProgramBuilder;
import ca.ulaval.glo4003.coverage.helper.premium.QuotePremiumInputBuilder;
import ca.ulaval.glo4003.shared.domain.identity.Cycle;
import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.shared.helper.MoneyGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static ca.ulaval.glo4003.coverage.helper.form.identity.UniversityProgramGenerator.createCycle;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GraduateStudentFormulaPartTest {
  private static final Money BASE_PREMIUM = MoneyGenerator.createMoney();
  private static final Money PREMIUM_ADJUSTMENT = MoneyGenerator.createMoney();
  private static final Cycle CYCLE = createCycle();
  private static final UniversityProgram UNIVERSITY_PROGRAM =
      UniversityProgramBuilder.aUniversityProgram().withCycle(CYCLE).build();
  private static final QuotePremiumInput QUOTE_PREMIUM_INPUT =
      QuotePremiumInputBuilder.aQuotePremiumInput()
          .withNamedInsuredUniversityProgram(UNIVERSITY_PROGRAM)
          .build();

  @Mock private GraduateStudentAdjustmentProvider graduateStudentAdjustmentProvider;
  @Mock private PremiumAdjustment premiumAdjustment;

  private GraduateStudentFormulaPart subject;

  @Before
  public void setUp() {
    when(graduateStudentAdjustmentProvider.getAdjustment(any(Cycle.class)))
        .thenReturn(premiumAdjustment);
    when(premiumAdjustment.apply(any(Money.class))).thenReturn(PREMIUM_ADJUSTMENT);
    subject = new GraduateStudentFormulaPart(graduateStudentAdjustmentProvider);
  }

  @Test
  public void computingFormulaPart_shouldGetAdjustment() {
    subject.compute(QUOTE_PREMIUM_INPUT, BASE_PREMIUM);

    verify(graduateStudentAdjustmentProvider).getAdjustment(CYCLE);
  }

  @Test
  public void computingFormulaPart_shouldApplyAdjustment() {
    subject.compute(QUOTE_PREMIUM_INPUT, BASE_PREMIUM);

    verify(premiumAdjustment).apply(BASE_PREMIUM);
  }

  @Test
  public void computingFormulaPart_shouldReturnPremiumAdjustment() {
    Money premiumAdjustment = subject.compute(QUOTE_PREMIUM_INPUT, BASE_PREMIUM);

    assertEquals(PREMIUM_ADJUSTMENT, premiumAdjustment);
  }
}
