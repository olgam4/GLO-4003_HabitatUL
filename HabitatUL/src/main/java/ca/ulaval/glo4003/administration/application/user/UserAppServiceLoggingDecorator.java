package ca.ulaval.glo4003.administration.application.user;

import ca.ulaval.glo4003.administration.domain.user.credential.Credentials;
import ca.ulaval.glo4003.administration.domain.user.token.Token;
import ca.ulaval.glo4003.administration.domain.user.token.TokenPayload;
import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.shared.application.logging.Logger;
import ca.ulaval.glo4003.shared.domain.money.Money;

import java.util.List;

public class UserAppServiceLoggingDecorator implements UserAppService {
  private UserAppService userAppService;
  private Logger logger;

  public UserAppServiceLoggingDecorator(UserAppService userAppService) {
    this(userAppService, ServiceLocator.resolve(Logger.class));
  }

  public UserAppServiceLoggingDecorator(UserAppService userAppService, Logger logger) {
    this.logger = logger;
    this.userAppService = userAppService;
  }

  @Override
  public String createUser(Credentials credentials) {
    logger.info(String.format("Creating User <%s>", credentials));
    String userKey = this.userAppService.createUser(credentials);
    logger.info(String.format("User <%s> created", userKey));
    return userKey;
  }

  @Override
  public Token authenticateUser(Credentials credentials) {
    logger.info(String.format("Authenticating User <%s>", credentials));
    Token token = this.userAppService.authenticateUser(credentials);
    logger.info(String.format("Token <%s> generated", token));
    return token;
  }

  @Override
  public void controlAccess(TokenPayload tokenPayload) {
    logger.info(String.format("Controling access of <%s>", tokenPayload));
    this.userAppService.controlAccess(tokenPayload);
  }

  @Override
  public void associateQuote(String userKey, String quoteKey) {
    logger.info(String.format("Associating Quote <%s> with User <%s>", quoteKey, userKey));
    this.userAppService.associateQuote(userKey, quoteKey);
  }

  @Override
  public void associatePolicy(String quoteKey, String policyKey) {
    logger.info(String.format("Associating Quote <%s> with Policy <%s>", quoteKey, policyKey));
    this.userAppService.associatePolicy(quoteKey, policyKey);
  }

  @Override
  public void processQuotePayment(String quoteKey, Money payment) {
    logger.info(
        String.format(
            "Processing quote payment of Quote <%s> with amount of <%s>", quoteKey, payment));
    this.userAppService.processQuotePayment(quoteKey, payment);
  }

  @Override
  public List<String> getPolicies(String userKey) {
    logger.info(String.format("Geting Policies of User <%s>", userKey));
    List<String> policies = this.userAppService.getPolicies(userKey);
    logger.info(String.format("User Policies: <%s>", String.join(", ", policies)));
    return policies;
  }
}
