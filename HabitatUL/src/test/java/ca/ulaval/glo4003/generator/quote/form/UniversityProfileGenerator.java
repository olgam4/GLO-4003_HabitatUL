package ca.ulaval.glo4003.generator.quote.form;

import ca.ulaval.glo4003.gateway.presentation.quote.request.UniversityProfileRequest;
import ca.ulaval.glo4003.underwriting.domain.quote.form.identity.UniversityProfile;
import com.github.javafaker.Faker;

public class UniversityProfileGenerator {
  public static UniversityProfileRequest createUniversityProfileRequest() {
    return new UniversityProfileRequest(
        createIdul(), createIdentificationNumber(), createProgram());
  }

  public static UniversityProfile createUniversityProfile() {
    return new UniversityProfile(createIdentificationNumber(), createIdul(), createProgram());
  }

  private static String createIdul() {
    return Faker.instance().name().username();
  }

  private static String createIdentificationNumber() {
    return Faker.instance().idNumber().valid();
  }

  private static String createProgram() {
    return Faker.instance().cat().name();
  }
}
