package ca.ulaval.glo4003.helper.quote.form;

import ca.ulaval.glo4003.gateway.presentation.quote.request.UniversityProfileRequest;
import ca.ulaval.glo4003.underwriting.domain.quote.form.identity.UniversityProfile;
import com.github.javafaker.Faker;

import static ca.ulaval.glo4003.helper.calculator.UniversityProgramGenerator.createUniversityProgram;
import static ca.ulaval.glo4003.helper.calculator.UniversityProgramGenerator.createUniversityProgramRequest;

public class UniversityProfileGenerator {
  private UniversityProfileGenerator() {}

  public static UniversityProfileRequest createUniversityProfileRequest() {
    return new UniversityProfileRequest(createIdul(), createNi(), createUniversityProgramRequest());
  }

  public static UniversityProfile createUniversityProfile() {
    return new UniversityProfile(createNi(), createIdul(), createUniversityProgram());
  }

  public static String createIdul() {
    return Faker.instance().name().username();
  }

  public static String createNi() {
    return Faker.instance().idNumber().valid();
  }
}
