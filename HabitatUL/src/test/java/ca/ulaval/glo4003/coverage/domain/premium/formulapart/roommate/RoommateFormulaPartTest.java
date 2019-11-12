package ca.ulaval.glo4003.coverage.domain.premium.formulapart.roommate;

import ca.ulaval.glo4003.coverage.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.coverage.domain.premium.formula.quote.QuotePremiumInput;
import ca.ulaval.glo4003.helper.coverage.premium.QuotePremiumInputBuilder;
import ca.ulaval.glo4003.helper.shared.MoneyGenerator;
import ca.ulaval.glo4003.shared.domain.identity.Gender;
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

import static ca.ulaval.glo4003.helper.coverage.form.identity.IdentityGenerator.createGender;
import static ca.ulaval.glo4003.helper.shared.ParameterizedTestHelper.PARAMETERIZED_TEST_TITLE;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(Parameterized.class)
public class RoommateFormulaPartTest {
  private static final Money BASE_PREMIUM = MoneyGenerator.createMoney();
  private static final Money PREMIUM_ADJUSTMENT = MoneyGenerator.createMoney();
  private static final Gender GENDER = createGender();
  private static final Gender ANOTHER_GENDER = createGender();

  @Rule public MockitoRule mockitoRule = MockitoJUnit.rule().silent();
  @Mock private RoommateAdjustmentProvider roommateAdjustmentProvider;
  @Mock private PremiumAdjustment premiumAdjustment;

  private RoommateFormulaPart subject;
  private Gender additionalInsuredGender;
  private Money expectedPremiumAdjustment;

  public RoommateFormulaPartTest(
      String title, Gender roommateGender, Money expectedPremiumAdjustment) {
    this.additionalInsuredGender = roommateGender;
    this.expectedPremiumAdjustment = expectedPremiumAdjustment;
  }

  @Parameterized.Parameters(name = PARAMETERIZED_TEST_TITLE)
  public static Collection parameters() {
    return Arrays.asList(
        new Object[][] {
          {"without roommate should compute null adjustment", null, Money.ZERO},
          {
            "with roommate should compute associated adjustment", ANOTHER_GENDER, PREMIUM_ADJUSTMENT
          },
        });
  }

  @Before
  public void setUp() {
    when(roommateAdjustmentProvider.getAdjustment(any(Gender.class), any(Gender.class)))
        .thenReturn(premiumAdjustment);
    when(premiumAdjustment.apply(any(Money.class))).thenReturn(PREMIUM_ADJUSTMENT);
    subject = new RoommateFormulaPart(roommateAdjustmentProvider);
  }

  @Test
  public void computingFormulaPart() {
    QuotePremiumInput quotePremiumInput =
        QuotePremiumInputBuilder.aQuotePremiumInput()
            .withNamedInsuredGender(GENDER)
            .withAdditionalInsuredGender(additionalInsuredGender)
            .build();

    Money premiumAdjustment = subject.compute(quotePremiumInput, BASE_PREMIUM);

    assertEquals(expectedPremiumAdjustment, premiumAdjustment);
  }
}
