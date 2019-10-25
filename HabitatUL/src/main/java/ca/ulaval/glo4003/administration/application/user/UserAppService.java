package ca.ulaval.glo4003.administration.application.user;

import ca.ulaval.glo4003.administration.application.user.error.CouldNotAuthenticateUserError;
import ca.ulaval.glo4003.administration.application.user.error.CouldNotCreateUserError;
import ca.ulaval.glo4003.administration.application.user.error.InvalidCredentialsError;
import ca.ulaval.glo4003.administration.domain.user.*;
import ca.ulaval.glo4003.administration.domain.user.credential.Credentials;
import ca.ulaval.glo4003.administration.domain.user.credential.InvalidPasswordException;
import ca.ulaval.glo4003.administration.domain.user.credential.PasswordValidator;
import ca.ulaval.glo4003.administration.domain.user.error.UnauthorizedError;
import ca.ulaval.glo4003.administration.domain.user.exception.KeyAlreadyExistException;
import ca.ulaval.glo4003.administration.domain.user.exception.KeyNotFoundException;
import ca.ulaval.glo4003.administration.domain.user.token.Token;
import ca.ulaval.glo4003.administration.domain.user.token.TokenPayload;
import ca.ulaval.glo4003.administration.domain.user.token.TokenTranslator;
import ca.ulaval.glo4003.administration.domain.user.token.TokenValidityPeriodProvider;
import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;

import java.time.Instant;
import java.util.List;

public class UserAppService implements AccessController {
  private UsernameRegistry usernameRegistry;
  private QuoteRegistry quoteRegistry;
  private PolicyRegistry policyRegistry;
  private PasswordValidator passwordValidator;
  private TokenTranslator tokenTranslator;
  private ClockProvider clockProvider;
  private TokenValidityPeriodProvider tokenValidityPeriodProvider;
  private TokenRegistry tokenRegistry;
  private PaymentProcessor paymentProcessor;
  private UserKeyGenerator userKeyGenerator;

  public UserAppService() {
    this(
        ServiceLocator.resolve(UsernameRegistry.class),
        ServiceLocator.resolve(QuoteRegistry.class),
        ServiceLocator.resolve(PolicyRegistry.class),
        ServiceLocator.resolve(PasswordValidator.class),
        ServiceLocator.resolve(TokenTranslator.class),
        ServiceLocator.resolve(ClockProvider.class),
        ServiceLocator.resolve(TokenValidityPeriodProvider.class),
        ServiceLocator.resolve(TokenRegistry.class),
        ServiceLocator.resolve(PaymentProcessor.class),
        new UserKeyGenerator());
  }

  public UserAppService(
      UsernameRegistry usernameRegistry,
      QuoteRegistry quoteRegistry,
      PolicyRegistry policyRegistry,
      PasswordValidator passwordValidator,
      TokenTranslator tokenTranslator,
      ClockProvider clockProvider,
      TokenValidityPeriodProvider tokenValidityPeriodProvider,
      TokenRegistry tokenRegistry,
      PaymentProcessor paymentProcessor,
      UserKeyGenerator userKeyGenerator) {
    this.usernameRegistry = usernameRegistry;
    this.quoteRegistry = quoteRegistry;
    this.policyRegistry = policyRegistry;
    this.passwordValidator = passwordValidator;
    this.tokenTranslator = tokenTranslator;
    this.clockProvider = clockProvider;
    this.tokenValidityPeriodProvider = tokenValidityPeriodProvider;
    this.tokenRegistry = tokenRegistry;
    this.paymentProcessor = paymentProcessor;
    this.userKeyGenerator = userKeyGenerator;
  }

  public String createUser(Credentials credentials) {
    try {
      String userKey = userKeyGenerator.generateUserKey();
      usernameRegistry.register(userKey, credentials.getUsername());
      passwordValidator.registerPassword(userKey, credentials.getPassword());
      return userKey;
    } catch (KeyAlreadyExistException | InvalidPasswordException e) {
      throw new CouldNotCreateUserError();
    }
  }

  public Token authenticateUser(Credentials credentials) {
    try {
      String username = credentials.getUsername();
      String userKey = usernameRegistry.getUserKey(username);
      validateCredentials(userKey, credentials.getPassword());
      Token token = createToken(username, userKey);
      tokenRegistry.register(userKey, token.getValue());
      return token;
    } catch (KeyNotFoundException | KeyAlreadyExistException e) {
      throw new CouldNotAuthenticateUserError();
    }
  }

  private void validateCredentials(String userKey, String password) {
    if (isInvalidCredentials(userKey, password)) throw new InvalidCredentialsError();
  }

  private boolean isInvalidCredentials(String userKey, String password) {
    return !passwordValidator.validatePassword(userKey, password);
  }

  private Token createToken(String username, String userKey) {
    Instant tokenExpiration =
        Instant.now(clockProvider.getClock())
            .plus(tokenValidityPeriodProvider.getTokenValidityPeriod());
    TokenPayload tokenPayload = new TokenPayload(userKey, username, tokenExpiration);
    return tokenTranslator.encodeToken(tokenPayload);
  }

  @Override
  public void controlAccess(TokenPayload tokenPayload) {
    checkTokenExpiry(tokenPayload);
    checkTokenRegistration(tokenPayload);
  }

  private void checkTokenExpiry(TokenPayload tokenPayload) {
    if (Instant.now(clockProvider.getClock()).isAfter(tokenPayload.getExpiration())) {
      throw new UnauthorizedError();
    }
  }

  private void checkTokenRegistration(TokenPayload tokenPayload) {
    try {
      tokenRegistry.getToken(tokenPayload.getUserKey());
    } catch (KeyNotFoundException e) {
      throw new UnauthorizedError();
    }
  }

  public void associateQuote(String userKey, String quoteKey) {
    quoteRegistry.register(userKey, quoteKey);
  }

  public void associatePolicy(String quoteKey, String policyKey) {
    try {
      String userKey = quoteRegistry.getUserKey(quoteKey);
      policyRegistry.register(userKey, policyKey);
    } catch (KeyNotFoundException e) {
      // TODO: log event
      // TODO: put in a queue for later reprocessing
      e.printStackTrace();
    }
  }

  public List<String> getPolicies(String userKey) {
    return policyRegistry.getPolicyKeys(userKey);
  }

  public void processQuotePayment(String quoteKey, Money payment) {
    try {
      String userKey = quoteRegistry.getUserKey(quoteKey);
      paymentProcessor.process(userKey, payment);
    } catch (KeyNotFoundException e) {
      // TODO: log event
      // TODO: put in a queue for later reprocessing
      e.printStackTrace();
    }
  }
}
