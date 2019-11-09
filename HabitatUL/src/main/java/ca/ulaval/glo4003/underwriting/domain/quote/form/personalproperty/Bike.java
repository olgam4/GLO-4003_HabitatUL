package ca.ulaval.glo4003.underwriting.domain.quote.form.personalproperty;

import ca.ulaval.glo4003.shared.domain.ValueObject;
import ca.ulaval.glo4003.shared.domain.money.Amount;
import ca.ulaval.glo4003.shared.domain.temporal.Year;

public class Bike extends ValueObject {
  public static final Bike UNFILLED_BIKE = new Bike(null, null, null, null);

  private final Amount price;
  private final String brand;
  private final String model;
  private final Year year;

  public Bike(Amount price, String brand, String model, Year year) {
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
    return !equals(UNFILLED_BIKE);
  }
}
