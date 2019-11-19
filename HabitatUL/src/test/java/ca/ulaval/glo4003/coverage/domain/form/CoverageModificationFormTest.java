package ca.ulaval.glo4003.coverage.domain.form;

import ca.ulaval.glo4003.coverage.domain.coverage.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.premium.PremiumDetails;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static ca.ulaval.glo4003.helper.coverage.coverage.CoverageDetailsGenerator.createCoverageDetails;
import static ca.ulaval.glo4003.helper.coverage.form.building.BuildingGenerator.createNumberOfUnits;
import static ca.ulaval.glo4003.helper.coverage.form.personalproperty.PersonalPropertyGenerator.createCoverageAmount;
import static ca.ulaval.glo4003.helper.coverage.premium.PremiumDetailsGenerator.createPremiumDetails;
import static ca.ulaval.glo4003.helper.coverage.premium.QuotePremiumInputGenerator.createCivilLiabilityLimit;
import static ca.ulaval.glo4003.helper.shared.ParameterizedTestHelper.PARAMETERIZED_TEST_TITLE;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CoverageModificationFormTest {
  private static final CoverageDetails COVERAGE_DETAILS = createCoverageDetails();
  private static final PremiumDetails PREMIUM_DETAILS = createPremiumDetails();
  private static final int NUMBER_OF_UNITS = createNumberOfUnits();

  private CoverageModificationForm subject;
  private boolean isFilled;

  public CoverageModificationFormTest(
      String title, CoverageModificationForm subject, boolean isFilled) {
    this.subject = subject;
    this.isFilled = isFilled;
  }

  @Parameterized.Parameters(name = PARAMETERIZED_TEST_TITLE)
  public static Collection parameters() {
    return Arrays.asList(
        new Object[][] {
          {
            "with unfilled form should be false",
            new CoverageModificationForm(
                null, null, NUMBER_OF_UNITS, COVERAGE_DETAILS, PREMIUM_DETAILS),
            false
          },
          {
            "with coverage amount should be true",
            new CoverageModificationForm(
                createCoverageAmount(), null, NUMBER_OF_UNITS, COVERAGE_DETAILS, PREMIUM_DETAILS),
            true
          },
          {
            "with civil liability limit should be true",
            new CoverageModificationForm(
                null,
                createCivilLiabilityLimit(),
                NUMBER_OF_UNITS,
                COVERAGE_DETAILS,
                PREMIUM_DETAILS),
            true
          },
          {
            "with coverage amount and civil liability limit should be true",
            new CoverageModificationForm(
                createCoverageAmount(),
                createCivilLiabilityLimit(),
                NUMBER_OF_UNITS,
                COVERAGE_DETAILS,
                PREMIUM_DETAILS),
            true
          }
        });
  }

  @Test
  public void checkingIfFormIsFilled() {
    assertEquals(isFilled, subject.isFilled());
  }
}
