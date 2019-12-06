package ca.ulaval.glo4003.shared.domain.handling;

public interface Error {
  String getError();

  String getMessage();

  Throwable getCause();
}
