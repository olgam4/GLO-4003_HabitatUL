package ca.ulaval.glo4003.coverage.domain.form.validation.part;

import ca.ulaval.glo4003.coverage.domain.form.CoverageRenewalForm;
import ca.ulaval.glo4003.coverage.domain.form.validation.error.PositiveCoverageAmountError;
import ca.ulaval.glo4003.helper.coverage.form.CoverageRenewalFormBuilder;
import ca.ulaval.glo4003.shared.domain.money.Amount;
import org.junit.Before;
import org.junit.Test;

import static ca.ulaval.glo4003.helper.shared.MoneyGenerator.createAmountGreaterThanZero;
import static ca.ulaval.glo4003.helper.shared.MoneyGenerator.createAmountSmallerThanZero;

public class PositiveCoverageAmountCoverageRenewalFormValidationPartTest {
  private PositiveCoverageAmountCoverageRenewalFormValidationPart subject;
  private CoverageRenewalForm coverageRenewalForm;

  @Before
  public void setUp() {
    subject = new PositiveCoverageAmountCoverageRenewalFormValidationPart();
  }

  @Test
  public void validatingCoverageRenewalForm_withPositiveCoverageAmount_shouldNotThrow() {
    Amount coverageAmount = createAmountGreaterThanZero();
    coverageRenewalForm =
        CoverageRenewalFormBuilder.aCoverageRenewalForm()
            .withCoverageAmount(coverageAmount)
            .build();

    subject.validate(coverageRenewalForm);
  }

  @Test
  public void validatingCoverageRenewalForm_withNullCoverageAmount_shouldNotThrow() {
    coverageRenewalForm =
        CoverageRenewalFormBuilder.aCoverageRenewalForm().withCoverageAmount(null).build();

    subject.validate(coverageRenewalForm);
  }

  @Test(expected = PositiveCoverageAmountError.class)
  public void validatingCoverageRenewalForm_withNegativeCoverageAmount_shouldThrow() {
    Amount coverageAmount = createAmountSmallerThanZero();
    coverageRenewalForm =
        CoverageRenewalFormBuilder.aCoverageRenewalForm()
            .withCoverageAmount(coverageAmount)
            .build();

    subject.validate(coverageRenewalForm);
  }
}
