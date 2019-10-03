package ca.ulaval.glo4003.gateway.application.user;

import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.gateway.application.user.dto.UserDto;
import ca.ulaval.glo4003.gateway.application.user.exception.InvalidCredentialsException;
import ca.ulaval.glo4003.gateway.domain.user.User;
import ca.ulaval.glo4003.gateway.domain.user.UserFactory;
import ca.ulaval.glo4003.gateway.domain.user.UserRepository;
import ca.ulaval.glo4003.gateway.domain.user.credential.Credentials;
import ca.ulaval.glo4003.gateway.domain.user.credential.PasswordValidator;
import ca.ulaval.glo4003.gateway.domain.user.token.Token;
import ca.ulaval.glo4003.gateway.domain.user.token.TokenTranslator;
import ca.ulaval.glo4003.gateway.domain.user.token.TokenUser;

public class UserAppService {
  private UserFactory userFactory;
  private UserRepository userRepository;
  private PasswordValidator passwordValidator;
  private TokenTranslator tokenTranslator;
  private UserAssembler userAssembler;

  public UserAppService() {
    this(
        new UserFactory(),
        ServiceLocator.resolve(UserRepository.class),
        ServiceLocator.resolve(PasswordValidator.class),
        ServiceLocator.resolve(TokenTranslator.class),
        new UserAssembler());
  }

  public UserAppService(
      UserFactory userFactory,
      UserRepository userRepository,
      PasswordValidator passwordValidator,
      TokenTranslator tokenTranslator,
      UserAssembler userAssembler) {
    this.userFactory = userFactory;
    this.passwordValidator = passwordValidator;
    this.userRepository = userRepository;
    this.tokenTranslator = tokenTranslator;
    this.userAssembler = userAssembler;
  }

  public UserDto createUser(Credentials credentials) {
    User user = userFactory.create(credentials.getUsername());
    String userKey = user.getUserId().getValue().toString();
    passwordValidator.registerPassword(userKey, credentials.getPassword());
    userRepository.create(user);
    return userAssembler.from(user);
  }

  public Token authenticateUser(Credentials credentials) {
    User user = userRepository.getByUsername(credentials.getUsername());
    String userKey = user.getUserId().getValue().toString();
    validateCredentials(userKey, credentials);
    return tokenTranslator.encodeToken(new TokenUser(user.getUserId(), credentials.getUsername()));
  }

  private void validateCredentials(String userKey, Credentials credentials) {
    if (!passwordValidator.validatePassword(userKey, credentials.getPassword())) {
      throw new InvalidCredentialsException();
    }
  }
}