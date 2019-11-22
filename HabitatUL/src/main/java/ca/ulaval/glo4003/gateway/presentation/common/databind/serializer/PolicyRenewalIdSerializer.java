package ca.ulaval.glo4003.gateway.presentation.common.databind.serializer;

import ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyRenewalId;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class PolicyRenewalIdSerializer extends JsonSerializer<PolicyRenewalId> {
  @Override
  public void serialize(
      PolicyRenewalId policyRenewalId, JsonGenerator jsonGenerator, SerializerProvider serializers)
      throws IOException {
    String value = policyRenewalId.toRepresentation();
    jsonGenerator.writeString(value);
  }
}
