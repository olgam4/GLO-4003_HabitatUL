package ca.ulaval.glo4003.gateway.presentation.common.handling;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class RouteNotFoundErrorMapper implements ExceptionMapper<NotFoundException> {
  private static final String ERROR = "ROUTE_NOT_FOUND";
  private static final String MESSAGE = "sorry, this route does not exist";

  @Override
  public Response toResponse(NotFoundException error) {
    return Response.status(Response.Status.NOT_FOUND)
        .entity(new ErrorMessage(ERROR, MESSAGE))
        .type(MediaType.APPLICATION_JSON_TYPE)
        .build();
  }
}
