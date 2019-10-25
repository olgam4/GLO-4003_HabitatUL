package ca.ulaval.glo4003.gateway.presentation.common.filter;

import ca.ulaval.glo4003.administration.application.user.AccessController;
import ca.ulaval.glo4003.administration.domain.user.error.UnauthorizedError;
import ca.ulaval.glo4003.administration.domain.user.token.InvalidTokenSignatureException;
import ca.ulaval.glo4003.administration.domain.user.token.Token;
import ca.ulaval.glo4003.administration.domain.user.token.TokenPayload;
import ca.ulaval.glo4003.administration.domain.user.token.TokenTranslator;
import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.gateway.presentation.common.annotation.Secured;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.lang.reflect.Method;
import java.security.Principal;

@Provider
@Priority(Priorities.AUTHORIZATION)
public class AuthorizationFilter implements ContainerRequestFilter {
  public static final String AUTHORIZATION_HEADER_SCHEME = "Bearer";

  @Context private ResourceInfo resourceInfo;

  private TokenTranslator tokenTranslator;
  private AccessController accessController;

  public AuthorizationFilter() {
    this(
        ServiceLocator.resolve(TokenTranslator.class),
        ServiceLocator.resolve(AccessController.class));
  }

  public AuthorizationFilter(TokenTranslator tokenTranslator, AccessController accessController) {
    this.tokenTranslator = tokenTranslator;
    this.accessController = accessController;
  }

  public AuthorizationFilter(
      TokenTranslator tokenTranslator,
      ResourceInfo resourceInfo,
      AccessController accessController) {
    this(tokenTranslator, accessController);
    this.resourceInfo = resourceInfo;
  }

  @Override
  public void filter(ContainerRequestContext requestContext) {
    Method resourceMethod = resourceInfo.getResourceMethod();
    if (resourceMethod.getAnnotation(Secured.class) == null) return;

    TokenPayload tokenPayload = extractTokenUser(requestContext);
    accessController.controlAccess(tokenPayload);
    setSecurityContext(requestContext, tokenPayload);
  }

  private TokenPayload extractTokenUser(ContainerRequestContext requestContext) {
    String authHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
    if (authHeader == null) throw new UnauthorizedError();
    Token token = new Token(authHeader.replace(AUTHORIZATION_HEADER_SCHEME, "").trim());
    try {
      return tokenTranslator.decodeToken(token);
    } catch (InvalidTokenSignatureException e) {
      throw new UnauthorizedError();
    }
  }

  private void setSecurityContext(
      ContainerRequestContext requestContext, TokenPayload tokenPayload) {
    final SecurityContext currentSecurityContext = requestContext.getSecurityContext();
    requestContext.setSecurityContext(
        new SecurityContext() {
          @Override
          public Principal getUserPrincipal() {
            return tokenPayload::getUserKey;
          }

          @Override
          public boolean isUserInRole(String s) {
            return currentSecurityContext.isUserInRole(s);
          }

          @Override
          public boolean isSecure() {
            return currentSecurityContext.isSecure();
          }

          @Override
          public String getAuthenticationScheme() {
            return AUTHORIZATION_HEADER_SCHEME;
          }
        });
  }
}
