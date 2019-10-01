package ca.ulaval.glo4003.gateway.presentation.databind.deserializer;

import ca.ulaval.glo4003.shared.domain.Date;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.time.LocalDate;

public class DateDeserializer extends JsonDeserializer<Date> {
  @Override
  public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException {
    JsonNode node = jsonParser.getCodec().readTree(jsonParser);
    return Date.from(LocalDate.parse(node.textValue()));
  }
}
