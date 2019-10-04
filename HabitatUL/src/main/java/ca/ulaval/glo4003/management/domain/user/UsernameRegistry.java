package ca.ulaval.glo4003.management.domain.user;

public interface UsernameRegistry {
  void register(String userKey, String username);

  String getUserKey(String username);

  String getUsername(String userKey);
}
