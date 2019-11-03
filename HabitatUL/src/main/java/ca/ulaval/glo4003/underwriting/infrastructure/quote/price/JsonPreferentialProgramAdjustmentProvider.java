package ca.ulaval.glo4003.underwriting.infrastructure.quote.price;

import ca.ulaval.glo4003.shared.infrastructure.JsonFileReader;
import ca.ulaval.glo4003.underwriting.domain.quote.price.PreferentialProgramAdjustmentProvider;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.MultiplicativeQuotePriceAdjustment;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.NullQuotePriceAdjustment;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.QuotePriceAdjustment;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.Map;

public class JsonPreferentialProgramAdjustmentProvider
    implements PreferentialProgramAdjustmentProvider {
  static final String FILE_PATH = "rabais.json";

  private Map<String, Map<String, Map<String, QuotePriceAdjustment>>> adjustments = new HashMap<>();

  public JsonPreferentialProgramAdjustmentProvider() {
    this(new JsonFileReader());
  }

  public JsonPreferentialProgramAdjustmentProvider(JsonFileReader jsonFileReader) {
    JSONObject parsedAdjustments = jsonFileReader.read(FILE_PATH);
    parseAdjustmentsFile(parsedAdjustments);
  }

  private static String normalize(String value) {
    String normalizedValue = Normalizer.normalize(value, Normalizer.Form.NFD);
    return normalizedValue
        .replaceAll("[^\\p{ASCII}]", "")
        .replaceAll("\\W", " ")
        .replaceAll("_", " ")
        .replaceAll("\\s+", " ")
        .toLowerCase();
  }

  private void parseAdjustmentsFile(JSONObject parsedAdjustments) {
    parsedAdjustments
        .keys()
        .forEachRemaining(cycle -> parseDegreeAdjustments(parsedAdjustments, cycle));
  }

  private void parseDegreeAdjustments(JSONObject degreeAdjustments, String cycle) {
    JSONObject degreeMap = degreeAdjustments.getJSONObject(cycle);
    degreeMap.keys().forEachRemaining(degree -> parseProgramAdjustments(cycle, degreeMap, degree));
  }

  private void parseProgramAdjustments(String cycle, JSONObject degreeMap, String degree) {
    JSONArray preferentialAdjustmentsArray = degreeMap.getJSONArray(degree);
    addProgramAdjustments(cycle, degree, preferentialAdjustmentsArray);
  }

  private void addProgramAdjustments(
      String cycle, String degree, JSONArray preferentialAdjustmentsArray) {
    for (int i = 0; i < preferentialAdjustmentsArray.length(); i++) {
      JSONObject preferentialAdjustment = preferentialAdjustmentsArray.getJSONObject(i);
      String program = preferentialAdjustment.getString("prog");
      Number adjustmentValue = preferentialAdjustment.getNumber("rabais");
      addProgramAdjustment(cycle, degree, program, adjustmentValue);
    }
  }

  private void addProgramAdjustment(
      String cycle, String degree, String program, Number adjustmentValue) {
    double parsedAdjustmentValue = -adjustmentValue.floatValue() / 100;
    MultiplicativeQuotePriceAdjustment adjustment =
        new MultiplicativeQuotePriceAdjustment(parsedAdjustmentValue);
    Map<String, Map<String, QuotePriceAdjustment>> degreeMap =
        adjustments.getOrDefault(cycle, new HashMap<>());
    Map<String, QuotePriceAdjustment> programMap = degreeMap.getOrDefault(degree, new HashMap<>());
    programMap.put(program, adjustment);
    degreeMap.put(degree, programMap);
    adjustments.put(cycle, degreeMap);
  }

  @Override
  public QuotePriceAdjustment getAdjustment(String cycle, String degree, String program) {
    String normalizedCycle = normalize(cycle);
    String normalizedDegree = normalize(degree);
    String normalizedProgram = normalize(program);
    Map<String, Map<String, QuotePriceAdjustment>> degreeMap =
        adjustments.getOrDefault(normalizedCycle, new HashMap<>());
    Map<String, QuotePriceAdjustment> programMap =
        degreeMap.getOrDefault(normalizedDegree, new HashMap<>());
    return programMap.getOrDefault(normalizedProgram, new NullQuotePriceAdjustment());
  }
}
