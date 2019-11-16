package ca.ulaval.glo4003.coverage.domain.form.validation.part.error;

import ca.ulaval.glo4003.shared.domain.handling.BaseError;

public abstract class FormValidationError extends BaseError {
  public FormValidationError(String error, String message) {
    super(error, message);
  }
}
