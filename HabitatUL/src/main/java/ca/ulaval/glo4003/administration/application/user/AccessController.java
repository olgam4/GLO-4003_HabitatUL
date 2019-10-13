package ca.ulaval.glo4003.administration.application.user;

import ca.ulaval.glo4003.administration.domain.user.token.TokenPayload;

public interface AccessController {
  void controlAccess(TokenPayload tokenPayload);
}
