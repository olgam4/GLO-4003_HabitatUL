package ca.ulaval.glo4003.gateway.domain.user;

public interface UserRepository {
  User getById(UserId userId);

  void create(User user);
}
