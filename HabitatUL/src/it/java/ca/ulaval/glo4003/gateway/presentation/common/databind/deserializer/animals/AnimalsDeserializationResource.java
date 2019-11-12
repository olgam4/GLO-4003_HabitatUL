package ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.animals;

import ca.ulaval.glo4003.calculator.domain.input.Animals;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.CustomDeserializerIT.DESERIALIZE_ROUTE;

@Path(DESERIALIZE_ROUTE)
@Produces(MediaType.APPLICATION_JSON)
public class AnimalsDeserializationResource {
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response getDeserialization(AnimalsDeserializationRequest request) {
    return Response.ok().build();
  }

  public static class AnimalsDeserializationRequest {
    public Animals value;
  }
}
