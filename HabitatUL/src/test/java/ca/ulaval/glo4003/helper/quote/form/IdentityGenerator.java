package ca.ulaval.glo4003.helper.quote.form;

import ca.ulaval.glo4003.gateway.presentation.quote.request.IdentityRequest;
import ca.ulaval.glo4003.helper.EnumSampler;
import ca.ulaval.glo4003.shared.domain.temporal.Date;
import ca.ulaval.glo4003.underwriting.domain.quote.form.identity.Gender;
import ca.ulaval.glo4003.underwriting.domain.quote.form.identity.Identity;
import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.ZoneId;

import static ca.ulaval.glo4003.helper.quote.form.UniversityProfileGenerator.createUniversityProfile;
import static ca.ulaval.glo4003.helper.quote.form.UniversityProfileGenerator.createUniversityProfileRequest;

public class IdentityGenerator {
  private IdentityGenerator() {}

  public static IdentityRequest createIdentityRequest() {
    return new IdentityRequest(
        createFirstName(),
        createLastName(),
        createBirthDate(),
        createGender(),
        createUniversityProfileRequest());
  }

  public static Identity createIdentity() {
    return new Identity(
        createFirstName(),
        createLastName(),
        createBirthDate(),
        createGender(),
        createUniversityProfile());
  }

  public static String createFirstName() {
    return Faker.instance().name().firstName();
  }

  public static String createLastName() {
    return Faker.instance().name().lastName();
  }

  public static Date createBirthDate() {
    LocalDate localDate =
        Faker.instance().date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    return Date.from(localDate);
  }

  public static Gender createGender() {
    return EnumSampler.sample(Gender.class);
  }
}
