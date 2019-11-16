package ca.ulaval.glo4003.coverage.domain.form.validation.part;

import ca.ulaval.glo4003.coverage.domain.form.BicycleEndorsementForm;
import ca.ulaval.glo4003.coverage.domain.form.personalproperty.Bicycle;
import ca.ulaval.glo4003.coverage.domain.form.validation.error.PositiveBicyclePriceError;
import ca.ulaval.glo4003.helper.coverage.form.BicycleEndorsementFormBuilder;
import ca.ulaval.glo4003.helper.coverage.form.personalproperty.BicycleBuilder;
import ca.ulaval.glo4003.shared.domain.money.Amount;
import org.junit.Before;
import org.junit.Test;

import static ca.ulaval.glo4003.helper.shared.MoneyGenerator.createAmountGreaterThanZero;
import static ca.ulaval.glo4003.helper.shared.MoneyGenerator.createAmountSmallerThanZero;

public class PositiveBicyclePriceBicycleEndorsementFormValidationPartTest {
  private static final Bicycle BICYCLE_WITH_POSITIVE_PRICE =
      BicycleBuilder.aBicycle().withPrice(createAmountGreaterThanZero()).build();
  private static final Bicycle BICYCLE_WITH_NEGATIVE_PRICE =
      BicycleBuilder.aBicycle().withPrice(createAmountSmallerThanZero()).build();
  private static final Bicycle BICYCLE_WITH_NULL_PRICE =
      BicycleBuilder.aBicycle().withPrice(Amount.ZERO).build();

  private PositiveBicyclePriceBicycleEndorsementFormValidationPart subject;

  @Before
  public void setUp() {
    subject = new PositiveBicyclePriceBicycleEndorsementFormValidationPart();
  }

  @Test
  public void validatingBicycleEndorsementForm_withPositiveBicyclePrice_shouldNotThrow() {
    BicycleEndorsementForm bicycleEndorsementForm =
        BicycleEndorsementFormBuilder.aBicycleEndorsementForm()
            .withBicycle(BICYCLE_WITH_POSITIVE_PRICE)
            .build();

    subject.validate(bicycleEndorsementForm);
  }

  @Test(expected = PositiveBicyclePriceError.class)
  public void validatingBicycleEndorsementForm_withNegativeBicyclePrice_shouldThrow() {
    BicycleEndorsementForm bicycleEndorsementForm =
        BicycleEndorsementFormBuilder.aBicycleEndorsementForm()
            .withBicycle(BICYCLE_WITH_NEGATIVE_PRICE)
            .build();

    subject.validate(bicycleEndorsementForm);
  }

  @Test(expected = PositiveBicyclePriceError.class)
  public void validatingBicycleEndorsementForm_withNullBicyclePrice_shouldThrow() {
    BicycleEndorsementForm bicycleEndorsementForm =
        BicycleEndorsementFormBuilder.aBicycleEndorsementForm()
            .withBicycle(BICYCLE_WITH_NULL_PRICE)
            .build();

    subject.validate(bicycleEndorsementForm);
  }
}
