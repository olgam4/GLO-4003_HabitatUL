package ca.ulaval.glo4003.gateway.presentation.common.handling;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path(ErrorThrowingResource.ERROR_ROUTE)
@Produces(MediaType.APPLICATION_JSON)
public class ErrorThrowingResource {
  public static final String ERROR_ROUTE = "/error";

  private ErrorThrowingAppService errorThrowingAppService;

  public ErrorThrowingResource(ErrorThrowingAppService errorThrowingAppService) {
    this.errorThrowingAppService = errorThrowingAppService;
  }

  @GET
  public Response getError() throws Exception {
    errorThrowingAppService.getError();
    return Response.ok().build();
  }
}
