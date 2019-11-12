package ca.ulaval.glo4003.gateway.presentation.claim;

import ca.ulaval.glo4003.coverage.application.claim.ClaimAppService;
import ca.ulaval.glo4003.coverage.application.claim.dto.ClaimDto;
import ca.ulaval.glo4003.coverage.domain.claim.ClaimId;
import ca.ulaval.glo4003.gateway.presentation.claim.response.ClaimResponse;
import ca.ulaval.glo4003.gateway.presentation.common.annotation.Secured;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path(ClaimResource.CLAIM_ROUTE)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClaimResource {
  public static final String CLAIM_ROUTE = "/claims";
  private static final String CLAIM_ID_PARAM_NAME = "claimId";

  private ClaimAppService claimAppService;
  private ClaimViewAssembler claimViewAssembler;

  public ClaimResource() {
    this(new ClaimAppService(), new ClaimViewAssembler());
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
}
