package ca.ulaval.glo4003.administration.application.user;

import ca.ulaval.glo4003.administration.domain.user.credential.Credentials;
import ca.ulaval.glo4003.administration.domain.user.token.Token;
import ca.ulaval.glo4003.administration.domain.user.token.TokenPayload;
import ca.ulaval.glo4003.shared.domain.money.Money;

import java.util.List;

public interface UserAppService extends AccessController {
  String createUser(Credentials credentials);

  Token authenticateUser(Credentials credentials);

  void controlAccess(TokenPayload tokenPayload);

  void associateQuote(String userKey, String quoteKey);

  void associatePolicy(String quoteKey, String policyKey);

  void processQuotePayment(String quoteKey, Money payment);

  List<String> getPolicies(String userKey);
}
