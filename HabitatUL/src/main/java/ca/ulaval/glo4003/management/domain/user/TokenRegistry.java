package ca.ulaval.glo4003.management.domain.user;

public interface TokenRegistry {
  void register(String userKey, String token);

  String getUserKey(String token);

  String getToken(String userKey);
}
