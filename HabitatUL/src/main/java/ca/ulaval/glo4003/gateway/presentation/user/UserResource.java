package ca.ulaval.glo4003.gateway.presentation.user;

import ca.ulaval.glo4003.management.application.user.UserAppService;
import ca.ulaval.glo4003.management.domain.user.credential.Credentials;
import ca.ulaval.glo4003.management.domain.user.token.Token;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

import static ca.ulaval.glo4003.Server.CONTEXT_PATH;

@Path(UserResource.USER_ROUTE)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
  public static final String USER_ROUTE = "/users";
  public static final String AUTHENTICATION_ROUTE = "/authenticate";

  private UserAppService userAppService;
  private UserViewAssembler userViewAssembler;

  public UserResource() {
    this(new UserAppService(), new UserViewAssembler());
  }

  public UserResource(UserAppService userAppService, UserViewAssembler userViewAssembler) {
    this.userAppService = userAppService;
    this.userViewAssembler = userViewAssembler;
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response createUser(Credentials credentials) {
    String userKey = userAppService.createUser(credentials);
    URI location = UriBuilder.fromPath(CONTEXT_PATH).path(USER_ROUTE).path(userKey).build();
    return Response.created(location).build();
  }

  @POST
  @Path(AUTHENTICATION_ROUTE)
  public Response authenticateUser(Credentials authRequest) {
    Token token = userAppService.authenticateUser(authRequest);
    return Response.ok(userViewAssembler.from(token)).build();
  }
}
