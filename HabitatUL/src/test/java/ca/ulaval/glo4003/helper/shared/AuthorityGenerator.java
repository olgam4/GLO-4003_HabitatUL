package ca.ulaval.glo4003.helper.shared;

import ca.ulaval.glo4003.shared.domain.authority.AuthorityNumber;
import ca.ulaval.glo4003.shared.domain.handling.InvalidArgumentException;
import ca.ulaval.glo4003.shared.domain.temporal.Date;
import com.github.javafaker.Faker;

public class AuthorityGenerator {
  private AuthorityGenerator() {}

  public static AuthorityNumber createAuthorityNumber() {
    try {
      Faker faker = Faker.instance();
      String authorityNumber = faker.commerce().productName();
      return new AuthorityNumber(
          authorityNumber, number -> authorityNumber, number -> TemporalGenerator.createDate());
    } catch (InvalidArgumentException e) {
      e.printStackTrace();
      return null;
    }
  }

  public static AuthorityNumber createAuthorityNumber(Date date) {
    try {
      Faker faker = Faker.instance();
      String authorityNumber = faker.commerce().productName();
      return new AuthorityNumber(authorityNumber, number -> authorityNumber, number -> date);
    } catch (InvalidArgumentException e) {
      e.printStackTrace();
      return null;
    }
  }
}
