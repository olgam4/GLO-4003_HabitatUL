package ca.ulaval.glo4003.gateway.presentation.coverage.request;

import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import java.util.Set;

import static ca.ulaval.glo4003.gateway.presentation.ValidationTestHelper.assertViolationDetected;
import static ca.ulaval.glo4003.gateway.presentation.ValidationTestHelper.getValidator;
import static ca.ulaval.glo4003.helper.coverage.form.personalproperty.BikeGenerator.*;
import static ca.ulaval.glo4003.helper.shared.TemporalGenerator.createYear;

public class BikeRequestTest {
  private BikeRequest subject;
  private Validator validator;

  @Before
  public void setUp() {
    validator = getValidator();
  }

  @Test
  public void validatingRequest_shouldEnforceNotNullPrice() {
    subject = new BikeRequest(null, createBrand(), createModel(), createYear());

    Set<ConstraintViolation<BikeRequest>> violations = validator.validate(subject);

    assertViolationDetected(violations, "price", NotNull.class);
  }

  @Test
  public void validatingRequest_shouldEnforceNotNullBrand() {
    subject = new BikeRequest(createBikePrice(), null, createModel(), createYear());

    Set<ConstraintViolation<BikeRequest>> violations = validator.validate(subject);

    assertViolationDetected(violations, "brand", NotNull.class);
  }

  @Test
  public void validatingRequest_shouldEnforceNotNullModel() {
    subject = new BikeRequest(createBikePrice(), createBrand(), null, createYear());

    Set<ConstraintViolation<BikeRequest>> violations = validator.validate(subject);

    assertViolationDetected(violations, "model", NotNull.class);
  }

  @Test
  public void validatingRequest_shouldEnforceNotNullYear() {
    subject = new BikeRequest(createBikePrice(), createBrand(), createModel(), null);

    Set<ConstraintViolation<BikeRequest>> violations = validator.validate(subject);

    assertViolationDetected(violations, "year", NotNull.class);
  }
}
