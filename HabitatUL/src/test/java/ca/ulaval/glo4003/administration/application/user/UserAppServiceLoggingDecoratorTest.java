package ca.ulaval.glo4003.administration.application.user;

import ca.ulaval.glo4003.administration.domain.user.credential.Credentials;
import ca.ulaval.glo4003.administration.domain.user.token.TokenPayload;
import ca.ulaval.glo4003.helper.shared.MoneyGenerator;
import ca.ulaval.glo4003.helper.user.CredentialsGenerator;
import ca.ulaval.glo4003.helper.user.TokenPayloadGenerator;
import ca.ulaval.glo4003.shared.domain.money.Money;
import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.logging.Logger;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserAppServiceLoggingDecoratorTest {
  private static final String USER_KEY = Faker.instance().internet().uuid();
  private static final String QUOTE_KEY = Faker.instance().rickAndMorty().quote();
  private static final String POLICY_KEY = Faker.instance().rickAndMorty().quote();
  private static final TokenPayload TOKEN_PAYLOAD = TokenPayloadGenerator.createValidTokenPayload();
  private static final Money PAYMENT = MoneyGenerator.createMoney();
  private static final Credentials CREDENTIALS = CredentialsGenerator.createCredentials();

  @Mock private UserAppService userAppService;
  @Mock private Logger logger;

  private UserAppService subject;

  @Before
  public void setUp() {
    subject = new UserAppServiceLoggingDecorator(userAppService, logger);
  }

  @Test
  public void creatingUser_shouldLogParamsAndReturnAsInfo() {
    subject.createUser(CREDENTIALS);

    verify(logger, times(2)).info(anyString());
  }

  @Test
  public void creatingUser_shouldDelegateToUserAppService() {
    subject.createUser(CREDENTIALS);

    verify(userAppService).createUser(CREDENTIALS);
  }

  @Test
  public void authenticatingUser_shouldLogParamsAndReturnAsInfo() {
    subject.authenticateUser(CREDENTIALS);

    verify(logger, times(2)).info(anyString());
  }

  @Test
  public void authenticatingUser_shouldDelegateToUserAppService() {
    subject.authenticateUser(CREDENTIALS);

    verify(userAppService).authenticateUser(CREDENTIALS);
  }

  @Test
  public void controlingAccess_shouldLogParamsAsInfo() {
    subject.controlAccess(TOKEN_PAYLOAD);

    verify(logger).info(anyString());
  }

  @Test
  public void controlingAccess_shouldDelegateToUserAppService() {
    subject.controlAccess(TOKEN_PAYLOAD);

    verify(userAppService).controlAccess(TOKEN_PAYLOAD);
  }

  @Test
  public void associatingQuote_shouldLogParamsAsInfo() {
    subject.associateQuote(USER_KEY, QUOTE_KEY);

    verify(logger).info(anyString());
  }

  @Test
  public void associatingQuote_shouldDelegateToUserAppService() {
    subject.associateQuote(USER_KEY, QUOTE_KEY);

    verify(userAppService).associateQuote(USER_KEY, QUOTE_KEY);
  }

  @Test
  public void associatingPolicy_shouldLogParamsAsInfo() {
    subject.associatePolicy(USER_KEY, POLICY_KEY);

    verify(logger).info(anyString());
  }

  @Test
  public void associatingPolicy_shouldDelegateToUserAppService() {
    subject.associatePolicy(USER_KEY, POLICY_KEY);

    verify(userAppService).associatePolicy(USER_KEY, POLICY_KEY);
  }

  @Test
  public void processingQuotePayment_shouldLogParamsAsInfo() {
    subject.processQuotePayment(QUOTE_KEY, PAYMENT);

    verify(logger).info(anyString());
  }

  @Test
  public void processingQuotePayment_shouldDelegateToUserAppService() {
    subject.processQuotePayment(QUOTE_KEY, PAYMENT);

    verify(userAppService).processQuotePayment(QUOTE_KEY, PAYMENT);
  }

  @Test
  public void gettingPolicies_shouldLogParamsAndReturnAsInfo() {
    subject.getPolicies(USER_KEY);

    verify(logger, times(2)).info(anyString());
  }

  @Test
  public void gettingPolicies_shouldDelegateToUserAppService() {
    subject.getPolicies(USER_KEY);

    verify(userAppService).getPolicies(USER_KEY);
  }
}
