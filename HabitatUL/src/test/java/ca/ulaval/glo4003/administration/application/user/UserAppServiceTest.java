package ca.ulaval.glo4003.administration.application.user;

import ca.ulaval.glo4003.administration.application.user.error.CouldNotAuthenticateUserError;
import ca.ulaval.glo4003.administration.application.user.error.CouldNotCreateUserError;
import ca.ulaval.glo4003.administration.application.user.error.InvalidCredentialsError;
import ca.ulaval.glo4003.administration.domain.user.*;
import ca.ulaval.glo4003.administration.domain.user.credential.Credentials;
import ca.ulaval.glo4003.administration.domain.user.credential.InvalidCredentialsException;
import ca.ulaval.glo4003.administration.domain.user.credential.InvalidPasswordException;
import ca.ulaval.glo4003.administration.domain.user.credential.PasswordManager;
import ca.ulaval.glo4003.administration.domain.user.error.UnauthorizedError;
import ca.ulaval.glo4003.administration.domain.user.exception.KeyAlreadyExistException;
import ca.ulaval.glo4003.administration.domain.user.exception.KeyNotFoundException;
import ca.ulaval.glo4003.administration.domain.user.exception.PaymentFailedException;
import ca.ulaval.glo4003.administration.domain.user.token.Token;
import ca.ulaval.glo4003.administration.domain.user.token.TokenPayload;
import ca.ulaval.glo4003.administration.domain.user.token.TokenTranslator;
import ca.ulaval.glo4003.administration.domain.user.token.TokenValidityPeriodProvider;
import ca.ulaval.glo4003.helper.shared.MoneyGenerator;
import ca.ulaval.glo4003.helper.shared.TemporalGenerator;
import ca.ulaval.glo4003.helper.user.CredentialsGenerator;
import ca.ulaval.glo4003.helper.user.TokenGenerator;
import ca.ulaval.glo4003.helper.user.TokenPayloadGenerator;
import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserAppServiceTest {
  private static final String USER_KEY = Faker.instance().internet().uuid();
  private static final String QUOTE_KEY = Faker.instance().rickAndMorty().quote();
  private static final String POLICY_KEY = Faker.instance().rickAndMorty().quote();
  private static final Token TOKEN = TokenGenerator.createToken();
  private static final TokenPayload TOKEN_PAYLOAD = TokenPayloadGenerator.createValidTokenPayload();
  private static final Money PAYMENT = MoneyGenerator.createMoney();
  private static final Duration VALIDITY_PERIOD = Duration.of(1, ChronoUnit.MINUTES);
  private static final ClockProvider CLOCK_PROVIDER = TemporalGenerator.getClockProvider();
  private static final Credentials CREDENTIALS = CredentialsGenerator.createCredentials();

  @Mock UsernameRegistry usernameRegistry;
  @Mock UserKeyGenerator userKeyGenerator;
  @Mock PasswordManager passwordManager;
  @Mock TokenTranslator tokenTranslator;
  @Mock TokenValidityPeriodProvider tokenValidityPeriodProvider;
  @Mock TokenRegistry tokenRegistry;
  @Mock QuoteRegistry quoteRegistry;
  @Mock PolicyRegistry policyRegistry;
  @Mock PaymentProcessor paymentProcessor;

  private UserAppService subject;

  @Before
  public void setUp() throws KeyNotFoundException {
    when(userKeyGenerator.generateUserKey()).thenReturn(USER_KEY);
    when(usernameRegistry.getUserKey(any())).thenReturn(USER_KEY);
    when(quoteRegistry.getUserKey(any())).thenReturn(USER_KEY);
    when(tokenTranslator.encodeToken(any())).thenReturn(TOKEN);
    when(tokenRegistry.getToken(any())).thenReturn(TOKEN.getValue());
    when(tokenValidityPeriodProvider.getTokenValidityPeriod()).thenReturn(VALIDITY_PERIOD);
    subject =
        new UserAppService(
            usernameRegistry,
            quoteRegistry,
            policyRegistry,
            passwordManager,
            tokenTranslator,
            CLOCK_PROVIDER,
            tokenValidityPeriodProvider,
            tokenRegistry,
            paymentProcessor,
            userKeyGenerator);
  }

  @Test
  public void creatingUser_shouldGenerateUserKey() {
    subject.createUser(CREDENTIALS);

    verify(userKeyGenerator).generateUserKey();
  }

  @Test
  public void creatingUser_shouldRegisterUsername() throws KeyAlreadyExistException {
    subject.createUser(CREDENTIALS);

    verify(usernameRegistry).register(USER_KEY, CREDENTIALS.getUsername());
  }

  @Test
  public void creatingUser_shouldRegisterPassword() throws InvalidPasswordException {
    subject.createUser(CREDENTIALS);

    verify(passwordManager).registerPassword(USER_KEY, CREDENTIALS.getPassword());
  }

  @Test
  public void creatingUser_shouldReturnGeneratedUserKey() {
    String userKey = subject.createUser(CREDENTIALS);

    assertEquals(USER_KEY, userKey);
  }

  @Test(expected = CouldNotCreateUserError.class)
  public void creatingUser_withAlreadyExistingUserKey_shouldThrow()
      throws KeyAlreadyExistException {
    Mockito.doThrow(KeyAlreadyExistException.class)
        .when(usernameRegistry)
        .register(USER_KEY, CREDENTIALS.getUsername());

    subject.createUser(CREDENTIALS);
  }

  @Test(expected = CouldNotCreateUserError.class)
  public void creatingUser_withInvalidPassword_shouldThrow() throws InvalidPasswordException {
    doThrow(InvalidPasswordException.class)
        .when(passwordManager)
        .registerPassword(USER_KEY, CREDENTIALS.getPassword());

    subject.createUser(CREDENTIALS);
  }

  @Test
  public void authenticatingUser_shouldGetUserKeyFromUsernameRegistry()
      throws KeyNotFoundException {
    subject.authenticateUser(CREDENTIALS);

    verify(usernameRegistry).getUserKey(CREDENTIALS.getUsername());
  }

  @Test
  public void authenticatingUser_shouldValidatePassword() throws InvalidCredentialsException {
    subject.authenticateUser(CREDENTIALS);

    verify(passwordManager).validatePassword(USER_KEY, CREDENTIALS.getPassword());
  }

  @Test
  public void authenticatingUser_shouldEncodeToken() {
    subject.authenticateUser(CREDENTIALS);

    TokenPayload expectedTokenPayload =
        new TokenPayload(
            USER_KEY,
            CREDENTIALS.getUsername(),
            Instant.now(CLOCK_PROVIDER.getClock()).plus(VALIDITY_PERIOD));
    verify(tokenTranslator).encodeToken(expectedTokenPayload);
  }

  @Test
  public void authenticatingUser_shouldRegisterToken() throws KeyAlreadyExistException {
    subject.authenticateUser(CREDENTIALS);

    verify(tokenRegistry).register(USER_KEY, TOKEN.getValue());
  }

  @Test(expected = InvalidCredentialsError.class)
  public void authenticatingUser_withInvalidCredentials_shouldThrow()
      throws InvalidCredentialsException {
    Mockito.doThrow(InvalidCredentialsException.class)
        .when(passwordManager)
        .validatePassword(any(), any());

    subject.authenticateUser(CREDENTIALS);
  }

  @Test(expected = CouldNotAuthenticateUserError.class)
  public void authenticatingUser_withNotExistingUsername_shouldThrow() throws KeyNotFoundException {
    when(usernameRegistry.getUserKey(CREDENTIALS.getUsername()))
        .thenThrow(KeyNotFoundException.class);

    subject.authenticateUser(CREDENTIALS);
  }

  @Test(expected = CouldNotAuthenticateUserError.class)
  public void authenticatingUser_withAlreadyAuthenticatedUserKey_shouldThrow()
      throws KeyAlreadyExistException {
    Mockito.doThrow(KeyAlreadyExistException.class)
        .when(tokenRegistry)
        .register(USER_KEY, TOKEN.getValue());

    subject.authenticateUser(CREDENTIALS);
  }

  @Test
  public void controllingAccess_withValidTokenPayload_shouldNotThrow() {
    subject.controlAccess(TOKEN_PAYLOAD);
  }

  @Test(expected = UnauthorizedError.class)
  public void controllingAccess_withExpiredTokenPayload_shouldThrow() {
    TokenPayload expiredTokenPayload = TokenPayloadGenerator.createExpiredTokenPayload();

    subject.controlAccess(expiredTokenPayload);
  }

  @Test(expected = UnauthorizedError.class)
  public void controllingAccess_withUnregisteredToken_shouldThrow() throws KeyNotFoundException {
    when(tokenRegistry.getToken(TOKEN_PAYLOAD.getUserKey())).thenThrow(KeyNotFoundException.class);

    subject.controlAccess(TOKEN_PAYLOAD);
  }

  @Test
  public void associatingQuote_shouldRegisterQuoteKey() {
    subject.associateQuote(USER_KEY, QUOTE_KEY);

    verify(quoteRegistry).register(USER_KEY, QUOTE_KEY);
  }

  @Test
  public void associatingPolicy_shouldRegisterPolicyKey() throws KeyNotFoundException {
    when(quoteRegistry.getUserKey(QUOTE_KEY)).thenReturn(USER_KEY);

    subject.associatePolicy(QUOTE_KEY, POLICY_KEY);

    verify(policyRegistry).register(USER_KEY, POLICY_KEY);
  }

  @Test
  public void gettingPolicies_shouldGetPolicyKeys() {
    subject.getPolicies(USER_KEY);

    verify(policyRegistry).getPolicyKeys(USER_KEY);
  }

  @Test
  public void processingQuotePayment_shouldGetUserKey() throws KeyNotFoundException {
    subject.processQuotePayment(QUOTE_KEY, PAYMENT);

    verify(quoteRegistry).getUserKey(QUOTE_KEY);
  }

  @Test
  public void processingQuotePayment_shouldProcessPayment() throws PaymentFailedException {
    subject.processQuotePayment(QUOTE_KEY, PAYMENT);

    verify(paymentProcessor).process(USER_KEY, PAYMENT);
  }
}
