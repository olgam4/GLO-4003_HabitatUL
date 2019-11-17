package ca.ulaval.glo4003.gateway.presentation.common.databind.serializer;

import ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModificationId;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class PolicyModificationIdSerializer extends JsonSerializer<PolicyModificationId> {
  @Override
  public void serialize(
      PolicyModificationId policyModificationId,
      JsonGenerator jsonGenerator,
      SerializerProvider serializers)
      throws IOException {
    String value = policyModificationId.toRepresentation();
    jsonGenerator.writeString(value);
  }
}
