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

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PreferentialProgramFormulaPartTest {
  private static final String NAMED_INSURED_PROGRAM = Faker.instance().educator().course();
  private static final UniversityProfile NAMED_INSURED_UNIVERSITY_PROFILE =
      UniversityProfileBuilder.aUniversity().withProgram(NAMED_INSURED_PROGRAM).build();
  private static final Identity NAMED_INSURED_IDENTITY =
      IdentityBuilder.anIdentity().withUniversityProfile(NAMED_INSURED_UNIVERSITY_PROFILE).build();
  private static final String ADDITIONAL_INSURED_PROGRAM = Faker.instance().educator().campus();
  private static final UniversityProfile ADDITIONAL_INSURED_UNIVERSITY_PROFILE =
      UniversityProfileBuilder.aUniversity().withProgram(ADDITIONAL_INSURED_PROGRAM).build();
  private static final Identity ADDITIONAL_INSURED_IDENTITY =
      IdentityBuilder.anIdentity()
          .withUniversityProfile(ADDITIONAL_INSURED_UNIVERSITY_PROFILE)
          .build();
  private static final Identity IDENTITY_WITHOUT_UNIVERSITY_PROFILE =
      IdentityBuilder.anIdentity().withoutUniversityProfile().build();
  private static final QuoteForm QUOTE_FORM_WITHOUT_ADDITIONAL_INSURED =
      QuoteFormBuilder.aQuoteForm()
          .withPersonalInformation(NAMED_INSURED_IDENTITY)
          .withoutAdditionalInsured()
          .build();
  private static final QuoteForm QUOTE_FORM_WITH_ADDITIONAL_INSURED_WITHOUT_UNIVERSITY_PROFILE =
      QuoteFormBuilder.aQuoteForm()
          .withPersonalInformation(NAMED_INSURED_IDENTITY)
          .withAdditionalInsured(IDENTITY_WITHOUT_UNIVERSITY_PROFILE)
          .build();
  private static final QuoteForm QUOTE_FORM_WITH_ADDITIONAL_INSURED_WITH_UNIVERSITY_PROFILE =
      QuoteFormBuilder.aQuoteForm()
          .withPersonalInformation(NAMED_INSURED_IDENTITY)
          .withAdditionalInsured(ADDITIONAL_INSURED_IDENTITY)
          .build();
  private static final Money BASE_PRICE = MoneyGenerator.create();
  private static final Money PRICE_ADJUSTMENT = MoneyBuilder.aMoney().withAmount(10f).build();
  private static final Money SMALLER_PRICE_ADJUSTMENT =
      MoneyBuilder.aMoney().withAmount(5f).build();

  @Mock private PreferentialProgramAdjustmentProvider preferentialProgramAdjustmentProvider;
  @Mock private QuotePriceAdjustment quotePriceAdjustment;
  @Mock private QuotePriceAdjustment anotherQuotePriceAdjustment;

  private PreferentialProgramFormulaPart subject;

  @Before
  public void setUp() {

    when(preferentialProgramAdjustmentProvider.getAdjustment(NAMED_INSURED_PROGRAM))
        .thenReturn(quotePriceAdjustment);
    when(quotePriceAdjustment.apply(any(Money.class))).thenReturn(PRICE_ADJUSTMENT);
    when(preferentialProgramAdjustmentProvider.getAdjustment(ADDITIONAL_INSURED_PROGRAM))
        .thenReturn(anotherQuotePriceAdjustment);
    when(anotherQuotePriceAdjustment.apply(any(Money.class))).thenReturn(SMALLER_PRICE_ADJUSTMENT);
    subject = new PreferentialProgramFormulaPart(preferentialProgramAdjustmentProvider);
  }

  @Test
  public void
      computingFormulaPart_withoutAdditionalInsured_shouldGetPreferentialProgramAdjustments() {
    subject.compute(QUOTE_FORM_WITHOUT_ADDITIONAL_INSURED, BASE_PRICE);

    verify(preferentialProgramAdjustmentProvider).getAdjustment(NAMED_INSURED_PROGRAM);
    verify(preferentialProgramAdjustmentProvider, never())
        .getAdjustment(ADDITIONAL_INSURED_PROGRAM);
  }

  @Test
  public void computingFormulaPart_withoutAdditionalInsured_shouldComputeAdjustmentAmounts() {
    subject.compute(QUOTE_FORM_WITHOUT_ADDITIONAL_INSURED, BASE_PRICE);

    verify(quotePriceAdjustment).apply(BASE_PRICE);
    verify(anotherQuotePriceAdjustment, never()).apply(any());
  }

  @Test
  public void computingFormulaPart_withoutAdditionalInsured_shouldReturnAdjustmentAmount() {
    Money adjustmentAmount = subject.compute(QUOTE_FORM_WITHOUT_ADDITIONAL_INSURED, BASE_PRICE);

    assertEquals(PRICE_ADJUSTMENT, adjustmentAmount);
  }

  @Test
  public void
      computingFormulaPart_withAdditionalInsuredWithoutUniversityProfile_shouldGetPreferentialProgramAdjustments() {
    subject.compute(QUOTE_FORM_WITH_ADDITIONAL_INSURED_WITHOUT_UNIVERSITY_PROFILE, BASE_PRICE);

    verify(preferentialProgramAdjustmentProvider).getAdjustment(NAMED_INSURED_PROGRAM);
    verify(preferentialProgramAdjustmentProvider, never())
        .getAdjustment(ADDITIONAL_INSURED_PROGRAM);
  }

  @Test
  public void
      computingFormulaPart_withAdditionalInsuredWithoutUniversityProfile_shouldComputeAdjustmentAmounts() {
    subject.compute(QUOTE_FORM_WITHOUT_ADDITIONAL_INSURED, BASE_PRICE);

    verify(quotePriceAdjustment).apply(BASE_PRICE);
    verify(anotherQuotePriceAdjustment, never()).apply(BASE_PRICE);
  }

  @Test
  public void
      computingFormulaPart_withAdditionalInsuredWithoutUniversityProfile_shouldReturnMinimumAdjustmentAmount() {
    Money adjustmentAmount =
        subject.compute(QUOTE_FORM_WITH_ADDITIONAL_INSURED_WITHOUT_UNIVERSITY_PROFILE, BASE_PRICE);

    assertEquals(PRICE_ADJUSTMENT, adjustmentAmount);
  }

  @Test
  public void
      computingFormulaPart_withAdditionalInsuredWithUniversityProfile_shouldGetPreferentialProgramAdjustments() {
    subject.compute(QUOTE_FORM_WITH_ADDITIONAL_INSURED_WITH_UNIVERSITY_PROFILE, BASE_PRICE);

    verify(preferentialProgramAdjustmentProvider).getAdjustment(NAMED_INSURED_PROGRAM);
    verify(preferentialProgramAdjustmentProvider).getAdjustment(ADDITIONAL_INSURED_PROGRAM);
  }

  @Test
  public void
      computingFormulaPart_withAdditionalInsuredWithUniversityProfile_shouldComputeAdjustmentAmounts() {
    subject.compute(QUOTE_FORM_WITH_ADDITIONAL_INSURED_WITH_UNIVERSITY_PROFILE, BASE_PRICE);

    verify(quotePriceAdjustment).apply(BASE_PRICE);
    verify(anotherQuotePriceAdjustment).apply(BASE_PRICE);
  }

  @Test
  public void
      computingFormulaPart_withAdditionalInsuredWithUniversityProfile_shouldReturnMinimumAdjustmentAmount() {
    Money adjustmentAmount =
        subject.compute(QUOTE_FORM_WITH_ADDITIONAL_INSURED_WITH_UNIVERSITY_PROFILE, BASE_PRICE);

    assertEquals(SMALLER_PRICE_ADJUSTMENT, adjustmentAmount);
  }
}
