package ca.ulaval.glo4003.context.exception;

public class CannotResolveServiceException extends ServiceLocatorException {
  public CannotResolveServiceException(Class<?> contract) {
    super(String.format("cannot resolve a service for <%s>", contract.getCanonicalName()));
  }
}
