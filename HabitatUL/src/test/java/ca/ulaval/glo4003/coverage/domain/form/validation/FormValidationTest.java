package ca.ulaval.glo4003.coverage.domain.form.validation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public abstract class FormValidationTest<T> {
  @Mock private FormValidationPart formValidationPart;
  @Mock private FormValidationPart anotherFormValidationPart;

  private FormValidation subject;
  private T form;

  @Before
  public void setUp() {
    form = createForm();
    subject = createSubject();
  }

  @Test
  public void validatingForm_shouldConsiderAllValidationParts() {
    subject.addFormValidationPart(formValidationPart);
    subject.addFormValidationPart(anotherFormValidationPart);

    subject.validate(form);

    verify(formValidationPart).validate(form);
    verify(anotherFormValidationPart).validate(form);
  }

  public abstract FormValidation createSubject();

  public abstract T createForm();
}
