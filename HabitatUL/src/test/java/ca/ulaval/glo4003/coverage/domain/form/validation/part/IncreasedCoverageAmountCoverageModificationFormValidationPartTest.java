package ca.ulaval.glo4003.coverage.domain.form.validation.part;

import ca.ulaval.glo4003.coverage.domain.coverage.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.form.CoverageModificationForm;
import ca.ulaval.glo4003.coverage.domain.form.validation.error.IncreasedCoverageAmountError;
import ca.ulaval.glo4003.helper.coverage.coverage.CoverageDetailsBuilder;
import ca.ulaval.glo4003.helper.coverage.form.CoverageModificationFormBuilder;
import ca.ulaval.glo4003.shared.domain.money.Amount;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static ca.ulaval.glo4003.helper.coverage.form.personalproperty.PersonalPropertyGenerator.createCoverageAmount;
import static ca.ulaval.glo4003.helper.shared.MoneyGenerator.createAmountGreaterThan;
import static ca.ulaval.glo4003.helper.shared.MoneyGenerator.createAmountSmallerThan;
import static ca.ulaval.glo4003.helper.shared.ParameterizedTestHelper.PARAMETERIZED_TEST_TITLE;

@RunWith(Enclosed.class)
public class IncreasedCoverageAmountCoverageModificationFormValidationPartTest {
  private static final Amount CURRENT_COVERAGE_AMOUNT = createCoverageAmount();

  @RunWith(Parameterized.class)
  public static class ValidTestCase extends TestCase {
    public ValidTestCase(
        String title, Amount currentCoverageAmount, Amount requestedCoverageAmount) {
      super(currentCoverageAmount, requestedCoverageAmount);
    }

    @Parameterized.Parameters(name = PARAMETERIZED_TEST_TITLE)
    public static Collection parameters() {
      return Arrays.asList(
          new Object[][] {
            {"with null requested coverage amount should not throw", CURRENT_COVERAGE_AMOUNT, null},
            {
              "with requested coverage amount greater than current should not throw",
              CURRENT_COVERAGE_AMOUNT,
              createAmountGreaterThan(CURRENT_COVERAGE_AMOUNT)
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
        String title, Amount currentCoverageAmount, Amount requestedCoverageAmount) {
      super(currentCoverageAmount, requestedCoverageAmount);
    }

    @Parameterized.Parameters(name = PARAMETERIZED_TEST_TITLE)
    public static Collection parameters() {
      return Arrays.asList(
          new Object[][] {
            {
              "with requested coverage amount smaller than current should throw",
              CURRENT_COVERAGE_AMOUNT,
              createAmountSmallerThan(CURRENT_COVERAGE_AMOUNT)
            },
            {
              "with requested coverage amount equal to current should throw",
              CURRENT_COVERAGE_AMOUNT,
              CURRENT_COVERAGE_AMOUNT
            }
          });
    }

    @Test(expected = IncreasedCoverageAmountError.class)
    public void validatingCoverageModificationForm() {
      super.validatingCoverageModificationForm();
    }
  }

  public abstract static class TestCase {
    private IncreasedCoverageAmountCoverageModificationFormValidationPart subject;
    private Amount currentCoverageAmount;
    private Amount requestedCoverageAmount;

    public TestCase(Amount currentCoverageAmount, Amount requestedCoverageAmount) {
      this.currentCoverageAmount = currentCoverageAmount;
      this.requestedCoverageAmount = requestedCoverageAmount;
    }

    @Before
    public void setUp() {
      subject = new IncreasedCoverageAmountCoverageModificationFormValidationPart();
    }

    public void validatingCoverageModificationForm() {
      CoverageDetails currentCoverageDetails =
          CoverageDetailsBuilder.aCoverageDetails()
              .withPersonalPropertyCoverageDetail(currentCoverageAmount)
              .build();
      CoverageModificationForm coverageModificationForm =
          CoverageModificationFormBuilder.aCoverageModificationForm()
              .withCoverageAmount(requestedCoverageAmount)
              .withCurrentCoverageDetails(currentCoverageDetails)
              .build();

      subject.validate(coverageModificationForm);
    }
  }
}
