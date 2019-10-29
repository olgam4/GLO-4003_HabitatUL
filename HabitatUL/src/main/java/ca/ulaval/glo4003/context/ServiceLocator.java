package ca.ulaval.glo4003.context;

import ca.ulaval.glo4003.context.exception.CannotRegisterContractTwiceException;
import ca.ulaval.glo4003.context.exception.CannotReplaceUnregisteredContract;
import ca.ulaval.glo4003.context.exception.CannotResolveServiceException;

import java.util.HashMap;
import java.util.Map;

public class ServiceLocator {
  private static final Map<Class<?>, Object> services = new HashMap<>();

  private ServiceLocator() {}

  public static <T> void register(Class<T> contract, T service) {
    if (services.containsKey(contract)) {
      throw new CannotRegisterContractTwiceException(contract);
    }
    services.put(contract, service);
  }

  @SuppressWarnings("unchecked")
  public static <T> T resolve(Class<? extends T> contract) {
    if (!services.containsKey(contract)) {
      throw new CannotResolveServiceException(contract);
    }
    return (T) services.get(contract);
  }

  public static <T> void replace(Class<T> contract, T service) {
    if (!services.containsKey(contract)) {
      throw new CannotReplaceUnregisteredContract(contract);
    }
    services.put(contract, service);
  }

  public static void reset() {
    services.clear();
  }
}
