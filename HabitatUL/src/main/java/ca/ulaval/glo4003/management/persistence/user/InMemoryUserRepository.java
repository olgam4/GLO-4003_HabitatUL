package ca.ulaval.glo4003.management.persistence.user;

import ca.ulaval.glo4003.management.domain.user.User;
import ca.ulaval.glo4003.management.domain.user.UserId;
import ca.ulaval.glo4003.management.domain.user.UserRepository;
import ca.ulaval.glo4003.management.domain.user.exception.UserAlreadyPersistedException;
import ca.ulaval.glo4003.management.domain.user.exception.UserNotFoundException;

import java.util.HashMap;
import java.util.Map;

public class InMemoryUserRepository implements UserRepository {
  private final Map<UserId, User> usersByUserId = new HashMap<>();
  private final Map<String, User> usersByUserName = new HashMap<>();

  @Override
  public User getById(UserId userId) {
    if (isExistingUser(userId)) return usersByUserId.get(userId);
    throw new UserNotFoundException(userId);
  }

  @Override
  public User getByUsername(String username) {
    if (isExistingUser(username)) return usersByUserName.get(username);
    throw new UserNotFoundException(username);
  }

  @Override
  public void create(User user) {
    UserId userId = user.getUserId();
    String username = user.getUsername();

    if (isExistingUser(userId)) throw new UserAlreadyPersistedException(userId);
    if (isExistingUser(username)) throw new UserAlreadyPersistedException(username);

    usersByUserId.put(userId, user);
    usersByUserName.put(username, user);
  }

  private boolean isExistingUser(UserId userId) {
    return usersByUserId.containsKey(userId);
  }

  private boolean isExistingUser(String username) {
    return usersByUserName.containsKey(username);
  }
}
