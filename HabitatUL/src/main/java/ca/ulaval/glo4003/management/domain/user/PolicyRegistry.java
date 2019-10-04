package ca.ulaval.glo4003.management.domain.user;

import java.util.List;

public interface PolicyRegistry {
  void register(String userKey, String policyKey);

  String getUserKey(String policyKey);

  List<String> getPolicyKeys(String userKey);
}
