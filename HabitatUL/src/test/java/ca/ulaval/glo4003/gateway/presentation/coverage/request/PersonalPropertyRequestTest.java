package ca.ulaval.glo4003.gateway.presentation.coverage.request;

import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import java.util.Set;

import static ca.ulaval.glo4003.coverage.helper.form.personalproperty.BicycleGenerator.createBicycleRequest;
import static ca.ulaval.glo4003.coverage.helper.form.personalproperty.PersonalPropertyGenerator.createCoverageAmount;
import static ca.ulaval.glo4003.coverage.helper.premium.QuotePremiumInputGenerator.createAnimals;
import static ca.ulaval.glo4003.gateway.presentation.ValidationTestHelper.assertViolationDetected;
import static ca.ulaval.glo4003.gateway.presentation.ValidationTestHelper.getValidator;

public class PersonalPropertyRequestTest {
  private PersonalPropertyRequest subject;
  private Validator validator;

  @Before
  public void setUp() {
    validator = getValidator();
  }

  @Test
  public void validatingRequest_shouldEnforceNotNullCoverageAmount() {
    subject = new PersonalPropertyRequest(null, createAnimals(), createBicycleRequest());

    Set<ConstraintViolation<PersonalPropertyRequest>> violations = validator.validate(subject);

    assertViolationDetected(violations, "coverageAmount", NotNull.class);
  }

  @Test
  public void validatingRequest_shouldEnforceValidBicycle() {
    subject =
        new PersonalPropertyRequest(
            createCoverageAmount(), createAnimals(), new BicycleRequest(null, null, null, null));

    Set<ConstraintViolation<PersonalPropertyRequest>> violations = validator.validate(subject);

    assertViolationDetected(violations, "bicycle");
  }
}
