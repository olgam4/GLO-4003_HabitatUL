package ca.ulaval.glo4003.helper.coverage.form.identity;

import ca.ulaval.glo4003.coverage.domain.form.identity.UniversityProgram;
import ca.ulaval.glo4003.gateway.presentation.coverage.request.UniversityProgramRequest;
import ca.ulaval.glo4003.helper.shared.EnumSampler;
import ca.ulaval.glo4003.shared.domain.identity.Cycle;
import ca.ulaval.glo4003.shared.domain.identity.Degree;
import com.github.javafaker.Faker;

public class UniversityProgramGenerator {
  private UniversityProgramGenerator() {}

  public static UniversityProgramRequest createUniversityProgramRequest() {
    return new UniversityProgramRequest(createCycle(), createDegree(), createMajor());
  }

  public static UniversityProgram createUniversityProgram() {
    return new UniversityProgram(createCycle(), createDegree(), createMajor());
  }

  public static Cycle createCycle() {
    return EnumSampler.sample(Cycle.class);
  }

  public static Degree createDegree() {
    return EnumSampler.sample(Degree.class);
  }

  public static String createMajor() {
    return Faker.instance().educator().university();
  }
}
