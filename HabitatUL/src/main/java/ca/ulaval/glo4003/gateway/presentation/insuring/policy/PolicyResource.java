package ca.ulaval.glo4003.gateway.presentation.insuring.policy;

import ca.ulaval.glo4003.administration.application.user.UserAppService;
import ca.ulaval.glo4003.gateway.presentation.common.annotation.Secured;
import ca.ulaval.glo4003.gateway.presentation.insuring.policy.request.ClaimRequest;
import ca.ulaval.glo4003.gateway.presentation.insuring.policy.request.InsureBicycleRequest;
import ca.ulaval.glo4003.gateway.presentation.insuring.policy.request.ModifyCoverageRequest;
import ca.ulaval.glo4003.gateway.presentation.insuring.policy.request.TriggerRenewalRequest;
import ca.ulaval.glo4003.insuring.application.policy.PolicyAppService;
import ca.ulaval.glo4003.insuring.application.policy.dto.*;
import ca.ulaval.glo4003.insuring.domain.claim.ClaimId;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyId;
import ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModificationId;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;

import static ca.ulaval.glo4003.Server.CONTEXT_PATH;
import static ca.ulaval.glo4003.gateway.presentation.insuring.claim.ClaimResource.CLAIM_ROUTE;

@Path(PolicyResource.POLICY_ROUTE)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PolicyResource {
  public static final String POLICY_ROUTE = "/policies";
  public static final String INSURE_BICYCLE_ROUTE = "/insure-bicycle";
  public static final String MODIFY_COVERAGE_ROUTE = "/modify-coverage";
  public static final String POLICY_MODIFICATION_ROUTE = "/modifications";
  public static final String CONFIRM_MODIFICATION_ROUTE = "/confirm";
  public static final String OPEN_CLAIM_ROUTE = "/open-claim";
  public static final String TRIGGER_RENEWAL_ROUTE = "/trigger-renewal";
  private static final String POLICY_ID_PARAM_NAME = "policyId";
  private static final String POLICY_MODIFICATION_ID_PARAM_NAME = "policyModificationId";
  private static final String SPECIFIC_POLICY_ROUTE = "/{" + POLICY_ID_PARAM_NAME + "}";
  private static final String INSURE_BICYCLE_FULL_ROUTE =
      SPECIFIC_POLICY_ROUTE + INSURE_BICYCLE_ROUTE;
  private static final String MODIFY_COVERAGE_FULL_ROUTE =
      SPECIFIC_POLICY_ROUTE + MODIFY_COVERAGE_ROUTE;
  private static final String SPECIFIC_POLICY_MODIFICATION_ROUTE =
      "/{" + POLICY_MODIFICATION_ID_PARAM_NAME + "}";
  private static final String CONFIRM_MODIFICATION_FULL_ROUTE =
      SPECIFIC_POLICY_ROUTE
          + POLICY_MODIFICATION_ROUTE
          + SPECIFIC_POLICY_MODIFICATION_ROUTE
          + CONFIRM_MODIFICATION_ROUTE;
  private static final String OPEN_CLAIM_FULL_ROUTE = SPECIFIC_POLICY_ROUTE + OPEN_CLAIM_ROUTE;
  private static final String TRIGGER_RENEWAL_FULL_ROUTE =
      SPECIFIC_POLICY_ROUTE + TRIGGER_RENEWAL_ROUTE;

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
  @Path(INSURE_BICYCLE_FULL_ROUTE)
  public Response insureBicycle(
      @Context SecurityContext securityContext,
      @PathParam(POLICY_ID_PARAM_NAME) PolicyId policyId,
      @Valid InsureBicycleRequest insureBicycleRequest) {
    InsureBicycleDto insureBicycleDto = policyViewAssembler.from(insureBicycleRequest);
    PolicyModificationDto policyModificationDto =
        policyAppService.insureBicycle(policyId, insureBicycleDto);
    URI location = buildPolicyModificationLocation(policyId, policyModificationDto);
    return Response.created(location)
        .entity(policyViewAssembler.from(policyModificationDto))
        .build();
  }

  @POST
  @Secured
  @Path(MODIFY_COVERAGE_FULL_ROUTE)
  public Response modifyCoverage(
      @Context SecurityContext securityContext,
      @PathParam(POLICY_ID_PARAM_NAME) PolicyId policyId,
      @Valid ModifyCoverageRequest modifyCoverageRequest) {
    ModifyCoverageDto modifyCoverageDto = policyViewAssembler.from(modifyCoverageRequest);
    PolicyModificationDto policyModificationDto =
        policyAppService.modifyCoverage(policyId, modifyCoverageDto);
    URI location = buildPolicyModificationLocation(policyId, policyModificationDto);
    return Response.created(location)
        .entity(policyViewAssembler.from(policyModificationDto))
        .build();
  }

  private URI buildPolicyModificationLocation(
      PolicyId policyId, PolicyModificationDto policyModificationDto) {
    String policyIdRepresentation = policyId.toRepresentation();
    String policyModificationIdRepresentation =
        policyModificationDto.getPolicyModificationId().toRepresentation();
    return UriBuilder.fromPath(CONTEXT_PATH)
        .path(POLICY_ROUTE)
        .path(policyIdRepresentation)
        .path(POLICY_MODIFICATION_ROUTE)
        .path(policyModificationIdRepresentation)
        .build();
  }

  @POST
  @Secured
  @Path(CONFIRM_MODIFICATION_FULL_ROUTE)
  public Response confirmModification(
      @Context SecurityContext securityContext,
      @PathParam(POLICY_ID_PARAM_NAME) PolicyId policyId,
      @PathParam(POLICY_MODIFICATION_ID_PARAM_NAME) PolicyModificationId policyModificationId) {
    PolicyDto policyDto = policyAppService.confirmModification(policyId, policyModificationId);
    return Response.ok(policyViewAssembler.from(policyDto)).build();
  }

  @POST
  @Secured
  @Path(OPEN_CLAIM_FULL_ROUTE)
  public Response openClaim(
      @Context SecurityContext securityContext,
      @PathParam(POLICY_ID_PARAM_NAME) PolicyId policyId,
      @Valid ClaimRequest claimRequest) {
    OpenClaimDto openClaimDto = policyViewAssembler.from(claimRequest);
    ClaimId claimId = policyAppService.openClaim(policyId, openClaimDto);
    String claimIdRepresentation = claimId.toRepresentation();
    URI location =
        UriBuilder.fromPath(CONTEXT_PATH).path(CLAIM_ROUTE).path(claimIdRepresentation).build();
    return Response.created(location).build();
  }

  @POST
  @Secured
  @Path(TRIGGER_RENEWAL_FULL_ROUTE)
  public Response triggerRenewal(
      @Context SecurityContext securityContext,
      @PathParam(POLICY_ID_PARAM_NAME) PolicyId policyId,
      @Valid TriggerRenewalRequest triggerRenewalRequest) {
    TriggerRenewalDto triggerRenewalDto = policyViewAssembler.from(triggerRenewalRequest);
    policyAppService.triggerRenewal(policyId, triggerRenewalDto);
    // TODO: COME BACK TO IT ONCE THE USE CASE COMPLETED
    return Response.ok().build();
  }
}
