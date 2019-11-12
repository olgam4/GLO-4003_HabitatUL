package ca.ulaval.glo4003.coverage.domain.form.validation;

public interface FormValidationPart<T> {
  void validate(T form);
}
