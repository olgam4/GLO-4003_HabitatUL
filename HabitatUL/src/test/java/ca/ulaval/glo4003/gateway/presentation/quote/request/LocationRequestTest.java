package ca.ulaval.glo4003.gateway.presentation.quote.request;

import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import java.util.Set;

import static ca.ulaval.glo4003.gateway.presentation.ValidationTestHelper.assertViolationDetected;
import static ca.ulaval.glo4003.gateway.presentation.ValidationTestHelper.getValidator;
import static ca.ulaval.glo4003.helper.quote.form.LocationGenerator.*;

public class LocationRequestTest {
  private LocationRequest subject;
  private Validator validator;

  @Before
  public void setUp() {
    validator = getValidator();
  }

  @Test
  public void validatingRequest_shouldEnforceNotNullZipCode() {
    subject =
        new LocationRequest(null, createStreetNumber(), createApartmentNumber(), createFloor());

    Set<ConstraintViolation<LocationRequest>> violations = validator.validate(subject);

    assertViolationDetected(violations, "zipCode", NotNull.class);
  }

  @Test
  public void validatingRequest_shouldEnforceNotNullStreetNumber() {
    subject = new LocationRequest(createZipCode(), null, createApartmentNumber(), createFloor());

    Set<ConstraintViolation<LocationRequest>> violations = validator.validate(subject);

    assertViolationDetected(violations, "streetNumber", NotNull.class);
  }

  @Test
  public void validatingRequest_shouldEnforceNotNullApartmentNumber() {
    subject = new LocationRequest(createZipCode(), createStreetNumber(), null, createFloor());

    Set<ConstraintViolation<LocationRequest>> violations = validator.validate(subject);

    assertViolationDetected(violations, "apartmentNumber", NotNull.class);
  }

  @Test
  public void validatingRequest_shouldEnforceNotNullFloor() {
    subject =
        new LocationRequest(createZipCode(), createStreetNumber(), createApartmentNumber(), null);

    Set<ConstraintViolation<LocationRequest>> violations = validator.validate(subject);

    assertViolationDetected(violations, "floor", NotNull.class);
  }
}
