package ca.ulaval.glo4003.coverage.domain.form.validation;

import java.util.ArrayList;
import java.util.List;

public abstract class FormValidation<T> {
  private List<FormValidationPart<T>> formValidationParts = new ArrayList<>();

  public void addFormValidationPart(FormValidationPart<T> formValidationPart) {
    formValidationParts.add(formValidationPart);
  }

  public void validate(T form) {
    formValidationParts.forEach(formValidationPart -> formValidationPart.validate(form));
  }
}
