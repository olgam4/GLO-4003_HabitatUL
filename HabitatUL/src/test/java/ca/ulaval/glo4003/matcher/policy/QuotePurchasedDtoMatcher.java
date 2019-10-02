package ca.ulaval.glo4003.matcher.policy;

import ca.ulaval.glo4003.coverage.application.policy.dto.QuotePurchasedDto;
import ca.ulaval.glo4003.mediator.Event;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.mockito.hamcrest.MockitoHamcrest;

public class QuotePurchasedDtoMatcher extends TypeSafeMatcher<QuotePurchasedDto> {
  private final Event event;

  private QuotePurchasedDtoMatcher(final Event event) {
    this.event = event;
  }

  public static QuotePurchasedDto mockitoQuotePurchasedDtoMatcher(final Event event) {
    return MockitoHamcrest.argThat(matchesQuotePurchasedDto(event));
  }

  public static Matcher<QuotePurchasedDto> matchesQuotePurchasedDto(final Event event) {
    return new QuotePurchasedDtoMatcher(event);
  }

  @Override
  public void describeTo(final Description description) {
    description.appendText(String.format("matches corresponding event: %s", event.toString()));
  }

  @Override
  public boolean matchesSafely(final QuotePurchasedDto quotePurchasedDto) {
    // TODO
    return true;
  }
}
