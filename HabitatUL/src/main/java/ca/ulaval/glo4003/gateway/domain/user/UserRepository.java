package ca.ulaval.glo4003.gateway.domain.user;

public interface UserRepository {
  void create(User user);

  User getById(UserId userId);

  User getByUsername(String username);
}
