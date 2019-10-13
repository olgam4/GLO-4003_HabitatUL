package ca.ulaval.glo4003.gateway.presentation.user;

import ca.ulaval.glo4003.administration.application.user.UserAppService;
import ca.ulaval.glo4003.administration.domain.user.credential.Credentials;
import ca.ulaval.glo4003.generator.user.CredentialsGenerator;
import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserResourceTest {
  private static final Credentials CREDENTIALS = CredentialsGenerator.createCredentials();
  private static final String USER_KEY = Faker.instance().internet().uuid();

  @Mock private UserAppService userAppService;

  private UserResource subject;
  private UserViewAssembler userViewAssembler;

  @Before
  public void setUp() {
    userViewAssembler = new UserViewAssembler();
    when(userAppService.createUser(CREDENTIALS)).thenReturn(USER_KEY);
    subject = new UserResource(userAppService, userViewAssembler);
  }

  @Test
  public void creatingUser_shouldDelegateToUserAppService() {
    subject.createUser(CREDENTIALS);

    verify(userAppService).createUser(CREDENTIALS);
  }

  @Test
  public void authenticatingUser_shouldDelegateToUserAppService() {
    subject.authenticateUser(CREDENTIALS);

    verify(userAppService).authenticateUser(CREDENTIALS);
  }
}
