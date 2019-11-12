package ca.ulaval.glo4003.helper.calculator.form.identity;

import ca.ulaval.glo4003.calculator.domain.form.identity.UniversityProgram;
import ca.ulaval.glo4003.gateway.presentation.quote.request.UniversityProgramRequest;
import com.github.javafaker.Faker;

public class UniversityProgramGenerator {
  private UniversityProgramGenerator() {}

  public static UniversityProgramRequest createUniversityProgramRequest() {
    return new UniversityProgramRequest(createCycle(), createDegree(), createMajor());
  }

  public static UniversityProgram createUniversityProgram() {
    return new UniversityProgram(createCycle(), createDegree(), createMajor());
  }

  public static String createCycle() {
    return Faker.instance().educator().course();
  }

  public static String createDegree() {
    return Faker.instance().educator().campus();
  }

  public static String createMajor() {
    return Faker.instance().educator().university();
  }
}
