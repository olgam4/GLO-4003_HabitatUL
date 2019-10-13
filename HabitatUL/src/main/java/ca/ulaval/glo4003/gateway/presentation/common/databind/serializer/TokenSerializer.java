package ca.ulaval.glo4003.gateway.presentation.common.databind.serializer;

import ca.ulaval.glo4003.administration.domain.user.token.Token;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class TokenSerializer extends JsonSerializer<Token> {
  @Override
  public void serialize(
      Token token, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
      throws IOException {
    String value = token.getValue();
    jsonGenerator.writeString(value);
  }
}
