package ca.ulaval.glo4003.gateway.presentation.quote;

import ca.ulaval.glo4003.helper.EnumSampler;
import ca.ulaval.glo4003.underwriting.domain.quote.form.building.PreventionSystem;
import ca.ulaval.glo4003.underwriting.domain.quote.form.civilliability.CivilLiabilityLimit;
import ca.ulaval.glo4003.underwriting.domain.quote.form.identity.Gender;
import ca.ulaval.glo4003.underwriting.domain.quote.form.personalproperty.AnimalBreed;
import com.github.javafaker.Faker;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class QuoteRequestBuilder {
  private final String DEFAULT_FIRST_NAME = Faker.instance().funnyName().name();
  private final String DEFAULT_LAST_NAME = Faker.instance().funnyName().name();
  private final String DEFAULT_BIRTH_DATE = createBirthDate();
  private final String DEFAULT_GENDER = createGender();

  private final String DEFAULT_IDUL = Faker.instance().university().name();
  private final String DEFAULT_IDENTIFICATION_NUMBER = Faker.instance().educator().university();
  private final String DEFAULT_PROGRAM = Faker.instance().educator().course();

  private final String DEFAULT_ZIP_CODE = "G3A0G4";
  private final int DEFAULT_STREET_NUMBER = 13;
  private final int DEFAULT_APARTMENT_NUMBER = 2;
  private final String DEFAULT_FLOOR = "1ST";

  private final String DEFAULT_EFFECTIVE_DATE = createEffectiveDate();

  private final int DEFAULT_NUMBER_OF_UNITS = 12;
  private final Set<String> DEFAULT_PREVENTION_SYSTEMS = createPreventionSystems();
  private final String DEFAULT_COMMERCIAL_USE = "other";

  private final double DEFAULT_PERSONAL_PROPERTY_COVERAGE_AMOUNT = createCoverageAmount();
  private final List<JSONObject> DEFAULT_ANIMALS = createAnimals();

  private final String DEFAULT_CIVIL_LIABILITY_COVERAGE_LIMIT =
      EnumSampler.sample(CivilLiabilityLimit.class).getRepresentation();

  private String firstName = DEFAULT_FIRST_NAME;
  private String lastName = DEFAULT_LAST_NAME;
  private String birthDate = DEFAULT_BIRTH_DATE;
  private String gender = DEFAULT_GENDER;

  private String idul = DEFAULT_IDUL;
  private String identificationNumber = DEFAULT_IDENTIFICATION_NUMBER;
  private String program = DEFAULT_PROGRAM;

  private String zipCode = DEFAULT_ZIP_CODE;
  private int streetNumber = DEFAULT_STREET_NUMBER;
  private int apartmentNumber = DEFAULT_APARTMENT_NUMBER;
  private String floor = DEFAULT_FLOOR;

  private String effectiveDate = DEFAULT_EFFECTIVE_DATE;

  private int numberOfUnits = DEFAULT_NUMBER_OF_UNITS;
  private Set<String> preventionSystems = DEFAULT_PREVENTION_SYSTEMS;
  private String commercialUse = DEFAULT_COMMERCIAL_USE;

  private double personalPropertyCoverageAmount = DEFAULT_PERSONAL_PROPERTY_COVERAGE_AMOUNT;
  private List<JSONObject> animals = DEFAULT_ANIMALS;

  private String civilLiabilityCoverageLimit = DEFAULT_CIVIL_LIABILITY_COVERAGE_LIMIT;

  private QuoteRequestBuilder() {}

  private static HashSet<String> createPreventionSystems() {
    int size = Faker.instance().number().numberBetween(0, PreventionSystem.values().length);
    List<PreventionSystem> sample = EnumSampler.sample(PreventionSystem.class, size);
    return sample.stream().map(Enum::toString).collect(Collectors.toCollection(HashSet::new));
  }

  private static double createCoverageAmount() {
    return Faker.instance().number().randomDouble(2, 8000, 100000);
  }

  private static List<JSONObject> createAnimals() {
    int randomDigit = Faker.instance().number().randomDigit();
    List<JSONObject> animals = new ArrayList<>();
    IntStream.range(0, randomDigit)
        .mapToObj(i -> new JSONObject())
        .forEach(
            animal -> {
              animal.put("breed", EnumSampler.sample(AnimalBreed.class));
              animal.put("quantity", Faker.instance().number().randomDigit());
              animals.add(animal);
            });
    return animals;
  }

  public static QuoteRequestBuilder aQuoteRequest() {
    return new QuoteRequestBuilder();
  }

  private String createBirthDate() {
    return new SimpleDateFormat("yyyy-MM-dd").format(Faker.instance().date().birthday());
  }

  private String createGender() {
    return EnumSampler.sample(Gender.class).toString();
  }

  private String createEffectiveDate() {
    return new SimpleDateFormat("yyyy-MM-dd")
        .format(Faker.instance().date().future(50, TimeUnit.DAYS));
  }

  public JSONObject build() {
    JSONObject json = new JSONObject();
    json.put("personalInformation", buildIdentity());
    json.put("additionalInsured", buildIdentity());
    json.put("location", buildLocation());
    json.put("effectiveDate", effectiveDate);
    json.put("building", buildBuilding());
    json.put("personalProperty", buildPersonalProperty());
    json.put("civilLiability", buildCivilLiability());
    return json;
  }

  private JSONObject buildIdentity() {
    JSONObject json = new JSONObject();
    json.put("firstName", firstName);
    json.put("lastName", lastName);
    json.put("birthDate", birthDate);
    json.put("gender", gender);
    json.put("universityProfile", buildUniversityProfile());
    return json;
  }

  private JSONObject buildUniversityProfile() {
    JSONObject json = new JSONObject();
    json.put("idul", idul);
    json.put("ni", identificationNumber);
    json.put("program", program);
    return json;
  }

  private JSONObject buildLocation() {
    JSONObject json = new JSONObject();
    json.put("zipCode", zipCode);
    json.put("streetNumber", streetNumber);
    json.put("apartmentNumber", apartmentNumber);
    json.put("floor", floor);
    return json;
  }

  private JSONObject buildBuilding() {
    JSONObject json = new JSONObject();
    json.put("numberOfUnits", numberOfUnits);
    json.put("preventionSystems", new JSONArray(preventionSystems));
    json.put("commercialUse", commercialUse);
    return json;
  }

  private JSONObject buildPersonalProperty() {
    JSONObject json = new JSONObject();
    json.put("coverageAmount", personalPropertyCoverageAmount);
    json.put("animals", new JSONArray(animals));
    return json;
  }

  private JSONObject buildCivilLiability() {
    JSONObject json = new JSONObject();
    json.put("limit", civilLiabilityCoverageLimit);
    return json;
  }
}
