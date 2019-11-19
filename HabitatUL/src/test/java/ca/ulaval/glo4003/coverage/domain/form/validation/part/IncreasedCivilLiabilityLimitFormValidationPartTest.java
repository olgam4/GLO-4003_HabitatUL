package ca.ulaval.glo4003.coverage.domain.form.validation.part;

import ca.ulaval.glo4003.coverage.domain.coverage.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.form.CoverageModificationForm;
import ca.ulaval.glo4003.coverage.domain.form.civilliability.CivilLiabilityLimit;
import ca.ulaval.glo4003.coverage.domain.form.validation.error.IncreasedCivilLiabilityLimitError;
import ca.ulaval.glo4003.helper.coverage.coverage.CoverageDetailsBuilder;
import ca.ulaval.glo4003.helper.coverage.form.CoverageModificationFormBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static ca.ulaval.glo4003.coverage.domain.form.civilliability.CivilLiabilityLimit.ONE_MILLION;
import static ca.ulaval.glo4003.coverage.domain.form.civilliability.CivilLiabilityLimit.TWO_MILLION;
import static ca.ulaval.glo4003.helper.coverage.premium.QuotePremiumInputGenerator.createCivilLiabilityLimit;
import static ca.ulaval.glo4003.helper.shared.ParameterizedTestHelper.PARAMETERIZED_TEST_TITLE;

@RunWith(Enclosed.class)
public class IncreasedCivilLiabilityLimitFormValidationPartTest {
  @RunWith(Parameterized.class)
  public static class ValidTestCase extends TestCase {
    public ValidTestCase(
        String title,
        CivilLiabilityLimit currentCivilLiabilityLimit,
        CivilLiabilityLimit requestedCivilLiabilityLimit) {
      super(currentCivilLiabilityLimit, requestedCivilLiabilityLimit);
    }

    @Parameterized.Parameters(name = PARAMETERIZED_TEST_TITLE)
    public static Collection parameters() {
      return Arrays.asList(
          new Object[][] {
            {
              "with null requested civil liability limit should not throw",
              createCivilLiabilityLimit(),
              null
            },
            {
              "with requested civil liability limit greater than current should not throw",
              ONE_MILLION,
              TWO_MILLION
            }
          });
    }

    @Test
    public void validatingCoverageModificationForm() {
      super.validatingCoverageModificationForm();
    }
  }

  @RunWith(Parameterized.class)
  public static class InvalidTestCase extends TestCase {
    public InvalidTestCase(
        String title,
        CivilLiabilityLimit currentCivilLiabilityLimit,
        CivilLiabilityLimit requestedCivilLiabilityLimit) {
      super(currentCivilLiabilityLimit, requestedCivilLiabilityLimit);
    }

    @Parameterized.Parameters(name = PARAMETERIZED_TEST_TITLE)
    public static Collection parameters() {
      return Arrays.asList(
          new Object[][] {
            {
              "with requested civil liability limit smaller than current should throw",
              TWO_MILLION,
              ONE_MILLION
            },
            {
              "with requested civil liability limit equal to current should throw",
              ONE_MILLION,
              ONE_MILLION
            }
          });
    }

    @Test(expected = IncreasedCivilLiabilityLimitError.class)
    public void validatingCoverageModificationForm() {
      super.validatingCoverageModificationForm();
    }
  }

  public abstract static class TestCase {
    private IncreasedCivilLiabilityLimitFormValidationPart subject;
    private CivilLiabilityLimit currentCivilLiabilityLimit;
    private CivilLiabilityLimit requestedCivilLiabilityLimit;

    public TestCase(
        CivilLiabilityLimit currentCivilLiabilityLimit,
        CivilLiabilityLimit requestedCivilLiabilityLimit) {
      this.currentCivilLiabilityLimit = currentCivilLiabilityLimit;
      this.requestedCivilLiabilityLimit = requestedCivilLiabilityLimit;
    }

    @Before
    public void setUp() {
      subject = new IncreasedCivilLiabilityLimitFormValidationPart();
    }

    public void validatingCoverageModificationForm() {
      CoverageDetails currentCoverageDetails =
          CoverageDetailsBuilder.aCoverageDetails()
              .withCivilLiabilityCoverageDetail(currentCivilLiabilityLimit.getAmount())
              .build();
      CoverageModificationForm coverageModificationForm =
          CoverageModificationFormBuilder.aCoverageModificationForm()
              .withCivilLiabilityLimit(requestedCivilLiabilityLimit)
              .withCurrentCoverageDetails(currentCoverageDetails)
              .build();

      subject.validate(coverageModificationForm);
    }
  }
}
