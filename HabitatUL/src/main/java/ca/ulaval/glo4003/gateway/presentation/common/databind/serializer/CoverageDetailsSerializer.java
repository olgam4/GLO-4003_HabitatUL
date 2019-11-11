package ca.ulaval.glo4003.gateway.presentation.common.databind.serializer;

import ca.ulaval.glo4003.calculator.domain.coverage.detail.CoverageDetail;
import ca.ulaval.glo4003.calculator.domain.coverage.detail.CoverageDetails;
import ca.ulaval.glo4003.gateway.presentation.calculator.response.CoverageDetailResponse;
import ca.ulaval.glo4003.shared.domain.money.Amount;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class CoverageDetailsSerializer extends JsonSerializer<CoverageDetails> {
  @Override
  public void serialize(
      CoverageDetails coverageDetails, JsonGenerator jsonGenerator, SerializerProvider serializers)
      throws IOException {
    jsonGenerator.writeStartArray();
    for (CoverageDetail coverageDetail : coverageDetails.getCollection()) {
      String coverage = coverageDetail.getCoverage().getName();
      Amount amount = coverageDetail.getAmount();
      jsonGenerator.writeObject(new CoverageDetailResponse(coverage, amount));
    }
    jsonGenerator.writeEndArray();
  }
}
