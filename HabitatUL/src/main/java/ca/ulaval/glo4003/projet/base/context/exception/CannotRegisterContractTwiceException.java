package ca.ulaval.glo4003.projet.base.context.exception;

public class CannotRegisterContractTwiceException extends ServiceLocatorException {
  public CannotRegisterContractTwiceException(Class<?> contract) {
    super(
        String.format(
            "You've tried to register this contract twice : '%s'", contract.getCanonicalName()));
  }
}
