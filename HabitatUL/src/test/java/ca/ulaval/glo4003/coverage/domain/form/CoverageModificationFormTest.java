package ca.ulaval.glo4003.coverage.domain.form;

import ca.ulaval.glo4003.coverage.domain.coverage.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.premium.PremiumDetails;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static ca.ulaval.glo4003.helper.coverage.coverage.CoverageDetailsGenerator.createCoverageDetails;
import static ca.ulaval.glo4003.helper.coverage.form.personalproperty.PersonalPropertyGenerator.createCoverageAmount;
import static ca.ulaval.glo4003.helper.coverage.premium.PremiumDetailsGenerator.createPremiumDetails;
import static ca.ulaval.glo4003.helper.coverage.premium.QuotePremiumInputGenerator.createCivilLiabilityLimit;
import static ca.ulaval.glo4003.helper.shared.ParameterizedTestHelper.PARAMETERIZED_TEST_TITLE;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CoverageModificationFormTest {
  private static final CoverageDetails COVERAGE_DETAILS = createCoverageDetails();
  private static final PremiumDetails PREMIUM_DETAILS = createPremiumDetails();

  @Parameterized.Parameters(name = PARAMETERIZED_TEST_TITLE)
  public static Collection parameters() {
    return Arrays.asList(
        new Object[][] {
          {
            "with unfilled form should be false",
            new CoverageModificationForm(null, null, COVERAGE_DETAILS, PREMIUM_DETAILS),
            false
          },
          {
            "with personal property coverage amount should be true",
            new CoverageModificationForm(
                createCoverageAmount(), null, COVERAGE_DETAILS, PREMIUM_DETAILS),
            true
          },
          {
            "with civil liability limit should be true",
            new CoverageModificationForm(
                null, createCivilLiabilityLimit(), COVERAGE_DETAILS, PREMIUM_DETAILS),
            true
          },
          {
            "with personal property coverage amount and civil liability limit should be true",
            new CoverageModificationForm(
                createCoverageAmount(),
                createCivilLiabilityLimit(),
                COVERAGE_DETAILS,
                PREMIUM_DETAILS),
            true
          }
        });
  }

  private CoverageModificationForm form;
  private boolean isFilled;

  public CoverageModificationFormTest(
      String title, CoverageModificationForm form, boolean isFilled) {
    this.form = form;
    this.isFilled = isFilled;
  }

  @Test
  public void checkingIfFormIsFilled() {
    assertEquals(isFilled, form.isFilled());
  }
}
