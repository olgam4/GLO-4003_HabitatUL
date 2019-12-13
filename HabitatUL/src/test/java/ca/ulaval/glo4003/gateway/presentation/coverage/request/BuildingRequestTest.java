package ca.ulaval.glo4003.gateway.presentation.coverage.request;

import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Set;

import static ca.ulaval.glo4003.coverage.helper.form.building.BuildingGenerator.createCommercialUse;
import static ca.ulaval.glo4003.coverage.helper.form.building.BuildingGenerator.createPreventionSystems;
import static ca.ulaval.glo4003.gateway.presentation.ValidationTestHelper.assertViolationDetected;
import static ca.ulaval.glo4003.gateway.presentation.ValidationTestHelper.getValidator;

public class BuildingRequestTest {
  private BuildingRequest subject;
  private Validator validator;

  @Before
  public void setUp() {
    validator = getValidator();
  }

  @Test
  public void validatingRequest_shouldEnforceNotNullNumberOfUnits() {
    subject = new BuildingRequest(null, createPreventionSystems(), createCommercialUse());

    Set<ConstraintViolation<BuildingRequest>> violations = validator.validate(subject);

    assertViolationDetected(violations, "numberOfUnits", NotNull.class);
  }

  @Test
  public void validatingRequest_shouldNotAllowNegativeNumberOfUnits() {
    subject = new BuildingRequest(-1, createPreventionSystems(), createCommercialUse());

    Set<ConstraintViolation<BuildingRequest>> violations = validator.validate(subject);

    assertViolationDetected(violations, "numberOfUnits", Min.class);
  }
}
