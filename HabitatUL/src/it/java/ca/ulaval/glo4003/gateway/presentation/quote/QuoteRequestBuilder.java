package ca.ulaval.glo4003.gateway.presentation.quote;

import ca.ulaval.glo4003.underwriting.domain.quote.form.identity.Gender;
import com.github.javafaker.Faker;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

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

  private String firstName = DEFAULT_FIRST_NAME;
  private String lastName = DEFAULT_LAST_NAME;
  private String birthDate = DEFAULT_BIRTH_DATE;
  private String gender = DEFAULT_GENDER;

  private String postalCode = DEFAULT_POSTAL_CODE;
  private int streetNumber = DEFAULT_STREET_NUMBER;
  private int apartmentNumber = DEFAULT_APARTMENT_NUMBER;
  private String floor = DEFAULT_FLOOR;

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
}
