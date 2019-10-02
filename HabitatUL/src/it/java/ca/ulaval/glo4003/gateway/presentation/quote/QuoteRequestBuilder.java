package ca.ulaval.glo4003.gateway.presentation.quote;

import ca.ulaval.glo4003.underwriting.domain.quote.form.building.PreventionSystem;
import ca.ulaval.glo4003.underwriting.domain.quote.form.identity.Gender;
import com.github.javafaker.Faker;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

;

public class QuoteRequestBuilder {
  private static final String DEFAULT_FIRST_NAME = Faker.instance().funnyName().name();
  private static final String DEFAULT_LAST_NAME = Faker.instance().funnyName().name();
  private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
  private static final String DEFAULT_BIRTH_DATE =
      SIMPLE_DATE_FORMAT.format(Faker.instance().date().birthday());
  private static final String DEFAULT_GENDER = Gender.MALE.toString();

  private static final String DEFAULT_POSTAL_CODE = "G1V4L8";
  private static final int DEFAULT_STREET_NUMBER = 13;
  private static final int DEFAULT_APARTMENT_NUMBER = 2;
  private static final String DEFAULT_FLOOR = "1ST";

  private static final String DEFAULT_EFFECTIVE_DATE =
      SIMPLE_DATE_FORMAT.format(Faker.instance().date().future(50, TimeUnit.DAYS));

  private static final int DEFAULT_NUMBER_OF_UNITS = 12;
  private static final Set<String> DEFAULT_PREVENTION_SYSTEMS =
      new HashSet<String>(Arrays.asList(PreventionSystem.CENTRAL_ALARM.toString()));

  private String firstName = DEFAULT_FIRST_NAME;
  private String lastName = DEFAULT_LAST_NAME;
  private String birthDate = DEFAULT_BIRTH_DATE;
  private String gender = DEFAULT_GENDER;

  private String postalCode = DEFAULT_POSTAL_CODE;
  private int streetNumber = DEFAULT_STREET_NUMBER;
  private int apartmentNumber = DEFAULT_APARTMENT_NUMBER;
  private String floor = DEFAULT_FLOOR;

  private String effectiveDate = DEFAULT_EFFECTIVE_DATE;

  private int numberOfUnits = DEFAULT_NUMBER_OF_UNITS;
  private Set<String> preventionSystems = DEFAULT_PREVENTION_SYSTEMS;

  private QuoteRequestBuilder() {}

  public static QuoteRequestBuilder aQuoteRequest() {
    return new QuoteRequestBuilder();
  }

  public QuoteRequestBuilder withBirthDate(String birthDate) {
    this.birthDate = birthDate;
    return this;
  }

  public JSONObject build() {
    JSONObject json = new JSONObject();
    json.put("identity", buildIdentity());
    json.put("location", buildLocation());
    json.put("effectiveDate", effectiveDate);
    json.put("building", buildBuilding());
    return json;
  }

  private JSONObject buildIdentity() {
    JSONObject json = new JSONObject();
    json.put("firstName", firstName);
    json.put("lastName", lastName);
    json.put("birthDate", birthDate);
    json.put("gender", gender);
    return json;
  }

  private JSONObject buildLocation() {
    JSONObject json = new JSONObject();
    json.put("postalCode", postalCode);
    json.put("streetNumber", streetNumber);
    json.put("apartmentNumber", apartmentNumber);
    json.put("floor", floor);
    return json;
  }

  private JSONObject buildBuilding() {
    JSONObject json = new JSONObject();
    json.put("numberOfUnits", numberOfUnits);
    json.put("preventionSystems", new JSONArray(preventionSystems));
    json.put("commercialUse", "other");
    return json;
  }
}
