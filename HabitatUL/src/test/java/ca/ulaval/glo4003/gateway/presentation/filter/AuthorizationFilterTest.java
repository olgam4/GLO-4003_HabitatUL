package ca.ulaval.glo4003.gateway.presentation.filter;

import ca.ulaval.glo4003.gateway.presentation.annotation.Secured;
import ca.ulaval.glo4003.generator.user.TokenGenerator;
import ca.ulaval.glo4003.generator.user.TokenPayloadGenerator;
import ca.ulaval.glo4003.management.application.user.AccessController;
import ca.ulaval.glo4003.management.domain.user.exception.UnauthorizedException;
import ca.ulaval.glo4003.management.domain.user.token.InvalidTokenSignatureException;
import ca.ulaval.glo4003.management.domain.user.token.Token;
import ca.ulaval.glo4003.management.domain.user.token.TokenPayload;
import ca.ulaval.glo4003.management.domain.user.token.TokenTranslator;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthorizationFilterTest {
  private static final Token TOKEN = TokenGenerator.createToken();
  private static final TokenPayload TOKEN_PAYLOAD = TokenPayloadGenerator.createValidTokenPayload();
  private static final String AUTH_HEADER = AUTHORIZATION_HEADER_SCHEME + TOKEN.getValue();

  @Mock private ResourceInfo resourceInfo;
  @Mock private Method resourceMethod;
  @Mock private Secured securedAnnotation;
  @Mock private ContainerRequestContext requestContext;
  @Mock private TokenTranslator tokenTranslator;
  @Mock private AccessController accessController;

  private AuthorizationFilter subject;

  @Before
  public void setUp() {
    when(resourceInfo.getResourceMethod()).thenReturn(resourceMethod);
    when(resourceMethod.getAnnotation(Secured.class)).thenReturn(securedAnnotation);
    when(requestContext.getHeaderString(HttpHeaders.AUTHORIZATION)).thenReturn(AUTH_HEADER);
    when(tokenTranslator.decodeToken(any())).thenReturn(TOKEN_PAYLOAD);
    subject = new AuthorizationFilter(tokenTranslator, resourceInfo, accessController);
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

  @Test
  public void filteringRequest_withSecuredRouteAndValidToken_shouldDelegateAccessController() {
    subject.filter(requestContext);

    verify(accessController).controlAccess(TOKEN_PAYLOAD);
  }

  @Test(expected = InvalidTokenSignatureException.class)
  public void filteringRequest_withSecuredRouteAndInvalidToken_shouldThrow() {
    when(tokenTranslator.decodeToken(any())).thenThrow(new InvalidTokenSignatureException());

    subject.filter(requestContext);
  }

  @Test(expected = UnauthorizedException.class)
  public void filteringRequest_withSecuredRouteAndWithoutToken_shouldThrow() {
    when(requestContext.getHeaderString(HttpHeaders.AUTHORIZATION)).thenReturn(null);

    subject.filter(requestContext);
  }
}
