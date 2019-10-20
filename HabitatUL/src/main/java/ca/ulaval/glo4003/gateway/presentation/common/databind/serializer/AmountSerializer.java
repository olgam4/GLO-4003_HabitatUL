package ca.ulaval.glo4003.gateway.presentation.common.databind.serializer;

import ca.ulaval.glo4003.shared.domain.money.Amount;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;

public class AmountSerializer extends JsonSerializer<Amount> {
  @Override
  public void serialize(
      Amount amount, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
      throws IOException {
    BigDecimal value = amount.getValue();
    jsonGenerator.writeNumber(value);
  }
}
