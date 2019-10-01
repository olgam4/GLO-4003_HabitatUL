package ca.ulaval.glo4003.gateway.persistence.user;

import ca.ulaval.glo4003.gateway.domain.user.User;
import ca.ulaval.glo4003.gateway.domain.user.UserId;
import ca.ulaval.glo4003.gateway.domain.user.UserRepository;
import ca.ulaval.glo4003.gateway.domain.user.exception.UserAlreadyPersistedException;
import ca.ulaval.glo4003.gateway.domain.user.exception.UserNotFoundException;

import java.util.HashMap;
import java.util.Map;

public class InMemoryUserRepository implements UserRepository {
  private final Map<UserId, User> users = new HashMap<>();

  @Override
  public User getById(UserId userId) {
    if (isExistingUser(userId)) return users.get(userId);

    throw new UserNotFoundException(userId);
  }

  @Override
  public void create(User user) {
    UserId userId = user.getUserId();

    if (isExistingUser(userId)) throw new UserAlreadyPersistedException(userId);

    users.put(userId, user);
  }

  private boolean isExistingUser(UserId userId) {
    return users.containsKey(userId);
  }
}
