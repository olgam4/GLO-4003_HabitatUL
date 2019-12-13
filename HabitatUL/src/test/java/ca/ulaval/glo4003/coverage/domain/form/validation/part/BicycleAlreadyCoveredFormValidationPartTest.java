package ca.ulaval.glo4003.coverage.domain.form.validation.part;

import ca.ulaval.glo4003.coverage.domain.coverage.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.form.BicycleEndorsementForm;
import ca.ulaval.glo4003.coverage.domain.form.validation.error.BicycleAlreadyCoveredError;
import ca.ulaval.glo4003.coverage.helper.coverage.CoverageDetailsBuilder;
import ca.ulaval.glo4003.coverage.helper.form.BicycleEndorsementFormBuilder;
import org.junit.Before;
import org.junit.Test;

import static ca.ulaval.glo4003.coverage.helper.form.personalproperty.PersonalPropertyGenerator.createCoverageAmount;

public class BicycleAlreadyCoveredFormValidationPartTest {
  private BicycleAlreadyCoveredFormValidationPart subject;

  @Before
  public void setUp() {
    subject = new BicycleAlreadyCoveredFormValidationPart();
  }

  @Test
  public void validatingBicycleEndorsementForm_withoutBicycleAlreadyCovered_shouldNotThrow() {
    CoverageDetails coverageDetails = CoverageDetailsBuilder.aCoverageDetails().build();
    BicycleEndorsementForm bicycleEndorsementForm =
        BicycleEndorsementFormBuilder.aBicycleEndorsementForm()
            .withCurrentCoverageDetails(coverageDetails)
            .build();

    subject.validate(bicycleEndorsementForm);
  }

  @Test(expected = BicycleAlreadyCoveredError.class)
  public void validatingBicycleEndorsementForm_withBicycleAlreadyCovered_shouldThrow() {
    CoverageDetails coverageDetails =
        CoverageDetailsBuilder.aCoverageDetails()
            .withBicycleEndorsementCoverageDetail(createCoverageAmount())
            .build();
    BicycleEndorsementForm bicycleEndorsementForm =
        BicycleEndorsementFormBuilder.aBicycleEndorsementForm()
            .withCurrentCoverageDetails(coverageDetails)
            .build();

    subject.validate(bicycleEndorsementForm);
  }
}
