package ca.ulaval.glo4003.shared.matcher;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

public class CommonMatcher {
  public static class AlwaysFalseMatcher<T> extends BaseMatcher<T> {
    @Override
    public boolean matches(Object item) {
      return false;
    }

    @Override
    public void describeTo(Description description) {
      String.format("Always false matcher - Something wrong happened");
    }
  }
}
