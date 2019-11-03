package ca.ulaval.glo4003.underwriting.domain.quote.price.formulapart;

import ca.ulaval.glo4003.helper.MoneyGenerator;
import ca.ulaval.glo4003.helper.quote.form.IdentityBuilder;
import ca.ulaval.glo4003.helper.quote.form.QuoteFormBuilder;
import ca.ulaval.glo4003.helper.quote.form.UniversityProfileBuilder;
import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;
import ca.ulaval.glo4003.underwriting.domain.quote.form.identity.Identity;
import ca.ulaval.glo4003.underwriting.domain.quote.form.identity.UniversityProfile;
import ca.ulaval.glo4003.underwriting.domain.quote.price.GraduateStudentAdjustmentProvider;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.QuotePriceAdjustment;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static ca.ulaval.glo4003.helper.quote.form.UniversityProfileGenerator.createCycle;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GraduateStudentFormulaPartTest {
  private static final Money BASE_PRICE = MoneyGenerator.createMoney();
  private static final Money PRICE_ADJUSTMENT = MoneyGenerator.createMoney();
  private static final String CYCLE = createCycle();
  private static final UniversityProfile UNIVERSITY_PROFILE =
      UniversityProfileBuilder.aUniversityProfile().withCycle(CYCLE).build();
  private static final Identity PERSONAL_INFORMATION =
      IdentityBuilder.anIdentity().withUniversityProfile(UNIVERSITY_PROFILE).build();
  private static final QuoteForm QUOTE_FORM =
      QuoteFormBuilder.aQuoteForm().withPersonalInformation(PERSONAL_INFORMATION).build();

  @Mock private GraduateStudentAdjustmentProvider graduateStudentAdjustmentProvider;
  @Mock private QuotePriceAdjustment quotePriceAdjustment;

  private GraduateStudentFormulaPart subject;

  @Before
  public void setUp() {
    when(graduateStudentAdjustmentProvider.getAdjustment(any(String.class)))
        .thenReturn(quotePriceAdjustment);
    when(quotePriceAdjustment.apply(any(Money.class))).thenReturn(PRICE_ADJUSTMENT);
    subject = new GraduateStudentFormulaPart(graduateStudentAdjustmentProvider);
  }

  @Test
  public void computingFormulaPart_shouldGetGraduateStudentAdjustment() {
    subject.compute(QUOTE_FORM, BASE_PRICE);

    verify(graduateStudentAdjustmentProvider).getAdjustment(CYCLE);
  }

  @Test
  public void computingFormulaPart_shouldApplyAdjustmentOnBasePrice() {
    subject.compute(QUOTE_FORM, BASE_PRICE);

    verify(quotePriceAdjustment).apply(BASE_PRICE);
  }

  @Test
  public void computingFormulaPart_shouldReturnAdjustmentAmount() {
    Money adjustmentAmount = subject.compute(QUOTE_FORM, BASE_PRICE);

    assertEquals(PRICE_ADJUSTMENT, adjustmentAmount);
  }
}
