package ca.ulaval.glo4003.gateway.presentation.common.handling.error.shared;

import ca.ulaval.glo4003.gateway.presentation.common.handling.ErrorMappingIT;
import ca.ulaval.glo4003.shared.application.concurrency.error.ConcurrentAccessError;

import javax.ws.rs.core.Response;

public class ConcurrentAccessErrorMappingIT extends ErrorMappingIT {
  @Override
  public Throwable getError() {
    return new ConcurrentAccessError();
  }

  @Override
  public int getStatusCode() {
    return Response.Status.TOO_MANY_REQUESTS.getStatusCode();
  }

  @Override
  public String getErrorCodeMatcher() {
    return "CONCURRENT_ACCESS";
  }

  @Override
  public String getErrorMessageMatcher() {
    return "sorry, too many requests received at once";
  }
}
