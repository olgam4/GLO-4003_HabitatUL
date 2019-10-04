package ca.ulaval.glo4003.management.application.user;

import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.management.application.user.exception.InvalidCredentialsException;
import ca.ulaval.glo4003.management.domain.user.PolicyRegistry;
import ca.ulaval.glo4003.management.domain.user.QuoteRegistry;
import ca.ulaval.glo4003.management.domain.user.UserKeyGenerator;
import ca.ulaval.glo4003.management.domain.user.UsernameRegistry;
import ca.ulaval.glo4003.management.domain.user.credential.Credentials;
import ca.ulaval.glo4003.management.domain.user.credential.PasswordValidator;
import ca.ulaval.glo4003.management.domain.user.token.Token;
import ca.ulaval.glo4003.management.domain.user.token.TokenPayload;
import ca.ulaval.glo4003.management.domain.user.token.TokenTranslator;

public class UserAppService {
  private UsernameRegistry usernameRegistry;
  private QuoteRegistry quoteRegistry;
  private PolicyRegistry policyRegistry;
  private PasswordValidator passwordValidator;
  private TokenTranslator tokenTranslator;

  public UserAppService() {
    this(
        ServiceLocator.resolve(UsernameRegistry.class),
        ServiceLocator.resolve(QuoteRegistry.class),
        ServiceLocator.resolve(PolicyRegistry.class),
        ServiceLocator.resolve(PasswordValidator.class),
        ServiceLocator.resolve(TokenTranslator.class));
  }

  public UserAppService(
      UsernameRegistry usernameRegistry,
      QuoteRegistry quoteRegistry,
      PolicyRegistry policyRegistry,
      PasswordValidator passwordValidator,
      TokenTranslator tokenTranslator) {
    this.usernameRegistry = usernameRegistry;
    this.quoteRegistry = quoteRegistry;
    this.policyRegistry = policyRegistry;
    this.passwordValidator = passwordValidator;
    this.tokenTranslator = tokenTranslator;
  }

  public String createUser(Credentials credentials) {
    String userKey = UserKeyGenerator.generateUserKey();
    usernameRegistry.register(userKey, credentials.getUsername());
    passwordValidator.registerPassword(userKey, credentials.getPassword());
    return userKey;
  }

  public Token authenticateUser(Credentials credentials) {
    String username = credentials.getUsername();
    String userKey = usernameRegistry.getUserKey(username);
    validateCredentials(userKey, credentials.getPassword());
    TokenPayload tokenPayload = new TokenPayload(userKey, username);
    return tokenTranslator.encodeToken(tokenPayload);
  }

  private void validateCredentials(String userKey, String password) {
    if (isInvalidCredentials(userKey, password)) throw new InvalidCredentialsException();
  }

  private boolean isInvalidCredentials(String userKey, String password) {
    return !passwordValidator.validatePassword(userKey, password);
  }

  public void associateQuote(String userKey, String quoteKey) {
    quoteRegistry.register(userKey, quoteKey);
  }

  public void associatePolicy(String quoteKey, String policyKey) {
    String userKey = quoteRegistry.getUserKey(quoteKey);
    policyRegistry.register(userKey, policyKey);
  }
}
