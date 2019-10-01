package ca.ulaval.glo4003.gateway.presentation.databind.serializer;

import ca.ulaval.glo4003.underwriting.domain.premium.Premium;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;

public class PremiumSerializer extends JsonSerializer<Premium> {
  @Override
  public void serialize(
      Premium premium, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
      throws IOException {
    BigDecimal value = premium.getValue();
    jsonGenerator.writeNumber(value);
  }
}
