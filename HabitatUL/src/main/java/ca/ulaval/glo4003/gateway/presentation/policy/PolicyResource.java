package ca.ulaval.glo4003.gateway.presentation.policy;

import ca.ulaval.glo4003.coverage.application.policy.PolicyAppService;
import ca.ulaval.glo4003.coverage.domain.claim.ClaimId;
import ca.ulaval.glo4003.coverage.domain.policy.PolicyId;
import ca.ulaval.glo4003.coverage.presentation.claim.ClaimDto;
import ca.ulaval.glo4003.gateway.presentation.annotation.Secured;
import ca.ulaval.glo4003.gateway.presentation.policy.request.ClaimRequest;
import ca.ulaval.glo4003.gateway.presentation.policy.response.PoliciesResponse;
import ca.ulaval.glo4003.management.application.user.UserAppService;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;

import static ca.ulaval.glo4003.Server.CONTEXT_PATH;

@Path(PolicyResource.POLICY_ROUTE)
@Produces(MediaType.APPLICATION_JSON)
public class PolicyResource {
  public static final String POLICY_ROUTE = "/policies";
  public static final String CLAIM_ROUTE = "/claims";
  private static final String POLICY_ID_PARAM_NAME = "policyId";

  private PolicyAppService policyAppService;
  private UserAppService userAppService;
  private ClaimAssembler claimAssembler;

  public PolicyResource() {
    this(new PolicyAppService(), new UserAppService(), new ClaimAssembler());
  }

  public PolicyResource(
      PolicyAppService policyAppService,
      UserAppService userAppService,
      ClaimAssembler claimAssembler) {
    this.policyAppService = policyAppService;
    this.userAppService = userAppService;
    this.claimAssembler = claimAssembler;
  }

  @GET
  @Secured
  public Response getPolicies(@Context SecurityContext securityContext) {
    String userKey = securityContext.getUserPrincipal().getName();
    List<String> policies = userAppService.getPolicies(userKey);
    return Response.ok(new PoliciesResponse(policies)).build();
  }

  @POST
  @Secured
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("/{" + POLICY_ID_PARAM_NAME + "}" + CLAIM_ROUTE)
  public Response claim(
      @Context SecurityContext securityContext,
      @PathParam(POLICY_ID_PARAM_NAME) PolicyId policyId,
      ClaimRequest claimRequest) {
    ClaimDto claimDto = claimAssembler.from(claimRequest);
    ClaimId claimId = policyAppService.openClaim(policyId, claimDto);
    URI location =
        UriBuilder.fromPath(CONTEXT_PATH)
            .path(CLAIM_ROUTE)
            .path(claimId.getValue().toString())
            .build();
    return Response.created(location).build();
  }
}
