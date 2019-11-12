package ca.ulaval.glo4003.coverage.domain.premium.formulapart.preferentialprogram;

import ca.ulaval.glo4003.coverage.domain.form.identity.UniversityProgram;
import ca.ulaval.glo4003.coverage.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.coverage.domain.premium.formula.quote.QuotePremiumInput;
import ca.ulaval.glo4003.helper.calculator.form.identity.UniversityProgramBuilder;
import ca.ulaval.glo4003.helper.calculator.premium.QuotePremiumInputBuilder;
import ca.ulaval.glo4003.helper.shared.MoneyGenerator;
import ca.ulaval.glo4003.shared.domain.money.Money;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Arrays;
import java.util.Collection;

import static ca.ulaval.glo4003.coverage.domain.form.identity.UniversityProgram.UNFILLED_UNIVERSITY_PROGRAM;
import static ca.ulaval.glo4003.helper.calculator.form.identity.UniversityProgramGenerator.*;
import static ca.ulaval.glo4003.helper.shared.ParameterizedTestHelper.PARAMETERIZED_TEST_TITLE;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(Parameterized.class)
public class PreferentialProgramFormulaPartTest {
  private static final Money BASE_PREMIUM = MoneyGenerator.createMoney();
  private static final Money PREMIUM_ADJUSTMENT = MoneyGenerator.createMoney();
  private static final Money SMALLER_PREMIUM_ADJUSTMENT =
      MoneyGenerator.createMoneySmallerThan(PREMIUM_ADJUSTMENT);
  private static final String CYCLE = createCycle();
  private static final String DEGREE = createDegree();
  private static final String MAJOR = createMajor();
  private static final UniversityProgram FILLED_UNIVERSITY_PROGRAM =
      UniversityProgramBuilder.aUniversityProgram()
          .withCycle(CYCLE)
          .withDegree(DEGREE)
          .withMajor(MAJOR)
          .build();
  private static final String ANOTHER_CYCLE = createCycle();
  private static final String ANOTHER_DEGREE = createDegree();
  private static final String ANOTHER_MAJOR = createMajor();
  private static final UniversityProgram ANOTHER_FILLED_UNIVERSITY_PROGRAM =
      UniversityProgramBuilder.aUniversityProgram()
          .withCycle(ANOTHER_CYCLE)
          .withDegree(ANOTHER_DEGREE)
          .withMajor(ANOTHER_MAJOR)
          .build();

  @Rule public MockitoRule mockitoRule = MockitoJUnit.rule().silent();
  @Mock private PreferentialProgramAdjustmentProvider preferentialProgramAdjustmentProvider;
  @Mock private PremiumAdjustment premiumAdjustment;
  @Mock private PremiumAdjustment anotherPremiumAdjustment;

  private PreferentialProgramFormulaPart subject;
  private UniversityProgram namedInsuredUniversityProgram;
  private UniversityProgram additionalInsuredUniversityProgram;
  private Money expectedPremiumAdjustment;

  public PreferentialProgramFormulaPartTest(
      String title,
      UniversityProgram namedInsuredUniversityProgram,
      UniversityProgram additionalInsuredUniversityProgram,
      Money expectedPremiumAdjustment) {
    this.namedInsuredUniversityProgram = namedInsuredUniversityProgram;
    this.additionalInsuredUniversityProgram = additionalInsuredUniversityProgram;
    this.expectedPremiumAdjustment = expectedPremiumAdjustment;
  }

  @Parameterized.Parameters(name = PARAMETERIZED_TEST_TITLE)
  public static Collection parameters() {
    return Arrays.asList(
        new Object[][] {
          {
            "with named and additional insureds not eligible should compute null adjustment",
            UNFILLED_UNIVERSITY_PROGRAM,
            UNFILLED_UNIVERSITY_PROGRAM,
            Money.ZERO
          },
          {
            "with named insured eligible should compute associated adjustment",
            FILLED_UNIVERSITY_PROGRAM,
            UNFILLED_UNIVERSITY_PROGRAM,
            PREMIUM_ADJUSTMENT
          },
          {
            "with additional insured eligible should compute associated adjustment",
            UNFILLED_UNIVERSITY_PROGRAM,
            FILLED_UNIVERSITY_PROGRAM,
            PREMIUM_ADJUSTMENT
          },
          {
            "with named insured with smaller adjustment should compute smaller adjustment",
            ANOTHER_FILLED_UNIVERSITY_PROGRAM,
            FILLED_UNIVERSITY_PROGRAM,
            SMALLER_PREMIUM_ADJUSTMENT
          },
          {
            "with additional insured with smaller adjustment should compute smaller adjustment",
            FILLED_UNIVERSITY_PROGRAM,
            ANOTHER_FILLED_UNIVERSITY_PROGRAM,
            SMALLER_PREMIUM_ADJUSTMENT
          },
        });
  }

  @Before
  public void setUp() {
    when(preferentialProgramAdjustmentProvider.getAdjustment(CYCLE, DEGREE, MAJOR))
        .thenReturn(premiumAdjustment);
    when(premiumAdjustment.apply(any(Money.class))).thenReturn(PREMIUM_ADJUSTMENT);
    when(preferentialProgramAdjustmentProvider.getAdjustment(
            ANOTHER_CYCLE, ANOTHER_DEGREE, ANOTHER_MAJOR))
        .thenReturn(anotherPremiumAdjustment);
    when(anotherPremiumAdjustment.apply(any(Money.class))).thenReturn(SMALLER_PREMIUM_ADJUSTMENT);
    subject = new PreferentialProgramFormulaPart(preferentialProgramAdjustmentProvider);
  }

  @Test
  public void computingFormulaPart() {
    QuotePremiumInput quotePremiumInput =
        QuotePremiumInputBuilder.aQuotePremiumInput()
            .withNamedInsuredUniversityProgram(namedInsuredUniversityProgram)
            .withAdditionalInsuredUniversityProgram(additionalInsuredUniversityProgram)
            .build();

    Money premiumAdjustment = subject.compute(quotePremiumInput, BASE_PREMIUM);

    assertEquals(expectedPremiumAdjustment, premiumAdjustment);
  }
}
