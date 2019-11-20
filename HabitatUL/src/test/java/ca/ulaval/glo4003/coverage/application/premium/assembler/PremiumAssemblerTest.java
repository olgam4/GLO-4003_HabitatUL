package ca.ulaval.glo4003.coverage.application.premium.assembler;

import ca.ulaval.glo4003.coverage.domain.form.CoverageModificationForm;
import ca.ulaval.glo4003.coverage.domain.premium.formula.coveragemodification.CoverageModificationPremiumInput;
import ca.ulaval.glo4003.helper.coverage.form.CoverageModificationFormBuilder;
import ca.ulaval.glo4003.shared.domain.handling.InvalidArgumentException;
import org.junit.Before;
import org.junit.Test;

import static ca.ulaval.glo4003.helper.coverage.form.CoverageModificationFormGenerator.createCoverageModificationForm;
import static org.junit.Assert.assertEquals;

public class PremiumAssemblerTest {
  private PremiumAssembler subject;

  @Before
  public void setUp() {
    subject = new PremiumAssembler();
  }

  @Test
  public void
      assemblingUpdatedCoverageModificationPremiumInput_withoutUpdatedCivilLiabilityLimit_shouldDefaultToCurrentValue()
          throws InvalidArgumentException {
    CoverageModificationForm coverageModificationForm =
        CoverageModificationFormBuilder.aCoverageModificationForm()
            .withCivilLiabilityLimit(null)
            .build();

    CoverageModificationPremiumInput updatedPremiumInput =
        subject.toUpdatedCoverageModificationPremiumInput(coverageModificationForm);
    CoverageModificationPremiumInput currentPremiumInput =
        subject.toCurrentCoverageModificationPremiumInput(coverageModificationForm);

    assertEquals(
        currentPremiumInput.getCivilLiabilityLimit(), updatedPremiumInput.getCivilLiabilityLimit());
  }

  @Test
  public void
      assemblingUpdatedCoverageModificationPremiumInput_withUpdatedCivilLiabilityLimit_shouldUseUpdatedValue()
          throws InvalidArgumentException {
    CoverageModificationForm coverageModificationForm = createCoverageModificationForm();

    CoverageModificationPremiumInput updatedPremiumInput =
        subject.toUpdatedCoverageModificationPremiumInput(coverageModificationForm);

    assertEquals(
        coverageModificationForm.getCivilLiabilityLimit(),
        updatedPremiumInput.getCivilLiabilityLimit());
  }
}
