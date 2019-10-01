package ca.ulaval.glo4003.gateway.domain.user;

import ca.ulaval.glo4003.gateway.domain.user.exception.UserAlreadyPersistedException;
import ca.ulaval.glo4003.gateway.domain.user.exception.UserNotFoundException;
import ca.ulaval.glo4003.generator.user.UserGenerator;
import org.junit.Before;
import org.junit.Test;

import static ca.ulaval.glo4003.matcher.user.UserMatcher.matchesUser;
import static org.junit.Assert.assertThat;

public abstract class UserRepositoryIT {
  private UserRepository subject;
  private User user;
  private UserId userId;

  @Before
  public void setUp() {
    subject = createSubject();
    user = UserGenerator.createValidUser();
    userId = user.getUserId();
  }

  @Test(expected = UserNotFoundException.class)
  public void gettingUserById_withUnknownUserId_shouldThrow() {
    subject.getById(new UserId());
  }

  @Test
  public void creatingUser_shouldPersistUserAsIs() {
    subject.create(user);
    assertThat(subject.getById(userId), matchesUser(user));
  }

  @Test(expected = UserAlreadyPersistedException.class)
  public void creatingUser_withAlreadyPersistedUser_shouldThrow() {
    subject.create(user);
    subject.create(user);
  }

  protected abstract UserRepository createSubject();
}
