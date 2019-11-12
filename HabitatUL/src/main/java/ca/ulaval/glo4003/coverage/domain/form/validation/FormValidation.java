package ca.ulaval.glo4003.coverage.domain.form.validation;

import java.util.ArrayList;
import java.util.List;

public class FormValidation<T> {
  private List<FormValidationPart<T>> formValidationParts = new ArrayList<>();

  public void addValidationPart(FormValidationPart<T> validationPart) {
    formValidationParts.add(validationPart);
  }

  public void validate(T form) {
    formValidationParts.forEach(formValidationPart -> formValidationPart.validate(form));
  }
}
