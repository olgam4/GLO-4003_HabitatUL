package ca.ulaval.glo4003.management.application.user;

import ca.ulaval.glo4003.management.domain.user.token.TokenPayload;

public interface AccessController {
  void controlAccess(TokenPayload tokenPayload);
}
