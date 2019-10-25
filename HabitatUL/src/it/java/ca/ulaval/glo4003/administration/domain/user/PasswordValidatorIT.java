package ca.ulaval.glo4003.administration.domain.user;

import ca.ulaval.glo4003.administration.domain.user.credential.InvalidPasswordException;
import ca.ulaval.glo4003.administration.domain.user.credential.PasswordValidator;
import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public abstract class PasswordValidatorIT {
  private static final String USER_KEY = Faker.instance().name().username();
  private static final String PASSWORD = Faker.instance().internet().password();
  private static final String INVALID_PASSWORD = Faker.instance().internet().uuid();

  private PasswordValidator subject;

  @Before
  public void setUp() {
    subject = createSubject();
  }

  @Test(expected = InvalidPasswordException.class)
  public void registeringPassword_withoutPassword_shouldThrow() throws InvalidPasswordException {
    subject.registerPassword(USER_KEY, null);
  }

  @Test
  public void validatingPassword_withoutPassword_shouldNotValidate() throws InvalidPasswordException {
    subject.registerPassword(USER_KEY, PASSWORD);

    assertFalse(subject.validatePassword(USER_KEY, null));
  }

  @Test
  public void validatingPassword_withUnregisteredUserKey_shouldNotValidate() {
    assertFalse(subject.validatePassword(USER_KEY, PASSWORD));
  }

  @Test
  public void validatingPassword_withRegisteredUserKeyAndValidPassword_shouldValidate()
      throws InvalidPasswordException {
    subject.registerPassword(USER_KEY, PASSWORD);

    assertTrue(subject.validatePassword(USER_KEY, PASSWORD));
  }

  @Test
  public void validatingPassword_withRegisteredUserKeyAndInvalidPassword_shouldNotValidate()
      throws InvalidPasswordException {
    subject.registerPassword(USER_KEY, PASSWORD);

    assertFalse(subject.validatePassword(USER_KEY, INVALID_PASSWORD));
  }

  protected abstract PasswordValidator createSubject();
}
