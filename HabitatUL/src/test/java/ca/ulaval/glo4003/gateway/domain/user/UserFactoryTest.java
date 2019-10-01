package ca.ulaval.glo4003.gateway.domain.user;

import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UserFactoryTest {
  private static final String A_USERNAME = Faker.instance().name().username();
  private static final String A_PASSWORD = Faker.instance().internet().password();

  @Mock private PasswordValidator passwordValidator;

  private UserFactory subject;

  @Before
  public void setUp() {
    subject = new UserFactory(passwordValidator);
  }

  @Test
  public void creatingUser_shouldRegisterPassword() {
    User user = subject.create(A_USERNAME, A_PASSWORD);

    String userKey = user.getUserId().getValue().toString();
    verify(passwordValidator).registerPassword(userKey, A_PASSWORD);
  }
}
