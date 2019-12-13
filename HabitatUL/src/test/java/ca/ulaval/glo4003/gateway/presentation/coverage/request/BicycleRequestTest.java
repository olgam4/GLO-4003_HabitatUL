package ca.ulaval.glo4003.gateway.presentation.coverage.request;

import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import java.util.Set;

import static ca.ulaval.glo4003.coverage.helper.form.personalproperty.BicycleGenerator.*;
import static ca.ulaval.glo4003.gateway.presentation.ValidationTestHelper.assertViolationDetected;
import static ca.ulaval.glo4003.gateway.presentation.ValidationTestHelper.getValidator;
import static ca.ulaval.glo4003.shared.helper.TemporalGenerator.createYear;

public class BicycleRequestTest {
  private BicycleRequest subject;
  private Validator validator;

  @Before
  public void setUp() {
    validator = getValidator();
  }

  @Test
  public void validatingRequest_shouldEnforceNotNullPrice() {
    subject = new BicycleRequest(null, createBrand(), createModel(), createYear());

    Set<ConstraintViolation<BicycleRequest>> violations = validator.validate(subject);

    assertViolationDetected(violations, "price", NotNull.class);
  }

  @Test
  public void validatingRequest_shouldEnforceNotNullBrand() {
    subject = new BicycleRequest(createBicyclePrice(), null, createModel(), createYear());

    Set<ConstraintViolation<BicycleRequest>> violations = validator.validate(subject);

    assertViolationDetected(violations, "brand", NotNull.class);
  }

  @Test
  public void validatingRequest_shouldEnforceNotNullModel() {
    subject = new BicycleRequest(createBicyclePrice(), createBrand(), null, createYear());

    Set<ConstraintViolation<BicycleRequest>> violations = validator.validate(subject);

    assertViolationDetected(violations, "model", NotNull.class);
  }

  @Test
  public void validatingRequest_shouldEnforceNotNullYear() {
    subject = new BicycleRequest(createBicyclePrice(), createBrand(), createModel(), null);

    Set<ConstraintViolation<BicycleRequest>> violations = validator.validate(subject);

    assertViolationDetected(violations, "year", NotNull.class);
  }
}
