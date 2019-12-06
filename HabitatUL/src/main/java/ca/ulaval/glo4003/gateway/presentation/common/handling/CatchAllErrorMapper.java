package ca.ulaval.glo4003.gateway.presentation.common.handling;

import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.shared.application.logging.Logger;
import ca.ulaval.glo4003.shared.domain.handling.DefaultError;
import ca.ulaval.glo4003.shared.domain.handling.Error;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class CatchAllErrorMapper implements ExceptionMapper<Throwable> {
  private ErrorResponseFactory errorResponseFactory;
  private Logger logger;

  public CatchAllErrorMapper() {
    this(ServiceLocator.resolve(Logger.class));
  }

  public CatchAllErrorMapper(Logger logger) {
    errorResponseFactory = new ErrorResponseFactory();
    this.logger = logger;
  }

  @Override
  public Response toResponse(Throwable throwable) {
    logger.severe(String.format("Unexpected error: %s", throwable));
    Error error = new DefaultError();
    ErrorResponse errorResponse = errorResponseFactory.createErrorResponse(error);
    return Response.status(errorResponse.getStatus())
        .entity(errorResponse.getMessage())
        .type(MediaType.APPLICATION_JSON_TYPE)
        .build();
  }
}
