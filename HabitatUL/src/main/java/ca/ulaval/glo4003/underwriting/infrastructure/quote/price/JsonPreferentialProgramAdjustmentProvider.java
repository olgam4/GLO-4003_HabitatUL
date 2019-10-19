package ca.ulaval.glo4003.underwriting.infrastructure.quote.price;

import ca.ulaval.glo4003.shared.infrastructure.JsonFileReader;
import ca.ulaval.glo4003.underwriting.domain.quote.price.PreferentialProgramAdjustmentProvider;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.MultiplicativeQuotePriceAdjustment;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.NoQuotePriceAdjustment;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.QuotePriceAdjustment;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class JsonPreferentialProgramAdjustmentProvider
    implements PreferentialProgramAdjustmentProvider {
  static final String FILE_PATH = "rabais.json";

  private Map<String, QuotePriceAdjustment> adjustments = new HashMap<>();

  public JsonPreferentialProgramAdjustmentProvider() {
    this(new JsonFileReader());
  }

  public JsonPreferentialProgramAdjustmentProvider(JsonFileReader jsonFileReader) {
    JSONObject parsedAdjustments = jsonFileReader.read(FILE_PATH);
    Map<String, Object> parsedAdjustmentsMap = parsedAdjustments.toMap();
    for (Map.Entry<String, Object> parsedAdjustment : parsedAdjustmentsMap.entrySet()) {
      double parsedAdjustmentValue = (double) parsedAdjustment.getValue();
      MultiplicativeQuotePriceAdjustment adjustment =
          new MultiplicativeQuotePriceAdjustment(parsedAdjustmentValue);
      String program = parsedAdjustment.getKey();
      adjustments.put(program, adjustment);
    }
  }

  @Override
  public QuotePriceAdjustment getAdjustment(String program) {
    return adjustments.getOrDefault(program, new NoQuotePriceAdjustment());
  }
}
