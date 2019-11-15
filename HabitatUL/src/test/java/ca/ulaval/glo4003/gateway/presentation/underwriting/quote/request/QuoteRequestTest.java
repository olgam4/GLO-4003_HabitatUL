package ca.ulaval.glo4003.gateway.presentation.underwriting.quote.request;

import ca.ulaval.glo4003.gateway.presentation.coverage.request.*;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import java.util.Set;

import static ca.ulaval.glo4003.gateway.presentation.ValidationTestHelper.assertViolationDetected;
import static ca.ulaval.glo4003.gateway.presentation.ValidationTestHelper.getValidator;
import static ca.ulaval.glo4003.helper.coverage.form.building.BuildingGenerator.createBuildingRequest;
import static ca.ulaval.glo4003.helper.coverage.form.civilliability.CivilLiabilityGenerator.createCivilLiabilityRequest;
import static ca.ulaval.glo4003.helper.coverage.form.identity.IdentityGenerator.createIdentityRequest;
import static ca.ulaval.glo4003.helper.coverage.form.location.LocationGenerator.createLocationRequest;
import static ca.ulaval.glo4003.helper.coverage.form.personalproperty.PersonalPropertyGenerator.createPersonalPropertyRequest;
import static ca.ulaval.glo4003.helper.shared.TemporalGenerator.createFutureDate;

public class QuoteRequestTest {
  private QuoteRequest subject;
  private Validator validator;

  @Before
  public void setUp() {
    validator = getValidator();
  }

  @Test
  public void validatingRequest_shouldEnforceNotNullPersonalInformation() {
    subject =
        new QuoteRequest(
            null,
            createIdentityRequest(),
            createLocationRequest(),
            createFutureDate(),
            createBuildingRequest(),
            createPersonalPropertyRequest(),
            createCivilLiabilityRequest());

    Set<ConstraintViolation<QuoteRequest>> violations;
    violations = validator.validate(subject);

    assertViolationDetected(violations, "personalInformation", NotNull.class);
  }

  @Test
  public void validatingRequest_shouldEnforceValidPersonalInformation() {
    subject =
        new QuoteRequest(
            new IdentityRequest(null, null, null, null, null),
            createIdentityRequest(),
            createLocationRequest(),
            createFutureDate(),
            createBuildingRequest(),
            createPersonalPropertyRequest(),
            createCivilLiabilityRequest());

    Set<ConstraintViolation<QuoteRequest>> violations = validator.validate(subject);

    assertViolationDetected(violations, "personalInformation");
  }

  @Test
  public void validatingRequest_shouldEnforceValidAdditionalInsured() {
    subject =
        new QuoteRequest(
            createIdentityRequest(),
            new IdentityRequest(null, null, null, null, null),
            createLocationRequest(),
            createFutureDate(),
            createBuildingRequest(),
            createPersonalPropertyRequest(),
            createCivilLiabilityRequest());

    Set<ConstraintViolation<QuoteRequest>> violations = validator.validate(subject);

    assertViolationDetected(violations, "additionalInsured");
  }

  @Test
  public void validatingRequest_shouldEnforceNotNullLocation() {
    subject =
        new QuoteRequest(
            createIdentityRequest(),
            createIdentityRequest(),
            null,
            createFutureDate(),
            createBuildingRequest(),
            createPersonalPropertyRequest(),
            createCivilLiabilityRequest());

    Set<ConstraintViolation<QuoteRequest>> violations;
    violations = validator.validate(subject);

    assertViolationDetected(violations, "location", NotNull.class);
  }

  @Test
  public void validatingRequest_shouldEnforceValidLocation() {
    subject =
        new QuoteRequest(
            createIdentityRequest(),
            createIdentityRequest(),
            new LocationRequest(null, null, null, null),
            createFutureDate(),
            createBuildingRequest(),
            createPersonalPropertyRequest(),
            createCivilLiabilityRequest());

    Set<ConstraintViolation<QuoteRequest>> violations = validator.validate(subject);

    assertViolationDetected(violations, "location");
  }

  @Test
  public void validatingRequest_shouldEnforceNotNullEffectiveDate() {
    subject =
        new QuoteRequest(
            createIdentityRequest(),
            createIdentityRequest(),
            createLocationRequest(),
            null,
            createBuildingRequest(),
            createPersonalPropertyRequest(),
            createCivilLiabilityRequest());

    Set<ConstraintViolation<QuoteRequest>> violations;
    violations = validator.validate(subject);

    assertViolationDetected(violations, "effectiveDate", NotNull.class);
  }

  @Test
  public void validatingRequest_shouldEnforceNotNullBuilding() {
    subject =
        new QuoteRequest(
            createIdentityRequest(),
            createIdentityRequest(),
            createLocationRequest(),
            createFutureDate(),
            null,
            createPersonalPropertyRequest(),
            createCivilLiabilityRequest());

    Set<ConstraintViolation<QuoteRequest>> violations;
    violations = validator.validate(subject);

    assertViolationDetected(violations, "building", NotNull.class);
  }

  @Test
  public void validatingRequest_shouldEnforceValidBuilding() {
    subject =
        new QuoteRequest(
            createIdentityRequest(),
            createIdentityRequest(),
            createLocationRequest(),
            createFutureDate(),
            new BuildingRequest(null, null, null),
            createPersonalPropertyRequest(),
            createCivilLiabilityRequest());

    Set<ConstraintViolation<QuoteRequest>> violations = validator.validate(subject);

    assertViolationDetected(violations, "building");
  }

  @Test
  public void validatingRequest_shouldEnforceNotNullPersonalProperty() {
    subject =
        new QuoteRequest(
            createIdentityRequest(),
            createIdentityRequest(),
            createLocationRequest(),
            createFutureDate(),
            createBuildingRequest(),
            null,
            createCivilLiabilityRequest());

    Set<ConstraintViolation<QuoteRequest>> violations;
    violations = validator.validate(subject);

    assertViolationDetected(violations, "personalProperty", NotNull.class);
  }

  @Test
  public void validatingRequest_shouldEnforceValidPersonalProperty() {
    subject =
        new QuoteRequest(
            createIdentityRequest(),
            createIdentityRequest(),
            createLocationRequest(),
            createFutureDate(),
            createBuildingRequest(),
            new PersonalPropertyRequest(null, null, null),
            createCivilLiabilityRequest());

    Set<ConstraintViolation<QuoteRequest>> violations = validator.validate(subject);

    assertViolationDetected(violations, "personalProperty");
  }

  @Test
  public void validatingRequest_shouldEnforceValidCivilLiability() {
    subject =
        new QuoteRequest(
            createIdentityRequest(),
            createIdentityRequest(),
            createLocationRequest(),
            createFutureDate(),
            createBuildingRequest(),
            createPersonalPropertyRequest(),
            new CivilLiabilityRequest(null));

    Set<ConstraintViolation<QuoteRequest>> violations = validator.validate(subject);

    assertViolationDetected(violations, "civilLiability");
  }
}
