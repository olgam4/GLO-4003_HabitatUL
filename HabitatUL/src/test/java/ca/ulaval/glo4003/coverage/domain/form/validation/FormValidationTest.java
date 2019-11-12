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
    subject = new FormValidation();
  }

  @Test
  public void validatingForm_shouldConsiderAllValidationParts() {
    subject.addValidationPart(formValidationPart);
    subject.addValidationPart(anotherFormValidationPart);

    subject.validate(form);

    verify(formValidationPart).validate(form);
    verify(anotherFormValidationPart).validate(form);
  }

  public abstract T createForm();
}
