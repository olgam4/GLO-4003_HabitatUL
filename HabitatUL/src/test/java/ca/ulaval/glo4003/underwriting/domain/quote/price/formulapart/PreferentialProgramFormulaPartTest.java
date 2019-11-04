package ca.ulaval.glo4003.underwriting.domain.quote.price.formulapart;

import ca.ulaval.glo4003.helper.MoneyBuilder;
import ca.ulaval.glo4003.helper.MoneyGenerator;
import ca.ulaval.glo4003.helper.quote.form.IdentityBuilder;
import ca.ulaval.glo4003.helper.quote.form.QuoteFormBuilder;
import ca.ulaval.glo4003.helper.quote.form.UniversityProfileBuilder;
import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;
import ca.ulaval.glo4003.underwriting.domain.quote.form.identity.Identity;
import ca.ulaval.glo4003.underwriting.domain.quote.form.identity.UniversityProfile;
import ca.ulaval.glo4003.underwriting.domain.quote.price.PreferentialProgramAdjustmentProvider;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.QuotePriceAdjustment;
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
import static ca.ulaval.glo4003.underwriting.domain.quote.form.identity.UniversityProfile.UNFILLED_UNIVERSITY_PROFILE;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(Parameterized.class)
public class PreferentialProgramFormulaPartTest {
  private static final Money BASE_PRICE = MoneyGenerator.createMoney();
  private static final Money PRICE_ADJUSTMENT = MoneyBuilder.aMoney().withAmount(10f).build();
  private static final Money SMALLER_PRICE_ADJUSTMENT =
      MoneyBuilder.aMoney().withAmount(5f).build();
  private static final String CYCLE = Faker.instance().university().prefix();
  private static final String DEGREE = Faker.instance().educator().campus();
  private static final String PROGRAM = Faker.instance().educator().course();
  private static final UniversityProfile FILLED_UNIVERSITY_PROFILE =
      UniversityProfileBuilder.aUniversityProfile()
          .withCycle(CYCLE)
          .withDegree(DEGREE)
          .withProgram(PROGRAM)
          .build();
  private static final Identity IDENTITY =
      IdentityBuilder.anIdentity().withUniversityProfile(FILLED_UNIVERSITY_PROFILE).build();
  private static final String ANOTHER_CYCLE = Faker.instance().university().suffix();
  private static final String ANOTHER_DEGREE = Faker.instance().educator().university();
  private static final String ANOTHER_PROGRAM = Faker.instance().educator().secondarySchool();
  private static final UniversityProfile ANOTHER_FILLED_UNIVERSITY_PROFILE =
      UniversityProfileBuilder.aUniversityProfile()
          .withCycle(ANOTHER_CYCLE)
          .withDegree(ANOTHER_DEGREE)
          .withProgram(ANOTHER_PROGRAM)
          .build();
  private static final Identity ANOTHER_IDENTITY =
      IdentityBuilder.anIdentity().withUniversityProfile(ANOTHER_FILLED_UNIVERSITY_PROFILE).build();
  private static final Identity IDENTITY_WITH_UNFILLED_UNIVERSITY_PROFILE =
      IdentityBuilder.anIdentity().withUniversityProfile(UNFILLED_UNIVERSITY_PROFILE).build();

  @Rule public MockitoRule mockitoRule = MockitoJUnit.rule().silent();
  @Mock private PreferentialProgramAdjustmentProvider preferentialProgramAdjustmentProvider;
  @Mock private QuotePriceAdjustment quotePriceAdjustment;
  @Mock private QuotePriceAdjustment anotherQuotePriceAdjustment;

  private PreferentialProgramFormulaPart subject;
  private Identity namedInsuredIdentity;
  private Identity additionalInsuredIdentity;
  private Money expectedAdjustmentAmount;

  public PreferentialProgramFormulaPartTest(
      String title,
      Identity namedInsuredIdentity,
      Identity additionalInsuredIdentity,
      Money expectedAdjustmentAmount) {
    this.namedInsuredIdentity = namedInsuredIdentity;
    this.additionalInsuredIdentity = additionalInsuredIdentity;
    this.expectedAdjustmentAmount = expectedAdjustmentAmount;
  }

  @Parameterized.Parameters(name = PARAMETERIZED_TEST_TITLE)
  public static Collection parameters() {
    return Arrays.asList(
        new Object[][] {
          {
            "with named and additional insureds not eligible should compute null adjustment",
            IDENTITY_WITH_UNFILLED_UNIVERSITY_PROFILE,
            IDENTITY_WITH_UNFILLED_UNIVERSITY_PROFILE,
            Money.ZERO
          },
          {
            "with named insured eligible should compute associated adjustment",
            IDENTITY,
            IDENTITY_WITH_UNFILLED_UNIVERSITY_PROFILE,
            PRICE_ADJUSTMENT
          },
          {
            "with additional insured eligible should compute associated adjustment",
            IDENTITY_WITH_UNFILLED_UNIVERSITY_PROFILE,
            IDENTITY,
            PRICE_ADJUSTMENT
          },
          {
            "with named insured with smaller adjustment should compute smaller adjustment",
            ANOTHER_IDENTITY,
            IDENTITY,
            SMALLER_PRICE_ADJUSTMENT
          },
          {
            "with additional insured with smaller adjustment should compute smaller adjustment",
            IDENTITY,
            ANOTHER_IDENTITY,
            SMALLER_PRICE_ADJUSTMENT
          },
        });
  }

  @Before
  public void setUp() {
    when(preferentialProgramAdjustmentProvider.getAdjustment(CYCLE, DEGREE, PROGRAM))
        .thenReturn(quotePriceAdjustment);
    when(quotePriceAdjustment.apply(any(Money.class))).thenReturn(PRICE_ADJUSTMENT);
    when(preferentialProgramAdjustmentProvider.getAdjustment(
            ANOTHER_CYCLE, ANOTHER_DEGREE, ANOTHER_PROGRAM))
        .thenReturn(anotherQuotePriceAdjustment);
    when(anotherQuotePriceAdjustment.apply(any(Money.class))).thenReturn(SMALLER_PRICE_ADJUSTMENT);
    subject = new PreferentialProgramFormulaPart(preferentialProgramAdjustmentProvider);
  }

  @Test
  public void computingFormulaPart() {
    QuoteForm quoteForm =
        QuoteFormBuilder.aQuoteForm()
            .withPersonalInformation(namedInsuredIdentity)
            .withAdditionalInsured(additionalInsuredIdentity)
            .build();

    Money adjustmentAmount = subject.compute(quoteForm, BASE_PRICE);

    assertEquals(expectedAdjustmentAmount, adjustmentAmount);
  }
}
