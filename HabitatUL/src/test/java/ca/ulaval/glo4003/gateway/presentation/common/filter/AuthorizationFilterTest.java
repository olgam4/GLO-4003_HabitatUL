package ca.ulaval.glo4003.gateway.presentation.common.filter;

import ca.ulaval.glo4003.administration.application.user.AccessController;
import ca.ulaval.glo4003.administration.domain.user.error.UnauthorizedError;
import ca.ulaval.glo4003.administration.domain.user.token.InvalidTokenSignatureException;
import ca.ulaval.glo4003.administration.domain.user.token.Token;
import ca.ulaval.glo4003.administration.domain.user.token.TokenPayload;
import ca.ulaval.glo4003.administration.domain.user.token.TokenTranslator;
import ca.ulaval.glo4003.administration.helper.user.TokenPayloadBuilder;
import ca.ulaval.glo4003.gateway.presentation.common.filter.annotation.Actuary;
import ca.ulaval.glo4003.gateway.presentation.common.filter.annotation.Secured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ResourceInfo;
import java.lang.reflect.Method;

import static ca.ulaval.glo4003.administration.helper.user.TokenGenerator.createToken;
import static ca.ulaval.glo4003.administration.helper.user.TokenPayloadGenerator.createValidTokenPayload;
import static ca.ulaval.glo4003.gateway.presentation.common.filter.AuthorizationFilter.AUTHORIZATION_HEADER_SCHEME;
import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthorizationFilterTest {
  private static final Token TOKEN = createToken();
  private static final TokenPayload TOKEN_PAYLOAD = createValidTokenPayload();
  private static final String AUTH_HEADER = AUTHORIZATION_HEADER_SCHEME + TOKEN.getValue();

  @Mock private ResourceInfo resourceInfo;
  @Mock private Method resourceMethod;
  @Mock private ContainerRequestContext requestContext;
  @Mock private TokenTranslator tokenTranslator;
  @Mock private AccessController accessController;
  @Mock private Secured securedAnnotation;
  @Mock private Actuary actuaryAnnotation;

  private AuthorizationFilter subject;

  @Before
  public void setUp() throws InvalidTokenSignatureException {
    when(resourceInfo.getResourceMethod()).thenReturn(resourceMethod);
    when(requestContext.getHeaderString(AUTHORIZATION)).thenReturn(AUTH_HEADER);
    when(tokenTranslator.decodeToken(any(Token.class))).thenReturn(TOKEN_PAYLOAD);
    subject = new AuthorizationFilter(tokenTranslator, resourceInfo, accessController);
  }

  @Test
  public void filteringRequest_withUnsecuredRoute_shouldNotThrow() {
    subject.filter(requestContext);
  }

  @Test
  public void filteringRequest_withSecuredRouteAndValidToken_shouldNotThrow() {
    when(resourceMethod.getAnnotation(Secured.class)).thenReturn(securedAnnotation);

    subject.filter(requestContext);
  }

  @Test
  public void filteringRequest_withSecuredRouteAndValidToken_shouldDelegateToAccessController() {
    when(resourceMethod.getAnnotation(Secured.class)).thenReturn(securedAnnotation);

    subject.filter(requestContext);

    verify(accessController).controlAccess(TOKEN_PAYLOAD);
  }

  @Test(expected = UnauthorizedError.class)
  public void filteringRequest_withSecuredRouteAndInvalidToken_shouldThrow()
      throws InvalidTokenSignatureException {
    when(resourceMethod.getAnnotation(Secured.class)).thenReturn(securedAnnotation);
    when(tokenTranslator.decodeToken(any())).thenThrow(new InvalidTokenSignatureException());

    subject.filter(requestContext);
  }

  @Test(expected = UnauthorizedError.class)
  public void filteringRequest_withSecuredRouteAndWithoutToken_shouldThrow() {
    when(resourceMethod.getAnnotation(Secured.class)).thenReturn(securedAnnotation);
    when(requestContext.getHeaderString(AUTHORIZATION)).thenReturn(null);

    subject.filter(requestContext);
  }

  @Test
  public void filteringRequest_withActuaryRouteAndValidToken_shouldNotThrow()
      throws InvalidTokenSignatureException {
    when(resourceMethod.getAnnotation(Actuary.class)).thenReturn(actuaryAnnotation);
    TokenPayload tokenPayload =
        TokenPayloadBuilder.aTokenPayload()
            .withUserKey("89826a12-9cfc-401e-82f0-dcf9e17d48af")
            .build();
    when(tokenTranslator.decodeToken(any(Token.class))).thenReturn(tokenPayload);

    subject.filter(requestContext);
  }

  @Test
  public void filteringRequest_withActuaryRouteAndValidToken_shouldDelegateToAccessController()
      throws InvalidTokenSignatureException {
    when(resourceMethod.getAnnotation(Actuary.class)).thenReturn(actuaryAnnotation);
    TokenPayload tokenPayload =
        TokenPayloadBuilder.aTokenPayload()
            .withUserKey("89826a12-9cfc-401e-82f0-dcf9e17d48af")
            .build();
    when(tokenTranslator.decodeToken(any(Token.class))).thenReturn(tokenPayload);

    subject.filter(requestContext);

    verify(accessController).controlAccess(tokenPayload);
  }

  @Test(expected = UnauthorizedError.class)
  public void filteringRequest_withActuaryRouteAndWithoutToken_shouldThrow() {
    when(resourceMethod.getAnnotation(Actuary.class)).thenReturn(actuaryAnnotation);
    when(requestContext.getHeaderString(AUTHORIZATION)).thenReturn(null);

    subject.filter(requestContext);
  }

  @Test(expected = UnauthorizedError.class)
  public void filteringRequest_withActuaryRouteAndInvalidToken_shouldThrow()
      throws InvalidTokenSignatureException {
    when(resourceMethod.getAnnotation(Actuary.class)).thenReturn(actuaryAnnotation);
    when(tokenTranslator.decodeToken(any())).thenThrow(new InvalidTokenSignatureException());

    subject.filter(requestContext);
  }

  @Test(expected = UnauthorizedError.class)
  public void filteringRequest_WithActuaryRouteAndValidTokenButNotAnActuaryUser_shouldThrow() {
    when(resourceMethod.getAnnotation(Actuary.class)).thenReturn(actuaryAnnotation);

    subject.filter(requestContext);

    verify(accessController).controlAccess(TOKEN_PAYLOAD);
  }
}
