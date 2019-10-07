package ca.ulaval.glo4003.gateway.presentation.databind.serializer;

import ca.ulaval.glo4003.underwriting.domain.price.Price;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;

public class PriceSerializer extends JsonSerializer<Price> {
  @Override
  public void serialize(
      Price price, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
      throws IOException {
    BigDecimal value = price.getValue();
    jsonGenerator.writeNumber(value);
  }
}
