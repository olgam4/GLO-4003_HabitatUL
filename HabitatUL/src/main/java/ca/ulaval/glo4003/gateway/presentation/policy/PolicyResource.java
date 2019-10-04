package ca.ulaval.glo4003.gateway.presentation.policy;

import ca.ulaval.glo4003.coverage.application.policy.PolicyAppService;
import ca.ulaval.glo4003.gateway.presentation.annotation.Secured;
import ca.ulaval.glo4003.gateway.presentation.policy.response.PoliciesResponse;
import ca.ulaval.glo4003.management.application.user.UserAppService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

@Path(PolicyResource.POLICY_ROUTE)
@Produces(MediaType.APPLICATION_JSON)
public class PolicyResource {
  public static final String POLICY_ROUTE = "/policies";

  private PolicyAppService policyAppService;
  private UserAppService userAppService;

  public PolicyResource() {
    this(new PolicyAppService(), new UserAppService());
  }

  public PolicyResource(PolicyAppService policyAppService, UserAppService userAppService) {
    this.policyAppService = policyAppService;
    this.userAppService = userAppService;
  }

  @GET
  @Secured
  public Response getPolicies(@Context SecurityContext securityContext) {
    String userKey = securityContext.getUserPrincipal().getName();
    List<String> policies = userAppService.getPolicies(userKey);
    return Response.ok(new PoliciesResponse(policies)).build();
  }
}
