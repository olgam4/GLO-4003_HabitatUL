package ca.ulaval.glo4003.gateway.presentation.common.databind.serializer;

import ca.ulaval.glo4003.coverage.domain.premium.detail.PremiumDetail;
import ca.ulaval.glo4003.coverage.domain.premium.detail.PremiumDetails;
import ca.ulaval.glo4003.gateway.presentation.coverage.response.PremiumDetailResponse;
import ca.ulaval.glo4003.shared.domain.money.Money;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class PremiumDetailsSerializer extends JsonSerializer<PremiumDetails> {
  @Override
  public void serialize(
      PremiumDetails premiumDetails, JsonGenerator jsonGenerator, SerializerProvider serializers)
      throws IOException {
    jsonGenerator.writeStartArray();
    for (PremiumDetail premiumDetail : premiumDetails.getCollection()) {
      String coverage = premiumDetail.getCoverage().getName();
      Money premium = premiumDetail.getPremium();
      jsonGenerator.writeObject(new PremiumDetailResponse(coverage, premium));
    }
    jsonGenerator.writeEndArray();
  }
}
