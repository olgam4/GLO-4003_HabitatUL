package ca.ulaval.glo4003.administration.persistence.user;

import ca.ulaval.glo4003.administration.domain.user.PolicyRegistry;
import ca.ulaval.glo4003.administration.domain.user.exception.KeyNotFoundException;

import java.util.*;

public class InMemoryPolicyRegistry implements PolicyRegistry {
  private Map<String, List<String>> policyKeysByUserKey = new HashMap<>();
  private Map<String, String> userKeyByPolicyKey = new HashMap<>();

  @Override
  public void register(String userKey, String policyKey) {
    List<String> quoteKeys = policyKeysByUserKey.getOrDefault(userKey, new ArrayList<>());
    quoteKeys.add(policyKey);
    policyKeysByUserKey.put(userKey, quoteKeys);
    userKeyByPolicyKey.put(policyKey, userKey);
  }

  @Override
  public String getUserKey(String policyKey) throws KeyNotFoundException {
    return Optional.ofNullable(userKeyByPolicyKey.get(policyKey))
        .orElseThrow(KeyNotFoundException::new);
  }

  @Override
  public List<String> getPolicyKeys(String userKey) {
    return policyKeysByUserKey.getOrDefault(userKey, new ArrayList<>());
  }
}
