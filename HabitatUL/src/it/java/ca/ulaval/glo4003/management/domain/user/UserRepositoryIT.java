package ca.ulaval.glo4003.management.domain.user;

import ca.ulaval.glo4003.generator.user.UserGenerator;
import ca.ulaval.glo4003.management.domain.user.exception.UserAlreadyPersistedException;
import ca.ulaval.glo4003.management.domain.user.exception.UserNotFoundException;
import com.github.javafaker.Faker;
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
    user = UserGenerator.createUser();
    userId = user.getUserId();
  }

  @Test(expected = UserNotFoundException.class)
  public void gettingUserById_withUnknownUserId_shouldThrow() {
    subject.create(user);

    subject.getById(new UserId());
  }

  @Test(expected = UserNotFoundException.class)
  public void gettingUserByUsername_withUnknownUsername_shouldThrow() {
    subject.create(user);

    subject.getByUsername(Faker.instance().name().username());
  }

  @Test
  public void creatingUser_shouldPersistUserAsIs() {
    subject.create(user);

    assertThat(subject.getById(userId), matchesUser(user));
  }

  @Test
  public void createdUsers_canBeFoundByUsername() {
    subject.create(user);

    assertThat(subject.getByUsername(user.getUsername()), matchesUser(user));
  }

  @Test(expected = UserAlreadyPersistedException.class)
  public void creatingUser_withAlreadyPersistedUserId_shouldThrow() {
    subject.create(user);

    subject.create(new User(userId, user.getUsername()));
  }

  @Test(expected = UserAlreadyPersistedException.class)
  public void creatingUser_withAlreadyPersistedUsername_shouldThrow() {
    subject.create(user);

    subject.create(new User(new UserId(), user.getUsername()));
  }

  protected abstract UserRepository createSubject();
}
