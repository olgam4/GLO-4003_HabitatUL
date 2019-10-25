package ca.ulaval.glo4003.administration.domain.user;

import ca.ulaval.glo4003.administration.domain.user.error.KeyAlreadyExistError;
import ca.ulaval.glo4003.administration.domain.user.error.KeyNotFoundError;
import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public abstract class UsernameRegistryIT {
  private static final String USER_KEY = Faker.instance().book().author();
  private static final String ANOTHER_USER_KEY = Faker.instance().ancient().god();
  private static final String NOT_EXISTING_USER_KEY = Faker.instance().dog().breed();
  private static final String USERNAME = Faker.instance().cat().name();
  private static final String ANOTHER_USERNAME = Faker.instance().country().capital();
  private static final String NOT_EXISTING_USERNAME = Faker.instance().dragonBall().character();

  private UsernameRegistry subject;

  @Before
  public void setUp() {
    subject = createSubject();
    subject.register(USER_KEY, USERNAME);
  }

  @Test(expected = KeyAlreadyExistError.class)
  public void registeringUsername_withAlreadyExistingUserKey_shouldThrow() {
    subject.register(USER_KEY, ANOTHER_USERNAME);
  }

  @Test(expected = KeyAlreadyExistError.class)
  public void registeringUsername_withAlreadyExistingUsername_shouldThrow() {
    subject.register(ANOTHER_USER_KEY, USERNAME);
  }

  @Test
  public void gettingUserKey_withRegisteredUsernameKey_shouldReturnMappedUserKey() {
    assertEquals(USER_KEY, subject.getUserKey(USERNAME));
  }

  @Test(expected = KeyNotFoundError.class)
  public void gettingUserKey_withNotExistingUsername_shouldThrow() {
    subject.getUserKey(NOT_EXISTING_USERNAME);
  }

  @Test
  public void gettingUsername_withRegisteredUserKey_shouldReturnMappedUsername() {
    assertEquals(USERNAME, subject.getUsername(USER_KEY));
  }

  @Test(expected = KeyNotFoundError.class)
  public void gettingUsername_withNotExistingUserKey_shouldThrow() {
    subject.getUsername(NOT_EXISTING_USER_KEY);
  }

  protected abstract UsernameRegistry createSubject();
}
