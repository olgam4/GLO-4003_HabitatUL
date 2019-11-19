package ca.ulaval.glo4003.coverage.domain.form.validation.part;

import ca.ulaval.glo4003.coverage.domain.form.CoverageModificationForm;
import ca.ulaval.glo4003.coverage.domain.form.validation.error.PositiveCoverageAmountError;
import ca.ulaval.glo4003.helper.coverage.form.CoverageModificationFormBuilder;
import ca.ulaval.glo4003.shared.domain.money.Amount;
import org.junit.Before;
import org.junit.Test;

import static ca.ulaval.glo4003.helper.shared.MoneyGenerator.createAmountGreaterThanZero;
import static ca.ulaval.glo4003.helper.shared.MoneyGenerator.createAmountSmallerThanZero;

public class PositiveCoverageAmountCoverageModificationFormValidationPartTest {
  private PositiveCoverageAmountCoverageModificationFormValidationPart subject;
  private CoverageModificationForm coverageModificationForm;

  @Before
  public void setUp() {
    subject = new PositiveCoverageAmountCoverageModificationFormValidationPart();
  }

  @Test
  public void validatingCoverageModificationForm_withPositiveCoverageAmount_shouldNotThrow() {
    Amount coverageAmount = createAmountGreaterThanZero();
    coverageModificationForm =
        CoverageModificationFormBuilder.aCoverageModificationForm()
            .withPersonalProperty(coverageAmount)
            .build();

    subject.validate(coverageModificationForm);
  }

  @Test
  public void validatingCoverageModificationForm_withNullCoverageAmount_shouldNotThrow() {
    coverageModificationForm =
        CoverageModificationFormBuilder.aCoverageModificationForm()
            .withPersonalProperty(null)
            .build();

    subject.validate(coverageModificationForm);
  }

  @Test(expected = PositiveCoverageAmountError.class)
  public void validatingCoverageModificationForm_withNegativeCoverageAmount_shouldThrow() {
    Amount coverageAmount = createAmountSmallerThanZero();
    coverageModificationForm =
        CoverageModificationFormBuilder.aCoverageModificationForm()
            .withPersonalProperty(coverageAmount)
            .build();

    subject.validate(coverageModificationForm);
  }
}
