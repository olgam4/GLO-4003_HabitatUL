package ca.ulaval.glo4003.underwriting.domain.quote.price.formulapart;

import ca.ulaval.glo4003.helper.MoneyGenerator;
import ca.ulaval.glo4003.helper.quote.form.IdentityGenerator;
import ca.ulaval.glo4003.helper.quote.form.QuoteFormBuilder;
import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;
import ca.ulaval.glo4003.underwriting.domain.quote.form.identity.Gender;
import ca.ulaval.glo4003.underwriting.domain.quote.form.identity.Identity;
import ca.ulaval.glo4003.underwriting.domain.quote.price.RoommateAdjustmentProvider;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.QuotePriceAdjustment;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static ca.ulaval.glo4003.underwriting.domain.quote.form.identity.Identity.UNFILLED_IDENTITY;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RoommateFormulaPartTest {
  private static final Money BASE_PRICE = MoneyGenerator.create();
  private static final Money PRICE_ADJUSTMENT = MoneyGenerator.create();
  private static final Identity IDENTITY = IdentityGenerator.createIdentity();
  private static final Identity ANOTHER_IDENTITY = IdentityGenerator.createIdentity();

  @Mock private RoommateAdjustmentProvider roommateAdjustmentProvider;
  @Mock private QuotePriceAdjustment quotePriceAdjustment;

  private RoommateFormulaPart subject;

  @Before
  public void setUp() {
    when(roommateAdjustmentProvider.getAdjustment(any(Gender.class), any(Gender.class)))
        .thenReturn(quotePriceAdjustment);
    when(quotePriceAdjustment.apply(any(Money.class))).thenReturn(PRICE_ADJUSTMENT);
    subject = new RoommateFormulaPart(roommateAdjustmentProvider);
  }

  @Test
  public void computingFormulaPart_shouldComputeAdjustmentAmount() {
    validateScenario(UNFILLED_IDENTITY, Money.ZERO);
    validateScenario(ANOTHER_IDENTITY, PRICE_ADJUSTMENT);
  }

  private void validateScenario(
      Identity additionalInsuredIdentity, Money expectedAdjustmentAmount) {
    QuoteForm quoteForm =
        QuoteFormBuilder.aQuoteForm()
            .withPersonalInformation(IDENTITY)
            .withAdditionalInsured(additionalInsuredIdentity)
            .build();

    Money adjustmentAmount = subject.compute(quoteForm, BASE_PRICE);

    assertEquals(expectedAdjustmentAmount, adjustmentAmount);
  }
}
