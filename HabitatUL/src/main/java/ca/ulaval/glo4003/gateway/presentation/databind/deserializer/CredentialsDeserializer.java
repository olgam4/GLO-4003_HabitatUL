package ca.ulaval.glo4003.gateway.presentation.databind.deserializer;

import ca.ulaval.glo4003.management.domain.user.credential.Credentials;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class CredentialsDeserializer extends JsonDeserializer<Credentials> {
  @Override
  public Credentials deserialize(
      JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
    JsonNode node = jsonParser.getCodec().readTree(jsonParser);
    return new Credentials(node.get("username").textValue(), node.get("password").textValue());
  }
}
