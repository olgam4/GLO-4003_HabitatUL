package ca.ulaval.glo4003.helper.premium;

import ca.ulaval.glo4003.calculator.domain.premium.input.UniversityProgramInput;
import com.github.javafaker.Faker;

public class UniversityProgramInputGenerator {
  private UniversityProgramInputGenerator() {}

  public static UniversityProgramInput create() {
    Faker faker = Faker.instance();
    return new UniversityProgramInput(
        faker.educator().course(), faker.educator().campus(), faker.educator().university());
  }

  public static UniversityProgramInput createUnfilled() {
    return new UniversityProgramInput(null, null, null);
  }
}
