package ca.ulaval.glo4003.administration.domain.user;

import ca.ulaval.glo4003.administration.domain.user.exception.KeyNotFoundException;

import java.util.List;

public interface PolicyRegistry {
  void register(String userKey, String policyKey);

  String getUserKey(String policyKey) throws KeyNotFoundException;

  List<String> getPolicyKeys(String userKey);
}
