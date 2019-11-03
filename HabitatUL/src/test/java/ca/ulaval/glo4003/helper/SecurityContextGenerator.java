package ca.ulaval.glo4003.helper;

import com.github.javafaker.Faker;

import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

public class SecurityContextGenerator {
  private static final String USER_ID = Faker.instance().internet().uuid();

  private SecurityContextGenerator() {}

  public static SecurityContext createSecurityContext() {
    return new SecurityContext() {
      @Override
      public Principal getUserPrincipal() {
        return () -> USER_ID;
      }

      @Override
      public boolean isUserInRole(String s) {
        return false;
      }

      @Override
      public boolean isSecure() {
        return false;
      }

      @Override
      public String getAuthenticationScheme() {
        return null;
      }
    };
  }
}
