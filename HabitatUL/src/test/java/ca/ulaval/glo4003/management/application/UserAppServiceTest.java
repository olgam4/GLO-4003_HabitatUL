package ca.ulaval.glo4003.management.application;

import ca.ulaval.glo4003.generator.user.UserGenerator;
import ca.ulaval.glo4003.management.application.user.UserAppService;
import ca.ulaval.glo4003.management.application.user.UserAssembler;
import ca.ulaval.glo4003.management.application.user.dto.UserDto;
import ca.ulaval.glo4003.management.application.user.exception.InvalidCredentialsException;
import ca.ulaval.glo4003.management.domain.user.User;
import ca.ulaval.glo4003.management.domain.user.UserFactory;
import ca.ulaval.glo4003.management.domain.user.UserId;
import ca.ulaval.glo4003.management.domain.user.UserRepository;
import ca.ulaval.glo4003.management.domain.user.credential.Credentials;
import ca.ulaval.glo4003.management.domain.user.credential.PasswordValidator;
import ca.ulaval.glo4003.management.domain.user.token.Token;
import ca.ulaval.glo4003.management.domain.user.token.TokenTranslator;
import ca.ulaval.glo4003.management.domain.user.token.TokenUser;
import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static ca.ulaval.glo4003.matcher.user.UserMatcher.matchesUserDto;
import static ca.ulaval.glo4003.matcher.user.UserMatcher.mockitoMatchesCredentials;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserAppServiceTest {
  private static final UserId USER_ID = new UserId();
  private static final Credentials CREDENTIALS = UserGenerator.createCredentials();
  private static final Token A_TOKEN = new Token(Faker.instance().internet().uuid());
  private static final TokenUser TOKEN_USER = new TokenUser(USER_ID, CREDENTIALS.getUsername());

  @Mock private User user;
  @Mock private UserFactory userFactory;
  @Mock private PasswordValidator passwordValidator;
  @Mock private TokenTranslator tokenTranslator;
  @Mock private UserRepository userRepository;

  private UserAppService subject;
  private UserAssembler userAssembler;

  @Before
  public void setUp() {
    userAssembler = new UserAssembler();

    when(user.getUserId()).thenReturn(USER_ID);
    when(user.getUsername()).thenReturn(CREDENTIALS.getUsername());
    when(userFactory.create(any())).thenReturn(user);
    when(userRepository.getByUsername(anyString())).thenReturn(user);
    when(passwordValidator.validatePassword(
            USER_ID.getValue().toString(), CREDENTIALS.getPassword()))
        .thenReturn(true);
    when(tokenTranslator.encodeToken(any(TokenUser.class))).thenReturn(A_TOKEN);

    subject =
        new UserAppService(
            userFactory, userRepository, passwordValidator, tokenTranslator, userAssembler);
  }

  @Test
  public void creatingUser_shouldRegisterPassword() {
    subject.createUser(CREDENTIALS);

    String userKey = USER_ID.getValue().toString();
    verify(passwordValidator).registerPassword(userKey, CREDENTIALS.getPassword());
  }

  @Test
  public void creatingUser_shouldCreateUser() {
    subject.createUser(CREDENTIALS);

    verify(userRepository).create(mockitoMatchesCredentials(CREDENTIALS));
  }

  @Test
  public void creatingUser_shouldProduceCorrespondingUserDto() {
    UserDto userDto = subject.createUser(CREDENTIALS);

    assertThat(user, matchesUserDto(userDto));
  }

  @Test(expected = InvalidCredentialsException.class)
  public void authenticatingUser_withInvalidCredentials_shouldThrow() {
    when(passwordValidator.validatePassword(any(), any())).thenReturn(false);

    subject.authenticateUser(UserGenerator.createCredentials());
  }

  @Test
  public void authenticatingUser_withValidCredentials_shouldGenerateToken() {
    subject.authenticateUser(CREDENTIALS);

    verify(tokenTranslator).encodeToken(TOKEN_USER);
  }

  @Test
  public void authenticatingUser_withValidCredentials_shouldProvideGeneratedToken() {
    Token token = subject.authenticateUser(CREDENTIALS);

    assertEquals(token, A_TOKEN);
  }
}
