package ca.ulaval.glo4003.gateway.presentation.databind.serializer;

import ca.ulaval.glo4003.underwriting.domain.quote.QuoteId;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class QuoteIdSerializer extends JsonSerializer<QuoteId> {
  @Override
  public void serialize(
      QuoteId quoteId, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
      throws IOException {
    String value = quoteId.getValue().toString();
    jsonGenerator.writeString(value);
  }
}
