package ca.ulaval.glo4003.gateway.presentation.common.databind.serializer;

import ca.ulaval.glo4003.coverage.domain.claim.ClaimId;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class ClaimIdSerializer extends JsonSerializer<ClaimId> {
  @Override
  public void serialize(
      ClaimId claimId, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
      throws IOException {
    String value = claimId.toRepresentation();
    jsonGenerator.writeString(value);
  }
}
