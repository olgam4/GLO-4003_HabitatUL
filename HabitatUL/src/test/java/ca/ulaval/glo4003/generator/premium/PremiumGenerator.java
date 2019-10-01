package ca.ulaval.glo4003.generator.premium;

import ca.ulaval.glo4003.underwriting.domain.premium.Premium;
import com.github.javafaker.Faker;

import java.math.BigDecimal;

public class PremiumGenerator {
  public static Premium create() {
    double randomDouble = Faker.instance().number().randomDouble(5, 0, 1000);
    return new Premium(new BigDecimal(randomDouble));
  }
}