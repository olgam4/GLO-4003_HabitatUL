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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static ca.ulaval.glo4003.underwriting.domain.quote.form.identity.UniversityProfile.UNFILLED_UNIVERSITY_PROFILE;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PreferentialProgramFormulaPartTest {
  private static final Money BASE_PRICE = MoneyGenerator.create();
  private static final Money PRICE_ADJUSTMENT = MoneyBuilder.aMoney().withAmount(10f).build();
  private static final Money SMALLER_PRICE_ADJUSTMENT =
      MoneyBuilder.aMoney().withAmount(5f).build();
  private static final String PROGRAM = Faker.instance().educator().course();
  private static final UniversityProfile FILLED_UNIVERSITY_PROFILE =
      UniversityProfileBuilder.aUniversity().withProgram(PROGRAM).build();
  private static final Identity IDENTITY =
      IdentityBuilder.anIdentity().withUniversityProfile(FILLED_UNIVERSITY_PROFILE).build();
  private static final String ANOTHER_PROGRAM = Faker.instance().educator().campus();
  private static final UniversityProfile ANOTHER_FILLED_UNIVERSITY_PROFILE =
      UniversityProfileBuilder.aUniversity().withProgram(ANOTHER_PROGRAM).build();
  private static final Identity ANOTHER_IDENTITY =
      IdentityBuilder.anIdentity().withUniversityProfile(ANOTHER_FILLED_UNIVERSITY_PROFILE).build();
  private static final Identity IDENTITY_WITH_UNFILLED_UNIVERSITY_PROFILE =
      IdentityBuilder.anIdentity().withUniversityProfile(UNFILLED_UNIVERSITY_PROFILE).build();

  @Mock private PreferentialProgramAdjustmentProvider preferentialProgramAdjustmentProvider;
  @Mock private QuotePriceAdjustment quotePriceAdjustment;
  @Mock private QuotePriceAdjustment anotherQuotePriceAdjustment;

  private PreferentialProgramFormulaPart subject;

  @Before
  public void setUp() {
    when(preferentialProgramAdjustmentProvider.getAdjustment(PROGRAM))
        .thenReturn(quotePriceAdjustment);
    when(quotePriceAdjustment.apply(any(Money.class))).thenReturn(PRICE_ADJUSTMENT);
    when(preferentialProgramAdjustmentProvider.getAdjustment(ANOTHER_PROGRAM))
        .thenReturn(anotherQuotePriceAdjustment);
    when(anotherQuotePriceAdjustment.apply(any(Money.class))).thenReturn(SMALLER_PRICE_ADJUSTMENT);
    subject = new PreferentialProgramFormulaPart(preferentialProgramAdjustmentProvider);
  }

  @Test
  public void computingFormulaPart_shouldComputeAdjustmentAmount() {
    validateScenario(
        IDENTITY_WITH_UNFILLED_UNIVERSITY_PROFILE,
        IDENTITY_WITH_UNFILLED_UNIVERSITY_PROFILE,
        Money.ZERO);
    validateScenario(IDENTITY, IDENTITY_WITH_UNFILLED_UNIVERSITY_PROFILE, PRICE_ADJUSTMENT);
    validateScenario(IDENTITY_WITH_UNFILLED_UNIVERSITY_PROFILE, IDENTITY, PRICE_ADJUSTMENT);
    validateScenario(IDENTITY, ANOTHER_IDENTITY, SMALLER_PRICE_ADJUSTMENT);
    validateScenario(ANOTHER_IDENTITY, IDENTITY, SMALLER_PRICE_ADJUSTMENT);
  }

  private void validateScenario(
      Identity namedInsuredIdentity,
      Identity additionalInsuredIdentity,
      Money expectedAdjustmentAmount) {
    QuoteForm quoteForm =
        QuoteFormBuilder.aQuoteForm()
            .withPersonalInformation(namedInsuredIdentity)
            .withAdditionalInsured(additionalInsuredIdentity)
            .build();

    Money adjustmentAmount = subject.compute(quoteForm, BASE_PRICE);

    assertEquals(expectedAdjustmentAmount, adjustmentAmount);
  }
}
