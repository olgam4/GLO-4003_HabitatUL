package ca.ulaval.glo4003.gateway.presentation.filter;

import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

public class AuthFilterBuilder {
  private String DEFAULT_USER_KEY = "e2d6f7c6-9e35-4db4-be53-0327f00a15c2";
  private List<String> DEFAULT_USER_ROLES = new ArrayList<>();
  private String userKey = DEFAULT_USER_KEY;
  private List<String> userRoles = DEFAULT_USER_ROLES;

  private AuthFilterBuilder() {}

  public static AuthFilterBuilder anAuthFilter() {
    return new AuthFilterBuilder();
  }

  public AuthFilterBuilder withUserKey(String userKey) {
    this.userKey = userKey;
    return this;
  }

  public AuthFilterBuilder withUserRoles(List<String> userRoles) {
    this.userRoles = userRoles;
    return this;
  }

  public ContainerRequestFilter build() {
    return requestContext ->
        requestContext.setSecurityContext(
            new SecurityContext() {
              @Override
              public Principal getUserPrincipal() {
                return () -> userKey;
              }

              @Override
              public boolean isUserInRole(String s) {
                return userRoles.contains(s);
              }

              @Override
              public boolean isSecure() {
                return true;
              }

              @Override
              public String getAuthenticationScheme() {
                return null;
              }
            });
  }
}
