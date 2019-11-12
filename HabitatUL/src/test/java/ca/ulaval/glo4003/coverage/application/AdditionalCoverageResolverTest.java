package ca.ulaval.glo4003.coverage.application;

import ca.ulaval.glo4003.coverage.domain.form.QuoteForm;
import ca.ulaval.glo4003.coverage.domain.form.personalproperty.Bicycle;
import ca.ulaval.glo4003.coverage.domain.form.personalproperty.PersonalProperty;
import ca.ulaval.glo4003.coverage.domain.premium.formula.bicycleendorsement.BasicBlockCoverageMaximumBicyclePriceProvider;
import ca.ulaval.glo4003.helper.coverage.form.QuoteFormBuilder;
import ca.ulaval.glo4003.helper.coverage.form.personalproperty.BicycleBuilder;
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
  private static final Amount BASIC_BLOCK_MAXIMUM_BICYCLE_PRICE = MoneyGenerator.createAmount();

  @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

  @Mock
  private BasicBlockCoverageMaximumBicyclePriceProvider
      basicBlockCoverageMaximumBicyclePriceProvider;

  private AdditionalCoverageResolver subject;
  private Bicycle bicycle;
  private boolean expected;

  public AdditionalCoverageResolverTest(String title, Bicycle bicycle, boolean expected) {
    this.bicycle = bicycle;
    this.expected = expected;
  }

  @Parameterized.Parameters(name = PARAMETERIZED_TEST_TITLE)
  public static Collection parameters() {
    return Arrays.asList(
        new Object[][] {
          {
            "without bicycle should be false",
            BicycleBuilder.aBicycle().withPrice(null).build(),
            false
          },
          {
            "with bicycle covered by basic block should be false",
            BicycleBuilder.aBicycle()
                .withPrice(
                    MoneyGenerator.createAmountSmallerThan(BASIC_BLOCK_MAXIMUM_BICYCLE_PRICE))
                .build(),
            false
          },
          {
            "with bicycle exceeding basic block coverage should be true",
            BicycleBuilder.aBicycle()
                .withPrice(
                    MoneyGenerator.createAmountGreaterThan(BASIC_BLOCK_MAXIMUM_BICYCLE_PRICE))
                .build(),
            true
          }
        });
  }

  @Before
  public void setUp() {
    when(basicBlockCoverageMaximumBicyclePriceProvider.getMaximumBicyclePrice())
        .thenReturn(BASIC_BLOCK_MAXIMUM_BICYCLE_PRICE);
    subject = new AdditionalCoverageResolver(basicBlockCoverageMaximumBicyclePriceProvider);
  }

  @Test
  public void checkingIfBicycleEndorsementShouldBeIncluded() {
    PersonalProperty personalProperty =
        PersonalPropertyBuilder.aPersonalProperty().withBicycle(bicycle).build();
    QuoteForm quoteForm =
        QuoteFormBuilder.aQuoteForm().withPersonalProperty(personalProperty).build();

    assertEquals(expected, subject.shouldIncludeBicycleEndorsement(quoteForm));
  }
}
