package ca.ulaval.glo4003.gateway.presentation.common.databind.serializer;

import ca.ulaval.glo4003.shared.domain.authority.AuthorityNumber;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class AuthorityNumberSerializer extends JsonSerializer<AuthorityNumber> {
  @Override
  public void serialize(
      AuthorityNumber authorityNumber,
      JsonGenerator jsonGenerator,
      SerializerProvider serializerProvider)
      throws IOException {
    String value = authorityNumber.toRepresentation();
    jsonGenerator.writeString(value);
  }
}
