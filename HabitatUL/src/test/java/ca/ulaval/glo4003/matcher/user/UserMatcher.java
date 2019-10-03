package ca.ulaval.glo4003.matcher.user;

import ca.ulaval.glo4003.gateway.application.user.dto.UserDto;
import ca.ulaval.glo4003.gateway.domain.user.User;
import ca.ulaval.glo4003.gateway.domain.user.credential.Credentials;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.mockito.hamcrest.MockitoHamcrest;

public class UserMatcher extends TypeSafeMatcher<User> {
  protected Credentials credentials;
  protected UserDto userDto;
  private User user;

  private UserMatcher(final User user) {
    this.user = user;
  }

  private UserMatcher(final Credentials credentials) {
    this.credentials = credentials;
  }

  private UserMatcher(final UserDto userDto) {
    this.userDto = userDto;
  }

  public static Matcher<User> matchesUser(final User user) {
    return new UserMatcher(user);
  }

  public static Matcher<User> matchesUserDto(final UserDto userDto) {
    return new UserMatcher(userDto) {
      @Override
      public boolean matchesSafely(final User user) {
        return user.getUsername().equals(this.userDto.getUsername());
      }
    };
  }

  public static User mockitoMatchesCredentials(final Credentials credentials) {
    return MockitoHamcrest.argThat(matchesCredentials(credentials));
  }

  public static Matcher<User> matchesCredentials(final Credentials credentials) {
    return new UserMatcher(credentials) {
      @Override
      public boolean matchesSafely(final User user) {
        return user.getUsername().equals(this.credentials.getUsername());
      }
    };
  }

  @Override
  public void describeTo(final Description description) {
    description.appendText(String.format("matches corresponding user: %s", user.toString()));
  }

  @Override
  public boolean matchesSafely(final User user) {
    return user.getUserId().equals(this.user.getUserId())
        && user.getUsername().equals(this.user.getUsername());
  }
}
