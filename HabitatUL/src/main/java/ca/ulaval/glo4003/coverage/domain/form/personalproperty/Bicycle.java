package ca.ulaval.glo4003.coverage.domain.form.personalproperty;

import ca.ulaval.glo4003.shared.domain.ValueObject;
import ca.ulaval.glo4003.shared.domain.money.Amount;
import ca.ulaval.glo4003.shared.domain.temporal.Year;

public class Bicycle extends ValueObject {
  public static final Bicycle UNFILLED_BICYCLE = new Bicycle(null, null, null, null);

  private final Amount price;
  private final String brand;
  private final String model;
  private final Year year;

  public Bicycle(Amount price, String brand, String model, Year year) {
    this.price = price;
    this.brand = brand;
    this.model = model;
    this.year = year;
  }

  public Amount getPrice() {
    return price;
  }

  public String getBrand() {
    return brand;
  }

  public String getModel() {
    return model;
  }

  public Year getYear() {
    return year;
  }

  public boolean isFilled() {
    return !equals(UNFILLED_BICYCLE);
  }
}
