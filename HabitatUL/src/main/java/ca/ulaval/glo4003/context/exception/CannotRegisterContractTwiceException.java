package ca.ulaval.glo4003.context.exception;

public class CannotRegisterContractTwiceException extends ServiceLocatorException {
  public CannotRegisterContractTwiceException(Class<?> contract) {
    super(
        String.format(
            "cannot register this contract twice: <%s>", contract.getCanonicalName()));
  }
}
