package ca.ulaval.glo4003.gateway.presentation.administration.user;

import ca.ulaval.glo4003.administration.application.user.UserAppService;
import ca.ulaval.glo4003.administration.domain.user.credential.Credentials;
import ca.ulaval.glo4003.administration.helper.user.CredentialsGenerator;
import ca.ulaval.glo4003.gateway.presentation.administration.user.request.CredentialsRequest;
import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static ca.ulaval.glo4003.administration.matcher.CredentialsMatcher.matchesCredentials;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.hamcrest.MockitoHamcrest.argThat;

@RunWith(MockitoJUnitRunner.class)
public class UserResourceTest {
  private static final String USER_KEY = Faker.instance().internet().uuid();

  @Mock private UserAppService userAppService;

  private UserResource subject;
  private CredentialsRequest credentialsRequest;
  private UserViewAssembler userViewAssembler;

  @Before
  public void setUp() {
    credentialsRequest = CredentialsGenerator.createCredentialsRequest();
    userViewAssembler = new UserViewAssembler();
    when(userAppService.createUser(any(Credentials.class))).thenReturn(USER_KEY);
    subject = new UserResource(userAppService, userViewAssembler);
  }

  @Test
  public void creatingUser_shouldDelegateToUserAppService() {
    subject.createUser(credentialsRequest);

    verify(userAppService).createUser(argThat(matchesCredentials(credentialsRequest)));
  }

  @Test
  public void authenticatingUser_shouldDelegateToUserAppService() {
    subject.authenticateUser(credentialsRequest);

    verify(userAppService).authenticateUser(argThat(matchesCredentials(credentialsRequest)));
  }
}
