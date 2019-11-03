package ca.ulaval.glo4003.gateway.presentation.policy;

import ca.ulaval.glo4003.administration.application.user.UserAppService;
import ca.ulaval.glo4003.coverage.application.claim.dto.ClaimCreationDto;
import ca.ulaval.glo4003.coverage.application.policy.PolicyAppService;
import ca.ulaval.glo4003.coverage.domain.claim.ClaimId;
import ca.ulaval.glo4003.coverage.domain.policy.PolicyId;
import ca.ulaval.glo4003.gateway.presentation.common.annotation.Secured;
import ca.ulaval.glo4003.gateway.presentation.policy.request.ClaimRequest;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;

import static ca.ulaval.glo4003.Server.CONTEXT_PATH;
import static ca.ulaval.glo4003.gateway.presentation.claim.ClaimResource.CLAIM_ROUTE;

@Path(PolicyResource.POLICY_ROUTE)
@Produces(MediaType.APPLICATION_JSON)
public class PolicyResource {
  public static final String POLICY_ROUTE = "/policies";
  public static final String OPEN_CLAIM_ROUTE = "/open-claim";
  private static final String POLICY_ID_PARAM_NAME = "policyId";

  private PolicyAppService policyAppService;
  private UserAppService userAppService;
  private PolicyViewAssembler policyViewAssembler;

  public PolicyResource() {
    this(new PolicyAppService(), new UserAppService(), new PolicyViewAssembler());
  }

  public PolicyResource(
      PolicyAppService policyAppService,
      UserAppService userAppService,
      PolicyViewAssembler policyViewAssembler) {
    this.policyAppService = policyAppService;
    this.userAppService = userAppService;
    this.policyViewAssembler = policyViewAssembler;
  }

  @GET
  @Secured
  public Response getPolicies(@Context SecurityContext securityContext) {
    String userKey = securityContext.getUserPrincipal().getName();
    List<String> policies = userAppService.getPolicies(userKey);
    return Response.ok(policyViewAssembler.from(policies)).build();
  }

  @POST
  @Secured
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("/{" + POLICY_ID_PARAM_NAME + "}" + OPEN_CLAIM_ROUTE)
  public Response openClaim(
      @Context SecurityContext securityContext,
      @PathParam(POLICY_ID_PARAM_NAME) PolicyId policyId,
      @Valid ClaimRequest claimRequest) {
    ClaimCreationDto claimCreationDto = policyViewAssembler.from(claimRequest);
    ClaimId claimId = policyAppService.openClaim(policyId, claimCreationDto);
    URI location =
        UriBuilder.fromPath(CONTEXT_PATH)
            .path(CLAIM_ROUTE)
            .path(claimId.toRepresentation())
            .build();
    return Response.created(location).build();
  }
}
