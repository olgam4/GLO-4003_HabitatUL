package ca.ulaval.glo4003.coverage.application.coverage;

import ca.ulaval.glo4003.coverage.domain.form.QuoteForm;
import ca.ulaval.glo4003.coverage.domain.form.personalproperty.Bike;
import ca.ulaval.glo4003.coverage.domain.form.personalproperty.PersonalProperty;
import ca.ulaval.glo4003.coverage.domain.premium.formula.bike.BasicBlockCoverageMaximumBikePriceProvider;
import ca.ulaval.glo4003.helper.coverage.form.QuoteFormBuilder;
import ca.ulaval.glo4003.helper.coverage.form.personalproperty.BikeBuilder;
import ca.ulaval.glo4003.helper.coverage.form.personalproperty.PersonalPropertyBuilder;
import ca.ulaval.glo4003.helper.shared.MoneyGenerator;
import ca.ulaval.glo4003.shared.domain.money.Amount;
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

import static ca.ulaval.glo4003.helper.shared.ParameterizedTestHelper.PARAMETERIZED_TEST_TITLE;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(Parameterized.class)
public class AdditionalCoverageResolverTest {
  private static final Amount BASIC_BLOCK_MAXIMUM_BIKE_PRICE = MoneyGenerator.createAmount();

  @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

  @Mock
  private BasicBlockCoverageMaximumBikePriceProvider basicBlockCoverageMaximumBikePriceProvider;

  private AdditionalCoverageResolver subject;
  private Bike bike;
  private boolean expected;

  public AdditionalCoverageResolverTest(String title, Bike bike, boolean expected) {
    this.bike = bike;
    this.expected = expected;
  }

  @Parameterized.Parameters(name = PARAMETERIZED_TEST_TITLE)
  public static Collection parameters() {
    return Arrays.asList(
        new Object[][] {
          {"without bike should be false", BikeBuilder.aBike().withPrice(null).build(), false},
          {
            "with bike covered by basic block should be false",
            BikeBuilder.aBike()
                .withPrice(MoneyGenerator.createAmountSmallerThan(BASIC_BLOCK_MAXIMUM_BIKE_PRICE))
                .build(),
            false
          },
          {
            "with bike exceeding basic block coverage should be true",
            BikeBuilder.aBike()
                .withPrice(MoneyGenerator.createAmountGreaterThan(BASIC_BLOCK_MAXIMUM_BIKE_PRICE))
                .build(),
            true
          }
        });
  }

  @Before
  public void setUp() {
    when(basicBlockCoverageMaximumBikePriceProvider.getMaximumBikePrice())
        .thenReturn(BASIC_BLOCK_MAXIMUM_BIKE_PRICE);
    subject = new AdditionalCoverageResolver(basicBlockCoverageMaximumBikePriceProvider);
  }

  @Test
  public void checkingIfBikeEndorsementShouldBeIncluded() {
    PersonalProperty personalProperty =
        PersonalPropertyBuilder.aPersonalProperty().withBike(bike).build();
    QuoteForm quoteForm =
        QuoteFormBuilder.aQuoteForm().withPersonalProperty(personalProperty).build();

    assertEquals(expected, subject.shouldIncludeBikeEndorsement(quoteForm));
  }
}
