package ca.ulaval.glo4003.gateway.presentation;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.lang.annotation.Annotation;
import java.util.Set;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertEquals;

public class ValidationTestHelper {
  public static Validator getValidator() {
    ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    return validatorFactory.getValidator();
  }

  public static <T> void assertViolationDetected(
      Set<ConstraintViolation<T>> violations, String propertyName, Class violationClass) {
    assertThat(violations).isNotEmpty();
    assertThat(violations).hasSize(1);
    ConstraintViolation<T> violation = violations.iterator().next();
    Annotation annotation = violation.getConstraintDescriptor().getAnnotation();
    assertEquals(violationClass, annotation.annotationType());
    assertEquals(propertyName, violation.getPropertyPath().toString());
  }

  public static <T> void assertViolationDetected(
      Set<ConstraintViolation<T>> violations, String propertyName) {
    assertThat(violations).isNotEmpty();
    violations
        .iterator()
        .forEachRemaining(
            x ->
                assertThat(
                    x.getPropertyPath().toString().startsWith(String.format("%s.", propertyName))));
  }
}
