package ca.ulaval.glo4003.gateway.presentation.policy;

import ca.ulaval.glo4003.administration.application.user.UserAppService;
import ca.ulaval.glo4003.gateway.presentation.common.annotation.Secured;
import ca.ulaval.glo4003.gateway.presentation.policy.request.ClaimRequest;
import ca.ulaval.glo4003.gateway.presentation.policy.request.ModificationRequest;
import ca.ulaval.glo4003.insuring.application.policy.PolicyAppService;
import ca.ulaval.glo4003.insuring.application.policy.dto.OpenClaimDto;
import ca.ulaval.glo4003.insuring.domain.claim.ClaimId;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyId;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;

import static ca.ulaval.glo4003.Server.CONTEXT_PATH;
import static ca.ulaval.glo4003.gateway.presentation.claim.ClaimResource.CLAIM_ROUTE;

@Path(PolicyResource.POLICY_ROUTE)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PolicyResource {
  public static final String POLICY_ROUTE = "/policies";
  public static final String MODIFY_POLICY_ROUTE = "/modifications";
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
  @Path("/{" + POLICY_ID_PARAM_NAME + "}" + MODIFY_POLICY_ROUTE)
  public Response modifyPolicy(
      @Context SecurityContext securityContext,
      @PathParam(POLICY_ID_PARAM_NAME) PolicyId policyId,
      @Valid ModificationRequest modificationRequest) {
    return Response.ok().build();
  }

  @POST
  @Secured
  @Path("/{" + POLICY_ID_PARAM_NAME + "}" + OPEN_CLAIM_ROUTE)
  public Response openClaim(
      @Context SecurityContext securityContext,
      @PathParam(POLICY_ID_PARAM_NAME) PolicyId policyId,
      @Valid ClaimRequest claimRequest) {
    OpenClaimDto openClaimDto = policyViewAssembler.from(claimRequest);
    ClaimId claimId = policyAppService.openClaim(policyId, openClaimDto);
    URI location =
        UriBuilder.fromPath(CONTEXT_PATH)
            .path(CLAIM_ROUTE)
            .path(claimId.toRepresentation())
            .build();
    return Response.created(location).build();
  }
}
