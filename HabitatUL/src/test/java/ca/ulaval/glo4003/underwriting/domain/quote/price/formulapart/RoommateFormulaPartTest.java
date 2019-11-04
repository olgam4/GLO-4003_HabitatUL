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
import static ca.ulaval.glo4003.underwriting.domain.quote.form.identity.Identity.UNFILLED_IDENTITY;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(Parameterized.class)
public class RoommateFormulaPartTest {
  private static final Money BASE_PRICE = MoneyGenerator.createMoney();
  private static final Money PRICE_ADJUSTMENT = MoneyGenerator.createMoney();
  private static final Identity IDENTITY = IdentityGenerator.createIdentity();
  private static final Identity ANOTHER_IDENTITY = IdentityGenerator.createIdentity();

  @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();
  @Mock private RoommateAdjustmentProvider roommateAdjustmentProvider;
  @Mock private QuotePriceAdjustment quotePriceAdjustment;

  private RoommateFormulaPart subject;
  private Identity additionalInsuredIdentity;
  private Money expectedAdjustmentAmount;

  public RoommateFormulaPartTest(
      String title, Identity additionalInsuredIdentity, Money expectedAdjustmentAmount) {
    this.additionalInsuredIdentity = additionalInsuredIdentity;
    this.expectedAdjustmentAmount = expectedAdjustmentAmount;
  }

  @Parameterized.Parameters(name = PARAMETERIZED_TEST_TITLE)
  public static Collection parameters() {
    return Arrays.asList(
        new Object[][] {
          {
            "without additional insured should compute no adjustment", UNFILLED_IDENTITY, Money.ZERO
          },
          {
            "with additional insured should compute associated adjustment",
            ANOTHER_IDENTITY,
            PRICE_ADJUSTMENT
          },
        });
  }

  @Before
  public void setUp() {
    when(roommateAdjustmentProvider.getAdjustment(any(Gender.class), any(Gender.class)))
        .thenReturn(quotePriceAdjustment);
    when(quotePriceAdjustment.apply(any(Money.class))).thenReturn(PRICE_ADJUSTMENT);
    subject = new RoommateFormulaPart(roommateAdjustmentProvider);
  }

  @Test
  public void computingFormulaPart() {
    QuoteForm quoteForm =
        QuoteFormBuilder.aQuoteForm()
            .withPersonalInformation(IDENTITY)
            .withAdditionalInsured(additionalInsuredIdentity)
            .build();

    Money adjustmentAmount = subject.compute(quoteForm, BASE_PRICE);

    assertEquals(expectedAdjustmentAmount, adjustmentAmount);
  }
}
