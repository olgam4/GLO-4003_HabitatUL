package ca.ulaval.glo4003.gateway.presentation.common.handling;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class ConstraintViolationErrorMapper
    implements ExceptionMapper<ConstraintViolationException> {
  private static final String ERROR = "INVALID_REQUEST_FORMAT";
  private static final String MESSAGE = "sorry, your request does not respect the expected format";

  @Override
  public Response toResponse(ConstraintViolationException exception) {
    return Response.status(Response.Status.BAD_REQUEST)
        .entity(new ErrorMessage(ERROR, MESSAGE))
        .type(MediaType.APPLICATION_JSON)
        .build();
  }
}
