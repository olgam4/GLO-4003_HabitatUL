package ca.ulaval.glo4003.generator.price;

import ca.ulaval.glo4003.underwriting.domain.price.Price;
import com.github.javafaker.Faker;

import java.math.BigDecimal;

public class PriceGenerator {
  public static Price create() {
    double randomDouble = Faker.instance().number().randomDouble(5, 0, 1000);
    return new Price(new BigDecimal(randomDouble));
  }
}
