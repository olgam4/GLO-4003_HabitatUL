package ca.ulaval.glo4003.gateway.presentation.quote;

import ca.ulaval.glo4003.shared.domain.Gender;
import com.github.javafaker.Faker;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

public class QuoteRequestBodyBuilder {
  private static final String DEFAULT_FIRST_NAME = Faker.instance().funnyName().name();
  private static final String DEFAULT_LAST_NAME = Faker.instance().funnyName().name();
  private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
  private static final String DEFAULT_BIRTH_DATE =
      SIMPLE_DATE_FORMAT.format(Faker.instance().date().birthday());
  private static final String DEFAULT_GENDER = Gender.MALE.toString();

  private String firstName = DEFAULT_FIRST_NAME;
  private String lastName = DEFAULT_LAST_NAME;
  private String birthDate = DEFAULT_BIRTH_DATE;
  private String gender = DEFAULT_GENDER;

  private QuoteRequestBodyBuilder() {}

  public static QuoteRequestBodyBuilder aQuoteRequestView() {
    return new QuoteRequestBodyBuilder();
  }

  public QuoteRequestBodyBuilder withBirthDate(String birthDate) {
    this.birthDate = birthDate;
    return this;
  }

  public JSONObject build() {
    JSONObject json = new JSONObject();
    json.put("identity", buildIdentity());
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
}
