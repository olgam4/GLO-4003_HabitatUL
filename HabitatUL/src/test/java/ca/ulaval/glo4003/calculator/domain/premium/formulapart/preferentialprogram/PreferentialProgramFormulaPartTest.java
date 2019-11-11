package ca.ulaval.glo4003.calculator.domain.premium.formulapart.preferentialprogram;

import ca.ulaval.glo4003.calculator.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.calculator.domain.premium.formula.quote.QuotePremiumInput;
import ca.ulaval.glo4003.calculator.domain.premium.formula.quote.input.UniversityProgramInput;
import ca.ulaval.glo4003.helper.MoneyGenerator;
import ca.ulaval.glo4003.helper.calculator.QuotePremiumInputBuilder;
import ca.ulaval.glo4003.helper.calculator.UniversityProgramInputBuilder;
import ca.ulaval.glo4003.helper.calculator.UniversityProgramInputGenerator;
import ca.ulaval.glo4003.shared.domain.money.Money;
import com.github.javafaker.Faker;
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

import static ca.ulaval.glo4003.helper.ParameterizedTestHelper.PARAMETERIZED_TEST_TITLE;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(Parameterized.class)
public class PreferentialProgramFormulaPartTest {
  private static final Money BASE_PREMIUM = MoneyGenerator.createMoney();
  private static final Money PREMIUM_ADJUSTMENT = MoneyGenerator.createMoney();
  private static final Money SMALLER_PREMIUM_ADJUSTMENT =
      MoneyGenerator.createMoneySmallerThan(PREMIUM_ADJUSTMENT);
  private static final String CYCLE = Faker.instance().university().prefix();
  private static final String DEGREE = Faker.instance().educator().campus();
  private static final String PROGRAM = Faker.instance().educator().course();
  private static final UniversityProgramInput FILLED_UNIVERSITY_PROGRAM_INPUT =
      UniversityProgramInputBuilder.aUniversityProgramInput()
          .withCycle(CYCLE)
          .withDegree(DEGREE)
          .withProgram(PROGRAM)
          .build();
  private static final String ANOTHER_CYCLE = Faker.instance().university().suffix();
  private static final String ANOTHER_DEGREE = Faker.instance().educator().university();
  private static final String ANOTHER_PROGRAM = Faker.instance().educator().secondarySchool();
  private static final UniversityProgramInput ANOTHER_FILLED_UNIVERSITY_PROGRAM_INPUT =
      UniversityProgramInputBuilder.aUniversityProgramInput()
          .withCycle(ANOTHER_CYCLE)
          .withDegree(ANOTHER_DEGREE)
          .withProgram(ANOTHER_PROGRAM)
          .build();
  private static final UniversityProgramInput UNFILLED_UNIVERSITY_PROGRAM_INPUT =
      UniversityProgramInputGenerator.createUnfilled();

  @Rule public MockitoRule mockitoRule = MockitoJUnit.rule().silent();
  @Mock private PreferentialProgramAdjustmentProvider preferentialProgramAdjustmentProvider;
  @Mock private PremiumAdjustment premiumAdjustment;
  @Mock private PremiumAdjustment anotherPremiumAdjustment;

  private PreferentialProgramFormulaPart subject;
  private UniversityProgramInput namedInsuredUniversityProgram;
  private UniversityProgramInput additionalInsuredUniversityProgram;
  private Money expectedPremiumAdjustment;

  public PreferentialProgramFormulaPartTest(
      String title,
      UniversityProgramInput namedInsuredUniversityProgram,
      UniversityProgramInput additionalInsuredUniversityProgram,
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
            UNFILLED_UNIVERSITY_PROGRAM_INPUT,
            UNFILLED_UNIVERSITY_PROGRAM_INPUT,
            Money.ZERO
          },
          {
            "with named insured eligible should compute associated adjustment",
            FILLED_UNIVERSITY_PROGRAM_INPUT,
            UNFILLED_UNIVERSITY_PROGRAM_INPUT,
            PREMIUM_ADJUSTMENT
          },
          {
            "with additional insured eligible should compute associated adjustment",
            UNFILLED_UNIVERSITY_PROGRAM_INPUT,
            FILLED_UNIVERSITY_PROGRAM_INPUT,
            PREMIUM_ADJUSTMENT
          },
          {
            "with named insured with smaller adjustment should compute smaller adjustment",
            ANOTHER_FILLED_UNIVERSITY_PROGRAM_INPUT,
            FILLED_UNIVERSITY_PROGRAM_INPUT,
            SMALLER_PREMIUM_ADJUSTMENT
          },
          {
            "with additional insured with smaller adjustment should compute smaller adjustment",
            FILLED_UNIVERSITY_PROGRAM_INPUT,
            ANOTHER_FILLED_UNIVERSITY_PROGRAM_INPUT,
            SMALLER_PREMIUM_ADJUSTMENT
          },
        });
  }

  @Before
  public void setUp() {
    when(preferentialProgramAdjustmentProvider.getAdjustment(CYCLE, DEGREE, PROGRAM))
        .thenReturn(premiumAdjustment);
    when(premiumAdjustment.apply(any(Money.class))).thenReturn(PREMIUM_ADJUSTMENT);
    when(preferentialProgramAdjustmentProvider.getAdjustment(
            ANOTHER_CYCLE, ANOTHER_DEGREE, ANOTHER_PROGRAM))
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
