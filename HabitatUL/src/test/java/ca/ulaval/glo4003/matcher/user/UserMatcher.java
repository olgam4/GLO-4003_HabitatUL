package ca.ulaval.glo4003.matcher.user;

import ca.ulaval.glo4003.gateway.domain.user.User;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class UserMatcher extends TypeSafeMatcher<User> {
  private final User user;

  private UserMatcher(final User user) {
    this.user = user;
  }

  public static Matcher<User> matchesUser(final User user) {
    return new UserMatcher(user);
  }

  @Override
  public void describeTo(final Description description) {
    description.appendText(String.format("matches corresponding user: %s", user.toString()));
  }

  @Override
  public boolean matchesSafely(final User user) {
    return user.getUserId() == this.user.getUserId()
        && user.getUsername() == this.user.getUsername();
  }
}
