package ca.ulaval.glo4003.projet.base.context.exception;

public class UnableResolveServiceException extends ServiceLocatorException {
  public UnableResolveServiceException(Class<?> contract) {
    super(String.format("Unable to resolve a service for '%s'.", contract.getCanonicalName()));
  }
}
