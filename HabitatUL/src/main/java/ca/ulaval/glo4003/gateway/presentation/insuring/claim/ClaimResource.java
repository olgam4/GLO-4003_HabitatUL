package ca.ulaval.glo4003.gateway.presentation.insuring.claim;

import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.gateway.presentation.common.annotation.Secured;
import ca.ulaval.glo4003.gateway.presentation.insuring.claim.request.ProvideAuthorityNumberRequest;
import ca.ulaval.glo4003.gateway.presentation.insuring.claim.response.ClaimResponse;
import ca.ulaval.glo4003.insuring.application.claim.ClaimAppService;
import ca.ulaval.glo4003.insuring.application.claim.dto.ClaimDto;
import ca.ulaval.glo4003.insuring.domain.claim.ClaimId;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path(ClaimResource.CLAIM_ROUTE)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClaimResource {
  public static final String CLAIM_ROUTE = "/claims";
  private static final String CLAIM_ID_PARAM_NAME = "claimId";
  private static final String PROVIDE_AUTHORITY_NUMBER_ROUTE = "/authority-number";

  private ClaimAppService claimAppService;
  private ClaimViewAssembler claimViewAssembler;

  public ClaimResource() {
    this(ServiceLocator.resolve(ClaimAppService.class), new ClaimViewAssembler());
  }

  public ClaimResource(ClaimAppService claimAppService, ClaimViewAssembler claimViewAssembler) {
    this.claimAppService = claimAppService;
    this.claimViewAssembler = claimViewAssembler;
  }

  @GET
  @Secured
  @Path("/{" + CLAIM_ID_PARAM_NAME + "}")
  public Response getClaim(@PathParam(CLAIM_ID_PARAM_NAME) ClaimId claimId) {
    ClaimDto claimDto = claimAppService.getClaim(claimId);
    ClaimResponse claimResponse = claimViewAssembler.from(claimDto);
    return Response.ok(claimResponse).build();
  }

  @POST
  @Secured
  @Path("/{" + CLAIM_ID_PARAM_NAME + "}" + PROVIDE_AUTHORITY_NUMBER_ROUTE)
  public Response provideAuthorityNumber(
      @PathParam(CLAIM_ID_PARAM_NAME) ClaimId claimId,
      @Valid ProvideAuthorityNumberRequest provideAuthorityNumberRequest) {
    ClaimDto claimDto =
        claimAppService.provideAuthorityNumber(
            claimId, provideAuthorityNumberRequest.getAuthorityNumber());
    ClaimResponse claimResponse = claimViewAssembler.from(claimDto);
    return Response.ok(claimResponse).build();
  }
}
