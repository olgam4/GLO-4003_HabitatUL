package ca.ulaval.glo4003.projet.base.context;

import ca.ulaval.glo4003.projet.base.context.exception.CannotRegisterContractTwiceException;
import ca.ulaval.glo4003.projet.base.context.exception.UnableResolveServiceException;
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
      throw new UnableResolveServiceException(contract);
    }
    return (T) services.get(contract);
  }

  public static void reset() {
    services.clear();
  }
}
