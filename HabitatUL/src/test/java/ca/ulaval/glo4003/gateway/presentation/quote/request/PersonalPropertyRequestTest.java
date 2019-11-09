package ca.ulaval.glo4003.gateway.presentation.quote.request;

import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import java.util.Set;

import static ca.ulaval.glo4003.gateway.presentation.ValidationTestHelper.assertViolationDetected;
import static ca.ulaval.glo4003.gateway.presentation.ValidationTestHelper.getValidator;
import static ca.ulaval.glo4003.helper.quote.form.BikeGenerator.createBikeRequest;
import static ca.ulaval.glo4003.helper.quote.form.PersonalPropertyGenerator.createAnimals;
import static ca.ulaval.glo4003.helper.quote.form.PersonalPropertyGenerator.createCoverageAmount;

public class PersonalPropertyRequestTest {
  private PersonalPropertyRequest subject;
  private Validator validator;

  @Before
  public void setUp() {
    validator = getValidator();
  }

  @Test
  public void validatingRequest_shouldEnforceNotNullCoverageAmount() {
    subject = new PersonalPropertyRequest(null, createAnimals(), createBikeRequest());

    Set<ConstraintViolation<PersonalPropertyRequest>> violations = validator.validate(subject);

    assertViolationDetected(violations, "coverageAmount", NotNull.class);
  }

  @Test
  public void validatingRequest_shouldEnforceValidBike() {
    subject =
        new PersonalPropertyRequest(
            createCoverageAmount(), createAnimals(), new BikeRequest(null, null, null, null));

    Set<ConstraintViolation<PersonalPropertyRequest>> violations = validator.validate(subject);

    assertViolationDetected(violations, "bike");
  }
}
