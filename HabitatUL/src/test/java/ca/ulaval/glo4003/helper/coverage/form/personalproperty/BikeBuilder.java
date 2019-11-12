package ca.ulaval.glo4003.helper.coverage.form.personalproperty;

import ca.ulaval.glo4003.coverage.domain.form.personalproperty.Bike;
import ca.ulaval.glo4003.shared.domain.money.Amount;
import ca.ulaval.glo4003.shared.domain.temporal.Year;

import static ca.ulaval.glo4003.helper.coverage.form.personalproperty.BikeGenerator.*;
import static ca.ulaval.glo4003.helper.shared.TemporalGenerator.createYear;

public class BikeBuilder {
  private static final Amount DEFAULT_PRICE = createBikePrice();
  private static final String DEFAULT_BRAND = createBrand();
  private static final String DEFAULT_MODEL = createModel();
  private static final Year DEFAULT_YEAR = createYear();

  private Amount price = DEFAULT_PRICE;
  private String brand = DEFAULT_BRAND;
  private String model = DEFAULT_MODEL;
  private Year year = DEFAULT_YEAR;

  private BikeBuilder() {}

  public static BikeBuilder aBike() {
    return new BikeBuilder();
  }

  public BikeBuilder withPrice(Amount price) {
    this.price = price;
    return this;
  }

  public Bike build() {
    return new Bike(price, brand, model, year);
  }
}
