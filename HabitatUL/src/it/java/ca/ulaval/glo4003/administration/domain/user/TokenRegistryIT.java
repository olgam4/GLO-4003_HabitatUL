package ca.ulaval.glo4003.administration.domain.user;

import ca.ulaval.glo4003.administration.domain.user.error.KeyAlreadyExistError;
import ca.ulaval.glo4003.administration.domain.user.error.KeyNotFoundError;
import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public abstract class TokenRegistryIT {
  private static final String USER_KEY = Faker.instance().book().author();
  private static final String ANOTHER_USER_KEY = Faker.instance().ancient().god();
  private static final String NOT_EXISTING_USER_KEY = Faker.instance().dog().breed();
  private static final String TOKEN = Faker.instance().cat().name();
  private static final String ANOTHER_TOKEN = Faker.instance().color().name();
  private static final String NOT_EXISTING_TOKEN = Faker.instance().dragonBall().character();

  private TokenRegistry subject;

  @Before
  public void setUp() {
    subject = createSubject();
    subject.register(USER_KEY, TOKEN);
  }

  @Test(expected = KeyAlreadyExistError.class)
  public void registeringToken_withAlreadyExistingUserKey_shouldThrow() {
    subject.register(USER_KEY, ANOTHER_TOKEN);
  }

  @Test(expected = KeyAlreadyExistError.class)
  public void registeringToken_withAlreadyExistingToken_shouldThrow() {
    subject.register(ANOTHER_USER_KEY, TOKEN);
  }

  @Test
  public void gettingUserKey_withRegisteredToken_shouldReturnMappedUserKey() {
    assertEquals(USER_KEY, subject.getUserKey(TOKEN));
  }

  @Test(expected = KeyNotFoundError.class)
  public void gettingUserKey_withNotExistingToken_shouldThrow() {
    subject.getUserKey(NOT_EXISTING_TOKEN);
  }

  @Test
  public void gettingToken_withRegisteredUserKey_shouldReturnMappedToken() {
    assertEquals(TOKEN, subject.getToken(USER_KEY));
  }

  @Test(expected = KeyNotFoundError.class)
  public void gettingToken_withNotExistingUserKey_shouldThrow() {
    subject.getToken(NOT_EXISTING_USER_KEY);
  }

  protected abstract TokenRegistry createSubject();
}
