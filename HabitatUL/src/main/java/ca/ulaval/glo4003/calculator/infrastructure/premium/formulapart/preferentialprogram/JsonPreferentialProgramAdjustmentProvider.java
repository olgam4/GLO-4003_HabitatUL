package ca.ulaval.glo4003.calculator.infrastructure.premium.formulapart.preferentialprogram;

import ca.ulaval.glo4003.calculator.domain.premium.adjustment.MultiplicativePremiumAdjustment;
import ca.ulaval.glo4003.calculator.domain.premium.adjustment.NullPremiumAdjustment;
import ca.ulaval.glo4003.calculator.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.calculator.domain.premium.formulapart.preferentialprogram.PreferentialProgramAdjustmentProvider;
import ca.ulaval.glo4003.shared.infrastructure.io.JsonFileReader;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.Map;

public class JsonPreferentialProgramAdjustmentProvider
    implements PreferentialProgramAdjustmentProvider {
  static final String FILE_PATH = "rabais.json";

  private Map<String, Map<String, Map<String, PremiumAdjustment>>> adjustments = new HashMap<>();

  public JsonPreferentialProgramAdjustmentProvider() {
    this(new JsonFileReader());
  }

  public JsonPreferentialProgramAdjustmentProvider(JsonFileReader jsonFileReader) {
    JSONObject parsedAdjustments = jsonFileReader.read(FILE_PATH);
    parseAdjustmentsFile(parsedAdjustments);
  }

  private static String normalize(String value) {
    if (value == null) return null;

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
    MultiplicativePremiumAdjustment adjustment =
        new MultiplicativePremiumAdjustment(parsedAdjustmentValue);
    Map<String, Map<String, PremiumAdjustment>> degreeMap =
        adjustments.getOrDefault(cycle, new HashMap<>());
    Map<String, PremiumAdjustment> programMap = degreeMap.getOrDefault(degree, new HashMap<>());
    programMap.put(program, adjustment);
    degreeMap.put(degree, programMap);
    adjustments.put(cycle, degreeMap);
  }

  @Override
  public PremiumAdjustment getAdjustment(String cycle, String degree, String program) {
    String normalizedCycle = normalize(cycle);
    String normalizedDegree = normalize(degree);
    String normalizedProgram = normalize(program);
    Map<String, Map<String, PremiumAdjustment>> degreeMap =
        adjustments.getOrDefault(normalizedCycle, new HashMap<>());
    Map<String, PremiumAdjustment> programMap =
        degreeMap.getOrDefault(normalizedDegree, new HashMap<>());
    return programMap.getOrDefault(normalizedProgram, new NullPremiumAdjustment());
  }
}
