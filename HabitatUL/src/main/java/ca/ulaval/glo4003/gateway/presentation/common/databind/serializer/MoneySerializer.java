package ca.ulaval.glo4003.gateway.presentation.common.databind.serializer;

import ca.ulaval.glo4003.shared.domain.money.Money;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;

public class MoneySerializer extends JsonSerializer<Money> {
  @Override
  public void serialize(
      Money price, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
      throws IOException {
    BigDecimal value = price.getAmount().getValue();
    jsonGenerator.writeNumber(value);
  }
}
