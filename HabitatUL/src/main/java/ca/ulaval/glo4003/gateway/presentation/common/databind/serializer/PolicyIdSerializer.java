package ca.ulaval.glo4003.gateway.presentation.common.databind.serializer;

import ca.ulaval.glo4003.insuring.domain.policy.PolicyId;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class PolicyIdSerializer extends JsonSerializer<PolicyId> {
  @Override
  public void serialize(
      PolicyId policyId, JsonGenerator jsonGenerator, SerializerProvider serializers)
      throws IOException {
    String value = policyId.toRepresentation();
    jsonGenerator.writeString(value);
  }
}
