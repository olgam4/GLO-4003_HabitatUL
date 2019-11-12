package ca.ulaval.glo4003.helper.coverage.form.personalproperty;

import ca.ulaval.glo4003.coverage.domain.form.personalproperty.Bike;
import ca.ulaval.glo4003.gateway.presentation.coverage.request.BikeRequest;
import ca.ulaval.glo4003.shared.domain.money.Amount;
import com.github.javafaker.Faker;

import static ca.ulaval.glo4003.helper.shared.MoneyGenerator.createAmountGreaterThanZero;
import static ca.ulaval.glo4003.helper.shared.TemporalGenerator.createYear;

public class BikeGenerator {
  private BikeGenerator() {}

  public static BikeRequest createBikeRequest() {
    return new BikeRequest(createBikePrice(), createBrand(), createModel(), createYear());
  }

  public static Bike createBike() {
    return new Bike(createBikePrice(), createBrand(), createModel(), createYear());
  }

  public static Amount createBikePrice() {
    return createAmountGreaterThanZero();
  }

  public static String createModel() {
    return Faker.instance().hitchhikersGuideToTheGalaxy().marvinQuote();
  }

  public static String createBrand() {
    return Faker.instance().hitchhikersGuideToTheGalaxy().character();
  }
}
