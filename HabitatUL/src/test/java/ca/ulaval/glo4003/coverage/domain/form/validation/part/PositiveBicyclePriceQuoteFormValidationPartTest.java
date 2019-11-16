package ca.ulaval.glo4003.coverage.domain.form.validation.part;

import ca.ulaval.glo4003.coverage.domain.form.QuoteForm;
import ca.ulaval.glo4003.coverage.domain.form.personalproperty.Bicycle;
import ca.ulaval.glo4003.coverage.domain.form.personalproperty.PersonalProperty;
import ca.ulaval.glo4003.coverage.domain.form.validation.error.PositiveBicyclePriceError;
import ca.ulaval.glo4003.helper.coverage.form.QuoteFormBuilder;
import ca.ulaval.glo4003.helper.coverage.form.personalproperty.BicycleBuilder;
import ca.ulaval.glo4003.helper.coverage.form.personalproperty.PersonalPropertyBuilder;
import ca.ulaval.glo4003.shared.domain.money.Amount;
import org.junit.Before;
import org.junit.Test;

import static ca.ulaval.glo4003.coverage.domain.form.personalproperty.Bicycle.UNFILLED_BICYCLE;
import static ca.ulaval.glo4003.helper.shared.MoneyGenerator.createAmountGreaterThanZero;
import static ca.ulaval.glo4003.helper.shared.MoneyGenerator.createAmountSmallerThanZero;

public class PositiveBicyclePriceQuoteFormValidationPartTest {
  private static final Bicycle BICYCLE_WITH_POSITIVE_PRICE =
      BicycleBuilder.aBicycle().withPrice(createAmountGreaterThanZero()).build();
  private static final Bicycle BICYCLE_WITH_NEGATIVE_PRICE =
      BicycleBuilder.aBicycle().withPrice(createAmountSmallerThanZero()).build();
  private static final Bicycle BICYCLE_WITH_NULL_PRICE =
      BicycleBuilder.aBicycle().withPrice(Amount.ZERO).build();

  private PositiveBicyclePriceQuoteFormValidationPart subject;

  @Before
  public void setUp() {
    subject = new PositiveBicyclePriceQuoteFormValidationPart();
  }

  @Test
  public void validatingQuoteForm_withUnfilledBicycle_shouldNotThrow() {
    PersonalProperty personalProperty =
        PersonalPropertyBuilder.aPersonalProperty().withBicycle(UNFILLED_BICYCLE).build();
    QuoteForm quoteForm =
        QuoteFormBuilder.aQuoteForm().withPersonalProperty(personalProperty).build();

    subject.validate(quoteForm);
  }

  @Test
  public void validatingQuoteForm_withPositiveBicyclePrice_shouldNotThrow() {
    PersonalProperty personalProperty =
        PersonalPropertyBuilder.aPersonalProperty()
            .withBicycle(BICYCLE_WITH_POSITIVE_PRICE)
            .build();
    QuoteForm quoteForm =
        QuoteFormBuilder.aQuoteForm().withPersonalProperty(personalProperty).build();

    subject.validate(quoteForm);
  }

  @Test(expected = PositiveBicyclePriceError.class)
  public void validatingQuoteForm_withNegativeBicyclePrice_shouldThrow() {
    PersonalProperty personalProperty =
        PersonalPropertyBuilder.aPersonalProperty()
            .withBicycle(BICYCLE_WITH_NEGATIVE_PRICE)
            .build();
    QuoteForm quoteForm =
        QuoteFormBuilder.aQuoteForm().withPersonalProperty(personalProperty).build();

    subject.validate(quoteForm);
  }

  @Test(expected = PositiveBicyclePriceError.class)
  public void validatingQuoteForm_withNullBicyclePrice_shouldThrow() {
    PersonalProperty personalProperty =
        PersonalPropertyBuilder.aPersonalProperty().withBicycle(BICYCLE_WITH_NULL_PRICE).build();
    QuoteForm quoteForm =
        QuoteFormBuilder.aQuoteForm().withPersonalProperty(personalProperty).build();

    subject.validate(quoteForm);
  }
}
