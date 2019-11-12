package ca.ulaval.glo4003.helper.calculator.form.identity;

import ca.ulaval.glo4003.calculator.domain.form.identity.UniversityProfile;
import ca.ulaval.glo4003.gateway.presentation.quote.request.UniversityProfileRequest;
import com.github.javafaker.Faker;

import static ca.ulaval.glo4003.helper.calculator.form.identity.UniversityProgramGenerator.createUniversityProgram;
import static ca.ulaval.glo4003.helper.calculator.form.identity.UniversityProgramGenerator.createUniversityProgramRequest;

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
