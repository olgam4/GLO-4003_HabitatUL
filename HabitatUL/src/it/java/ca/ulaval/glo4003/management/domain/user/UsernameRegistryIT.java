package ca.ulaval.glo4003.management.domain.user;

import ca.ulaval.glo4003.management.domain.user.exception.KeyNotFoundException;
import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public abstract class UsernameRegistryIT {
  private static final String NOT_EXISTING_USERNAME_KEY = Faker.instance().dragonBall().character();
  private static final String NOT_EXISTING_USER_KEY = Faker.instance().dog().breed();
  private static final String USER_KEY = Faker.instance().book().author();
  private static final String USERNAME_KEY = Faker.instance().cat().name();

  private UsernameRegistry subject;

  @Before
  public void setUp() {
    subject = createSubject();
    subject.register(USER_KEY, USERNAME_KEY);
  }

  @Test
  public void gettingUserKey_withRegisteredUsernameKey_shouldReturnMappedUserKey() {
    assertEquals(USER_KEY, subject.getUserKey(USERNAME_KEY));
  }

  @Test(expected = KeyNotFoundException.class)
  public void gettingUserKey_withNotExistingUsername_shouldThrow() {
    subject.getUserKey(NOT_EXISTING_USERNAME_KEY);
  }

  @Test
  public void gettingUsername_withRegisteredUserKey_shouldReturnMappedUsername() {
    assertEquals(USERNAME_KEY, subject.getUsername(USER_KEY));
  }

  @Test(expected = KeyNotFoundException.class)
  public void gettingUsername_withNotExistingUserKey_shouldThrow() {
    subject.getUsername(NOT_EXISTING_USER_KEY);
  }

  protected abstract UsernameRegistry createSubject();
}
