package ca.ulaval.glo4003.administration.domain.user;

import java.util.UUID;

public class UserKeyGenerator {
  public String generateUserKey() {
    return UUID.randomUUID().toString();
  }
}
