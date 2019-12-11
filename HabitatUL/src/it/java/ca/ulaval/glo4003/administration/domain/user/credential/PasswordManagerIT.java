package ca.ulaval.glo4003.administration.domain.user.credential;

import ca.ulaval.glo4003.administration.domain.user.credential.exception.InvalidCredentialsException;
import ca.ulaval.glo4003.administration.domain.user.credential.exception.InvalidPasswordException;
import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;

public abstract class PasswordManagerIT {
  private static final String USER_KEY = Faker.instance().name().username();
  private static final String PASSWORD = Faker.instance().internet().password();
  private static final String INVALID_PASSWORD = Faker.instance().internet().uuid();

  private PasswordManager subject;

  @Before
  public void setUp() {
    subject = createSubject();
  }

  @Test(expected = InvalidPasswordException.class)
  public void registeringPassword_withoutPassword_shouldThrow() throws InvalidPasswordException {
    subject.registerPassword(USER_KEY, null);
  }

  @Test(expected = InvalidCredentialsException.class)
  public void validatingPassword_withoutPassword_shouldNotValidate()
      throws InvalidPasswordException, InvalidCredentialsException {
    subject.registerPassword(USER_KEY, PASSWORD);

    subject.validatePassword(USER_KEY, null);
  }

  @Test(expected = InvalidCredentialsException.class)
  public void validatingPassword_withUnregisteredUserKey_shouldNotValidate()
      throws InvalidCredentialsException {
    subject.validatePassword(USER_KEY, PASSWORD);
  }

  @Test
  public void validatingPassword_withRegisteredUserKeyAndValidPassword_shouldValidate()
      throws InvalidPasswordException, InvalidCredentialsException {
    subject.registerPassword(USER_KEY, PASSWORD);

    subject.validatePassword(USER_KEY, PASSWORD);
  }

  @Test(expected = InvalidCredentialsException.class)
  public void validatingPassword_withRegisteredUserKeyAndInvalidPassword_shouldNotValidate()
      throws InvalidPasswordException, InvalidCredentialsException {
    subject.registerPassword(USER_KEY, PASSWORD);

    subject.validatePassword(USER_KEY, INVALID_PASSWORD);
  }

  protected abstract PasswordManager createSubject();
}
