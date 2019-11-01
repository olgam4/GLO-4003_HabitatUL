package ca.ulaval.glo4003.gateway.presentation.common.handling;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ElementKind;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class ConstraintViolationErrorMapper
    implements ExceptionMapper<ConstraintViolationException> {
  private static final String ERROR = "INVALID_REQUEST_FORMAT";

  @Override
  public Response toResponse(ConstraintViolationException exception) {
    return Response.status(Response.Status.BAD_REQUEST)
        .entity(new ErrorMessage(ERROR, getMessages(exception)))
        .type(MediaType.APPLICATION_JSON)
        .build();
  }

  private Set<String> getMessages(ConstraintViolationException exception) {
    Set<String> messages = new HashSet<>();
    exception
        .getConstraintViolations()
        .iterator()
        .forEachRemaining(appendViolationMessage(messages));
    return messages;
  }

  private Consumer<ConstraintViolation<?>> appendViolationMessage(Set<String> messages) {
    return violation -> {
      List<String> propertyPath = new ArrayList<>();
      violation
          .getPropertyPath()
          .iterator()
          .forEachRemaining(
              node -> {
                if (node.getKind().equals(ElementKind.PROPERTY)) {
                  propertyPath.add(node.getName());
                }
              });
      String propertyName = String.join(".", propertyPath);
      String violationMessage = violation.getMessage();
      messages.add(String.join(" ", propertyName, violationMessage));
    };
  }
}
