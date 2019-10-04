package ca.ulaval.glo4003.gateway.presentation.databind.serializer;

import ca.ulaval.glo4003.management.domain.user.UserId;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class UserIdSerializer extends JsonSerializer<UserId> {
  @Override
  public void serialize(
      UserId userId, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
      throws IOException {
    String value = userId.getValue().toString();
    jsonGenerator.writeString(value);
  }
}
