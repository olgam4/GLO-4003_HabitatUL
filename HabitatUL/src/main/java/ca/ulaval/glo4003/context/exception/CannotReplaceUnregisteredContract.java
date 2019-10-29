package ca.ulaval.glo4003.context.exception;

public class CannotReplaceUnregisteredContract extends ServiceLocatorException {
  public CannotReplaceUnregisteredContract(Class<?> contract) {
    super(
        String.format("cannot replace a unregistered contract: <%s>", contract.getCanonicalName()));
  }
}
