package ca.ulaval.glo4003.generator.quote.form;

import ca.ulaval.glo4003.gateway.presentation.quote.request.IdentityRequest;
import ca.ulaval.glo4003.generator.EnumSampler;
import ca.ulaval.glo4003.shared.domain.temporal.Date;
import ca.ulaval.glo4003.underwriting.domain.quote.form.identity.Gender;
import ca.ulaval.glo4003.underwriting.domain.quote.form.identity.Identity;
import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.ZoneId;

public class IdentityGenerator {
  public static IdentityRequest createIdentityRequest() {
    return new IdentityRequest(
        createFirstName(), createLastName(), createBirthDate(), createGender());
  }

  public static Identity createIdentity() {
    return new Identity(createFirstName(), createLastName(), createBirthDate(), createGender());
  }

  private static String createFirstName() {
    return Faker.instance().name().firstName();
  }

  private static String createLastName() {
    return Faker.instance().name().lastName();
  }

  private static Date createBirthDate() {
    LocalDate localDate =
        Faker.instance().date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    return Date.from(localDate);
  }

  private static Gender createGender() {
    return EnumSampler.sample(Gender.class);
  }
}
