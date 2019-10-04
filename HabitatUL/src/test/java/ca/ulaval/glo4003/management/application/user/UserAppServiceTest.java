package ca.ulaval.glo4003.management.application.user;

import ca.ulaval.glo4003.generator.user.CredentialsGenerator;
import ca.ulaval.glo4003.management.application.user.exception.InvalidCredentialsException;
import ca.ulaval.glo4003.management.domain.PaymentProcessor;
import ca.ulaval.glo4003.management.domain.user.PolicyRegistry;
import ca.ulaval.glo4003.management.domain.user.QuoteRegistry;
import ca.ulaval.glo4003.management.domain.user.UserKeyGenerator;
import ca.ulaval.glo4003.management.domain.user.UsernameRegistry;
import ca.ulaval.glo4003.management.domain.user.credential.Credentials;
import ca.ulaval.glo4003.management.domain.user.credential.PasswordValidator;
import ca.ulaval.glo4003.management.domain.user.token.TokenPayload;
import ca.ulaval.glo4003.management.domain.user.token.TokenTranslator;
import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserAppServiceTest {
  private static final String USER_KEY = Faker.instance().internet().uuid();
  private static final String QUOTE_KEY = Faker.instance().rickAndMorty().quote();
  private static final String POLICY_KEY = Faker.instance().rickAndMorty().quote();

  @Mock UsernameRegistry usernameRegistry;
  @Mock QuoteRegistry quoteRegistry;
  @Mock PolicyRegistry policyRegistry;
  @Mock PasswordValidator passwordValidator;
  @Mock TokenTranslator tokenTranslator;
  @Mock PaymentProcessor paymentProcessor;
  @Mock UserKeyGenerator userKeyGenerator;

  private UserAppService subject;
  private Credentials credentials = CredentialsGenerator.createCredentials();

  @Before
  public void setUp() {
    when(userKeyGenerator.generateUserKey()).thenReturn(USER_KEY);
    subject =
        new UserAppService(
            usernameRegistry,
            quoteRegistry,
            policyRegistry,
            passwordValidator,
            tokenTranslator,
            paymentProcessor,
            userKeyGenerator);
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

  @Test(expected = InvalidCredentialsException.class)
  public void authenticatingUser_withInvalidCredentials_shouldThrow() {
    subject.authenticateUser(credentials);
  }

  @Test
  public void authenticatingUser_withValidCredentials_shouldEncodeToken() {
    when(passwordValidator.validatePassword(anyString(), anyString())).thenReturn(true);
    when(usernameRegistry.getUserKey(anyString())).thenReturn(USER_KEY);

    subject.authenticateUser(credentials);

    verify(tokenTranslator).encodeToken(any(TokenPayload.class));
  }

  @Test
  public void associatingPolicy_shouldRegisterPolicyKey() {
    when(quoteRegistry.getUserKey(QUOTE_KEY)).thenReturn(USER_KEY);

    subject.associatePolicy(QUOTE_KEY, POLICY_KEY);

    verify(policyRegistry).register(USER_KEY, POLICY_KEY);
  }

  @Test
  public void gettingPolicies_shouldDelegate() {
    subject.getPolicies(USER_KEY);

    verify(policyRegistry).getPolicyKeys(USER_KEY);
  }

  @Test
  public void associatingQuote_shouldRegisterKey() {
    subject.associateQuote(USER_KEY, QUOTE_KEY);

    verify(quoteRegistry).register(USER_KEY, QUOTE_KEY);
  }
}
