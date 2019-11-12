package ca.ulaval.glo4003.gateway.presentation.coverage.request;

import ca.ulaval.glo4003.shared.domain.money.Amount;
import ca.ulaval.glo4003.shared.domain.temporal.Year;

import javax.validation.constraints.NotNull;

public class BikeRequest {
  @NotNull private Amount price;
  @NotNull private String brand;
  @NotNull private String model;
  @NotNull private Year year;

  private BikeRequest() {}

  public BikeRequest(Amount price, String brand, String model, Year year) {
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
}
