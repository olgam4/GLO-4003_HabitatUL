package ca.ulaval.glo4003.coverage.helper.form.personalproperty;

import ca.ulaval.glo4003.coverage.domain.form.personalproperty.Bicycle;
import ca.ulaval.glo4003.shared.domain.money.Amount;
import ca.ulaval.glo4003.shared.domain.temporal.Year;

import static ca.ulaval.glo4003.coverage.helper.form.personalproperty.BicycleGenerator.*;
import static ca.ulaval.glo4003.shared.helper.TemporalGenerator.createYear;

public class BicycleBuilder {
  private final Amount DEFAULT_PRICE = createBicyclePrice();
  private final String DEFAULT_BRAND = createBrand();
  private final String DEFAULT_MODEL = createModel();
  private final Year DEFAULT_YEAR = createYear();

  private Amount price = DEFAULT_PRICE;
  private String brand = DEFAULT_BRAND;
  private String model = DEFAULT_MODEL;
  private Year year = DEFAULT_YEAR;

  private BicycleBuilder() {}

  public static BicycleBuilder aBicycle() {
    return new BicycleBuilder();
  }

  public BicycleBuilder withPrice(Amount price) {
    this.price = price;
    return this;
  }

  public Bicycle build() {
    return new Bicycle(price, brand, model, year);
  }
}
