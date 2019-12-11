package ca.ulaval.glo4003.administration.infrastructure.user.credential;

public interface PbkdfPasswordStorage {
  void store(String key, Secret secret);

  Secret fetch(String key);
}
