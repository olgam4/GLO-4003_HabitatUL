package ca.ulaval.glo4003.gateway.presentation.filter;

import ca.ulaval.glo4003.gateway.domain.user.exception.InvalidTokenException;
import ca.ulaval.glo4003.gateway.domain.user.exception.UserUnauthorizedException;
import ca.ulaval.glo4003.gateway.domain.user.token.Token;
import ca.ulaval.glo4003.gateway.domain.user.token.TokenTranslator;
import ca.ulaval.glo4003.gateway.presentation.annotation.Secured;
import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.HttpHeaders;
import java.lang.reflect.Method;

import static ca.ulaval.glo4003.gateway.presentation.filter.AuthorizationFilter.AUTHORIZATION_HEADER_SCHEME;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthorizationFilterTest {
  private static final Token A_TOKEN = new Token(Faker.instance().internet().uuid());
  private static final String AUTH_HEADER = AUTHORIZATION_HEADER_SCHEME + A_TOKEN.getValue();

  @Mock private TokenTranslator tokenTranslator;
  @Mock private ResourceInfo resourceInfo;
  @Mock private Method resourceMethod;
  @Mock private Secured securedAnnotation;
  @Mock private ContainerRequestContext requestContext;

  private AuthorizationFilter subject;

  @Before
  public void setUp() {
    subject = new AuthorizationFilter(tokenTranslator, resourceInfo);
    when(resourceInfo.getResourceMethod()).thenReturn(resourceMethod);
    when(resourceMethod.getAnnotation(Secured.class)).thenReturn(securedAnnotation);
    when(requestContext.getHeaderString(HttpHeaders.AUTHORIZATION)).thenReturn(AUTH_HEADER);
  }

  @Test
  public void filteringRequest_withUnsecuredRoute_shouldNotThrow() {
    when(resourceMethod.getAnnotation(Secured.class)).thenReturn(null);

    subject.filter(requestContext);
  }

  @Test
  public void filteringRequest_withSecuredRouteAndValidToken_shouldNotThrow() {
    subject.filter(requestContext);
  }

  @Test(expected = InvalidTokenException.class)
  public void filteringRequest_withSecuredRouteAndInvalidToken_shouldThrow() {
    when(tokenTranslator.decodeToken(any())).thenThrow(new InvalidTokenException());

    subject.filter(requestContext);
  }

  @Test(expected = UserUnauthorizedException.class)
  public void filteringRequest_withSecuredRouteAndWithoutToken_shouldThrow() {
    when(requestContext.getHeaderString(HttpHeaders.AUTHORIZATION)).thenReturn(null);

    subject.filter(requestContext);
  }
}
