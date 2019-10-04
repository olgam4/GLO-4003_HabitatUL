package ca.ulaval.glo4003.gateway.presentation.filter;

import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.gateway.presentation.annotation.Secured;
import ca.ulaval.glo4003.management.domain.user.exception.UserUnauthorizedException;
import ca.ulaval.glo4003.management.domain.user.token.Token;
import ca.ulaval.glo4003.management.domain.user.token.TokenTranslator;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;
import java.lang.reflect.Method;

@Provider
@Priority(Priorities.AUTHORIZATION)
public class AuthorizationFilter implements ContainerRequestFilter {
  public static final String AUTHORIZATION_HEADER_SCHEME = "Bearer ";

  @Context private ResourceInfo resourceInfo;

  private TokenTranslator tokenTranslator;

  public AuthorizationFilter() {
    this(ServiceLocator.resolve(TokenTranslator.class));
  }

  public AuthorizationFilter(TokenTranslator tokenTranslator) {
    this.tokenTranslator = tokenTranslator;
  }

  public AuthorizationFilter(TokenTranslator tokenTranslator, ResourceInfo resourceInfo) {
    this(tokenTranslator);
    this.resourceInfo = resourceInfo;
  }

  @Override
  public void filter(ContainerRequestContext requestContext) {
    Method resourceMethod = resourceInfo.getResourceMethod();
    if (resourceMethod.getAnnotation(Secured.class) == null) return;

    Token token = extractToken(requestContext);
    tokenTranslator.decodeToken(token);
  }

  private Token extractToken(ContainerRequestContext requestContext) {
    String authHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
    if (authHeader == null) throw new UserUnauthorizedException();
    return new Token(authHeader.replace(AUTHORIZATION_HEADER_SCHEME, ""));
  }
}
