package ca.ulaval.glo4003.helper.shared;

import ca.ulaval.glo4003.shared.domain.address.Floor;
import ca.ulaval.glo4003.shared.domain.address.ZipCode;
import ca.ulaval.glo4003.shared.domain.handling.InvalidArgumentException;
import com.github.javafaker.Faker;

public class AddressGenerator {
  private AddressGenerator() {}

  public static ZipCode createZipCode() {
    try {
      String zipCodeValue = Faker.instance().address().zipCode();
      return new ZipCode(zipCodeValue, zipCode -> zipCode);
    } catch (InvalidArgumentException e) {
      e.printStackTrace();
      return null;
    }
  }

  public static Floor createFloor() {
    try {
      Faker faker = Faker.instance();
      String floorValue = faker.lordOfTheRings().location();
      return new Floor(floorValue, floor -> faker.number().randomDigit());
    } catch (InvalidArgumentException e) {
      e.printStackTrace();
      return null;
    }
  }
}
