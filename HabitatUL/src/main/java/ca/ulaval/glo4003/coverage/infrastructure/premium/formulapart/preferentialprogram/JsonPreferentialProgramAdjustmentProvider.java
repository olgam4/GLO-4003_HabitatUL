package ca.ulaval.glo4003.coverage.infrastructure.premium.formulapart.preferentialprogram;

import ca.ulaval.glo4003.coverage.domain.premium.adjustment.MultiplicativePremiumAdjustment;
import ca.ulaval.glo4003.coverage.domain.premium.adjustment.NullPremiumAdjustment;
import ca.ulaval.glo4003.coverage.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.coverage.domain.premium.formulapart.preferentialprogram.PreferentialProgramAdjustmentProvider;
import ca.ulaval.glo4003.shared.domain.handling.InvalidArgumentException;
import ca.ulaval.glo4003.shared.domain.identity.Cycle;
import ca.ulaval.glo4003.shared.domain.identity.Degree;
import ca.ulaval.glo4003.shared.infrastructure.io.JsonFileReader;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class JsonPreferentialProgramAdjustmentProvider
    implements PreferentialProgramAdjustmentProvider {
  static final String FILE_PATH = "rabais.json";

  private Map<Cycle, Map<Degree, Map<String, PremiumAdjustment>>> adjustments = new HashMap<>();

  public JsonPreferentialProgramAdjustmentProvider() throws InvalidArgumentException {
    this(new JsonFileReader());
  }

  public JsonPreferentialProgramAdjustmentProvider(JsonFileReader jsonFileReader)
      throws InvalidArgumentException {
    JSONObject parsedAdjustments = jsonFileReader.read(FILE_PATH);
    parseAdjustmentsFile(parsedAdjustments);
  }

  private void parseAdjustmentsFile(JSONObject parsedAdjustments) throws InvalidArgumentException {
    for (Iterator<String> it = parsedAdjustments.keys(); it.hasNext(); ) {
      String cycle = it.next();
      JSONObject degreeAdjustments = parsedAdjustments.getJSONObject(cycle);
      parseDegreeAdjustments(degreeAdjustments, Cycle.getEnum(cycle));
    }
  }

  private void parseDegreeAdjustments(JSONObject degreeAdjustments, Cycle cycle)
      throws InvalidArgumentException {
    for (Iterator<String> it = degreeAdjustments.keys(); it.hasNext(); ) {
      String degree = it.next();
      JSONArray programAdjustments = degreeAdjustments.getJSONArray(degree);
      addProgramAdjustments(cycle, Degree.getEnum(degree), programAdjustments);
    }
  }

  private void addProgramAdjustments(
      Cycle cycle, Degree degree, JSONArray preferentialAdjustmentsArray) {
    for (int i = 0; i < preferentialAdjustmentsArray.length(); i++) {
      JSONObject preferentialAdjustment = preferentialAdjustmentsArray.getJSONObject(i);
      String program = preferentialAdjustment.getString("prog");
      Number adjustmentValue = preferentialAdjustment.getNumber("rabais");
      addProgramAdjustment(cycle, degree, program, adjustmentValue);
    }
  }

  private void addProgramAdjustment(
      Cycle cycle, Degree degree, String program, Number adjustmentValue) {
    double parsedAdjustmentValue = -adjustmentValue.floatValue() / 100;
    MultiplicativePremiumAdjustment adjustment =
        new MultiplicativePremiumAdjustment(parsedAdjustmentValue);
    Map<Degree, Map<String, PremiumAdjustment>> degreeMap =
        adjustments.getOrDefault(cycle, new HashMap<>());
    Map<String, PremiumAdjustment> programMap = degreeMap.getOrDefault(degree, new HashMap<>());
    programMap.put(program, adjustment);
    degreeMap.put(degree, programMap);
    adjustments.put(cycle, degreeMap);
  }

  @Override
  public PremiumAdjustment getAdjustment(Cycle cycle, Degree degree, String major) {
    String normalizedProgram = normalize(major);
    Map<Degree, Map<String, PremiumAdjustment>> degreeMap =
        adjustments.getOrDefault(cycle, new HashMap<>());
    Map<String, PremiumAdjustment> programMap = degreeMap.getOrDefault(degree, new HashMap<>());
    return programMap.getOrDefault(normalizedProgram, new NullPremiumAdjustment());
  }

  private String normalize(String value) {
    if (value == null) return null;

    String normalizedValue = Normalizer.normalize(value, Normalizer.Form.NFD);
    return normalizedValue
        .replaceAll("[^\\p{ASCII}]", "")
        .replaceAll("\\W", " ")
        .replaceAll("_", " ")
        .replaceAll("\\s+", " ")
        .toLowerCase();
  }
}
