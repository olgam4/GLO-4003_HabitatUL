package ca.ulaval.glo4003.administration.infrastructure.user.credential;

import java.util.HashMap;
import java.util.Map;

public class InMemoryPbkdfPasswordStorage implements PbkdfPasswordStorage {
  private Map<String, Secret> values;

  public InMemoryPbkdfPasswordStorage() {
    values = new HashMap<>();
  }

  @Override
  public void store(String key, Secret secret) {
    values.put(key, secret);
  }

  @Override
  public Secret fetch(String key) {
    return values.get(key);
  }
}
