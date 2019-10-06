package ca.ulaval.glo4003.management.application.user;

import ca.ulaval.glo4003.generator.MoneyGenerator;
import ca.ulaval.glo4003.generator.user.CredentialsGenerator;
import ca.ulaval.glo4003.generator.user.TokenGenerator;
import ca.ulaval.glo4003.generator.user.TokenPayloadGenerator;
import ca.ulaval.glo4003.management.application.user.exception.InvalidCredentialsException;
import ca.ulaval.glo4003.management.domain.user.*;
import ca.ulaval.glo4003.management.domain.user.credential.Credentials;
import ca.ulaval.glo4003.management.domain.user.credential.PasswordValidator;
import ca.ulaval.glo4003.management.domain.user.exception.KeyNotFoundException;
import ca.ulaval.glo4003.management.domain.user.exception.UnauthorizedException;
import ca.ulaval.glo4003.management.domain.user.token.Token;
import ca.ulaval.glo4003.management.domain.user.token.TokenPayload;
import ca.ulaval.glo4003.management.domain.user.token.TokenTranslator;
import ca.ulaval.glo4003.management.domain.user.token.TokenValidityPeriodProvider;
import ca.ulaval.glo4003.shared.domain.ClockProvider;
import ca.ulaval.glo4003.shared.domain.Money;
import ca.ulaval.glo4003.shared.infrastructure.FixedClockProvider;
import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserAppServiceTest {
  private static final String USER_KEY = Faker.instance().internet().uuid();
  private static final String QUOTE_KEY = Faker.instance().rickAndMorty().quote();
  private static final String POLICY_KEY = Faker.instance().rickAndMorty().quote();
  private static final Token TOKEN = TokenGenerator.createToken();
  private static final TokenPayload TOKEN_PAYLAOD = TokenPayloadGenerator.createValidTokenPayload();
  private static final Money PAYMENT = MoneyGenerator.create();
  private static final Duration VALIDITY_PERIOD = Duration.of(1, ChronoUnit.MINUTES);

  @Mock UsernameRegistry usernameRegistry;
  @Mock UserKeyGenerator userKeyGenerator;
  @Mock PasswordValidator passwordValidator;
  @Mock TokenTranslator tokenTranslator;
  @Mock TokenValidityPeriodProvider tokenValidityPeriodProvider;
  @Mock TokenRegistry tokenRegistry;
  @Mock QuoteRegistry quoteRegistry;
  @Mock PolicyRegistry policyRegistry;
  @Mock PaymentProcessor paymentProcessor;

  private UserAppService subject;
  private ClockProvider clockProvider;
  private Credentials credentials = CredentialsGenerator.createCredentials();

  @Before
  public void setUp() {
    clockProvider = new FixedClockProvider();
    when(userKeyGenerator.generateUserKey()).thenReturn(USER_KEY);
    when(usernameRegistry.getUserKey(any())).thenReturn(USER_KEY);
    when(quoteRegistry.getUserKey(any())).thenReturn(USER_KEY);
    when(passwordValidator.validatePassword(any(), any())).thenReturn(true);
    when(tokenTranslator.encodeToken(any())).thenReturn(TOKEN);
    when(tokenRegistry.getToken(any())).thenReturn(TOKEN.getValue());
    when(tokenValidityPeriodProvider.getTokenValidityPeriod()).thenReturn(VALIDITY_PERIOD);
    createSubject();
  }

  @Test
  public void creatingUser_shouldGenerateUserKey() {
    subject.createUser(credentials);

    verify(userKeyGenerator).generateUserKey();
  }

  @Test
  public void creatingUser_shouldRegisterUsername() {
    subject.createUser(credentials);

    verify(usernameRegistry).register(USER_KEY, credentials.getUsername());
  }

  @Test
  public void creatingUser_shouldRegisterPassword() {
    subject.createUser(credentials);

    verify(passwordValidator).registerPassword(USER_KEY, credentials.getPassword());
  }

  @Test
  public void creatingUser_shouldReturnGeneratedUserKey() {
    String userKey = subject.createUser(credentials);

    assertEquals(USER_KEY, userKey);
  }

  @Test
  public void authenticatingUser_shouldGetUserKeyFromUsernameRegistry() {
    subject.authenticateUser(credentials);

    verify(usernameRegistry).getUserKey(credentials.getUsername());
  }

  @Test
  public void authenticatingUser_shouldValidatePassword() {
    subject.authenticateUser(credentials);

    verify(passwordValidator).validatePassword(USER_KEY, credentials.getPassword());
  }

  @Test
  public void authenticatingUser_shouldEncodeToken() {
    subject.authenticateUser(credentials);

    TokenPayload expectedTokenPayload =
        new TokenPayload(
            USER_KEY,
            credentials.getUsername(),
            Instant.now(clockProvider.getClock()).plus(VALIDITY_PERIOD));
    verify(tokenTranslator).encodeToken(expectedTokenPayload);
  }

  @Test
  public void authenticatingUser_shouldRegisterToken() {
    subject.authenticateUser(credentials);

    verify(tokenRegistry).register(USER_KEY, TOKEN.getValue());
  }

  @Test(expected = InvalidCredentialsException.class)
  public void authenticatingUser_withInvalidCredentials_shouldThrow() {
    when(passwordValidator.validatePassword(any(), any())).thenReturn(false);

    subject.authenticateUser(credentials);
  }

  @Test
  public void controllingAccess_withValidTokenPayload_shouldNotThrow() {
    subject.controlAccess(TOKEN_PAYLAOD);
  }

  @Test(expected = UnauthorizedException.class)
  public void controllingAccess_withExpiredTokenPayload_shouldThrow() {
    TokenPayload expiredTokenPayload = TokenPayloadGenerator.createExpiredTokenPayload();

    subject.controlAccess(expiredTokenPayload);
  }

  @Test(expected = UnauthorizedException.class)
  public void controllingAccess_withUnregisteredToken_shouldThrow() {
    when(tokenRegistry.getToken(TOKEN_PAYLAOD.getUserKey())).thenThrow(KeyNotFoundException.class);

    subject.controlAccess(TOKEN_PAYLAOD);
  }

  @Test
  public void associatingQuote_shouldRegisterQuoteKey() {
    subject.associateQuote(USER_KEY, QUOTE_KEY);

    verify(quoteRegistry).register(USER_KEY, QUOTE_KEY);
  }

  @Test
  public void associatingPolicy_shouldRegisterPolicyKey() {
    when(quoteRegistry.getUserKey(QUOTE_KEY)).thenReturn(USER_KEY);

    subject.associatePolicy(QUOTE_KEY, POLICY_KEY);

    verify(policyRegistry).register(USER_KEY, POLICY_KEY);
  }

  @Test
  public void gettingPolicies_shouldDelegatePolicyRegistry() {
    subject.getPolicies(USER_KEY);

    verify(policyRegistry).getPolicyKeys(USER_KEY);
  }

  @Test
  public void processingQuotePayment_shouldGetUserKey() {
    subject.processQuotePayment(QUOTE_KEY, PAYMENT);

    verify(quoteRegistry).getUserKey(QUOTE_KEY);
  }

  @Test
  public void processingQuotePayment_shouldProcessPayment() {
    subject.processQuotePayment(QUOTE_KEY, PAYMENT);

    verify(paymentProcessor).process(USER_KEY, PAYMENT);
  }

  private void createSubject() {
    subject =
        new UserAppService(
            usernameRegistry,
            quoteRegistry,
            policyRegistry,
            passwordValidator,
            tokenTranslator,
            clockProvider,
            tokenValidityPeriodProvider,
            tokenRegistry,
            paymentProcessor,
            userKeyGenerator);
  }
}
