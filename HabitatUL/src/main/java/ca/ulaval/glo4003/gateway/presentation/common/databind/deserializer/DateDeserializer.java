package ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer;

import ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.error.InvalidDateError;
import ca.ulaval.glo4003.shared.domain.temporal.Date;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Optional;

public class DateDeserializer extends JsonDeserializer<Date> {
  @Override
  public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException {
    JsonNode node = jsonParser.getCodec().readTree(jsonParser);
    String value =
        Optional.ofNullable(node.textValue())
            .orElseThrow(() -> new InvalidDateError(node.toString()));
    return convertValueSafely(value);
  }

  private Date convertValueSafely(String value) throws InvalidDateError {
    try {
      return Date.from(LocalDate.parse(value));
    } catch (DateTimeParseException e) {
      throw new InvalidDateError(value);
    }
  }
}
