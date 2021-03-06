package ca.ulaval.glo4003.administration.application.user;

import ca.ulaval.glo4003.administration.application.user.error.CouldNotAuthenticateUserError;
import ca.ulaval.glo4003.administration.application.user.error.CouldNotCreateUserError;
import ca.ulaval.glo4003.administration.application.user.error.InvalidCredentialsError;
import ca.ulaval.glo4003.administration.domain.user.*;
import ca.ulaval.glo4003.administration.domain.user.credential.Credentials;
import ca.ulaval.glo4003.administration.domain.user.credential.PasswordManager;
import ca.ulaval.glo4003.administration.domain.user.credential.exception.InvalidCredentialsException;
import ca.ulaval.glo4003.administration.domain.user.credential.exception.InvalidPasswordException;
import ca.ulaval.glo4003.administration.domain.user.error.UnauthorizedError;
import ca.ulaval.glo4003.administration.domain.user.exception.KeyAlreadyExistException;
import ca.ulaval.glo4003.administration.domain.user.exception.KeyNotFoundException;
import ca.ulaval.glo4003.administration.domain.user.exception.PaymentFailedException;
import ca.ulaval.glo4003.administration.domain.user.token.Token;
import ca.ulaval.glo4003.administration.domain.user.token.TokenPayload;
import ca.ulaval.glo4003.administration.domain.user.token.TokenTranslator;
import ca.ulaval.glo4003.administration.domain.user.token.TokenValidityPeriodProvider;
import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.shared.application.logging.Logger;
import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;

import java.time.Instant;
import java.util.List;

public class UserAppServiceImpl implements UserAppService {
  private UsernameRegistry usernameRegistry;
  private QuoteRegistry quoteRegistry;
  private PolicyRegistry policyRegistry;
  private PasswordManager passwordManager;
  private TokenTranslator tokenTranslator;
  private ClockProvider clockProvider;
  private TokenValidityPeriodProvider tokenValidityPeriodProvider;
  private TokenRegistry tokenRegistry;
  private PaymentProcessor paymentProcessor;
  private UserKeyGenerator userKeyGenerator;
  private Logger logger;

  public UserAppServiceImpl() {
    this(
        ServiceLocator.resolve(UsernameRegistry.class),
        ServiceLocator.resolve(QuoteRegistry.class),
        ServiceLocator.resolve(PolicyRegistry.class),
        ServiceLocator.resolve(PasswordManager.class),
        ServiceLocator.resolve(TokenTranslator.class),
        ServiceLocator.resolve(ClockProvider.class),
        ServiceLocator.resolve(TokenValidityPeriodProvider.class),
        ServiceLocator.resolve(TokenRegistry.class),
        ServiceLocator.resolve(PaymentProcessor.class),
        new UserKeyGenerator(),
        ServiceLocator.resolve(Logger.class));
  }

  public UserAppServiceImpl(
      UsernameRegistry usernameRegistry,
      QuoteRegistry quoteRegistry,
      PolicyRegistry policyRegistry,
      PasswordManager passwordManager,
      TokenTranslator tokenTranslator,
      ClockProvider clockProvider,
      TokenValidityPeriodProvider tokenValidityPeriodProvider,
      TokenRegistry tokenRegistry,
      PaymentProcessor paymentProcessor,
      UserKeyGenerator userKeyGenerator,
      Logger logger) {
    this.usernameRegistry = usernameRegistry;
    this.quoteRegistry = quoteRegistry;
    this.policyRegistry = policyRegistry;
    this.passwordManager = passwordManager;
    this.tokenTranslator = tokenTranslator;
    this.clockProvider = clockProvider;
    this.tokenValidityPeriodProvider = tokenValidityPeriodProvider;
    this.tokenRegistry = tokenRegistry;
    this.paymentProcessor = paymentProcessor;
    this.userKeyGenerator = userKeyGenerator;
    this.logger = logger;
  }

  public String createUser(Credentials credentials) {
    try {
      String userKey = userKeyGenerator.generateUserKey();
      usernameRegistry.register(userKey, credentials.getUsername());
      passwordManager.registerPassword(userKey, credentials.getPassword());
      return userKey;
    } catch (KeyAlreadyExistException | InvalidPasswordException e) {
      throw new CouldNotCreateUserError(e);
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
      throw new CouldNotAuthenticateUserError(e);
    }
  }

  private void validateCredentials(String userKey, String password) {
    try {
      passwordManager.validatePassword(userKey, password);
    } catch (InvalidCredentialsException e) {
      throw new InvalidCredentialsError();
    }
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
      logger.severe(e.toString());
    }
  }

  public void processQuotePayment(String quoteKey, Money payment) {
    try {
      String userKey = quoteRegistry.getUserKey(quoteKey);
      paymentProcessor.process(userKey, payment);
    } catch (KeyNotFoundException | PaymentFailedException e) {
      logger.severe(e.toString());
    }
  }

  @Override
  public void processPolicyModificationPayment(String policyKey, Money payment) {
    try {
      String userKey = policyRegistry.getUserKey(policyKey);
      paymentProcessor.process(userKey, payment);
    } catch (KeyNotFoundException | PaymentFailedException e) {
      logger.severe(e.toString());
    }
  }

  public void processPolicyRenewalPayment(String policyKey, Money payment) {
    try {
      String userKey = policyRegistry.getUserKey(policyKey);
      paymentProcessor.process(userKey, payment);
    } catch (KeyNotFoundException | PaymentFailedException e) {
      logger.severe(e.toString());
    }
  }

  public List<String> getPolicies(String userKey) {
    return policyRegistry.getPolicyKeys(userKey);
  }
}
