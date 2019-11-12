package ca.ulaval.glo4003.helper.quote.form;

import ca.ulaval.glo4003.gateway.presentation.quote.request.BikeRequest;
import ca.ulaval.glo4003.helper.TemporalGenerator;
import ca.ulaval.glo4003.shared.domain.money.Amount;
import ca.ulaval.glo4003.underwriting.domain.quote.form.personalproperty.Bike;
import com.github.javafaker.Faker;

import static ca.ulaval.glo4003.helper.MoneyGenerator.createAmountGreaterThanZero;

public class BikeGenerator {
  private BikeGenerator() {}

  public static BikeRequest createBikeRequest() {
    return new BikeRequest(
        createBikePrice(), createBrand(), createModel(), TemporalGenerator.createYear());
  }

  public static Bike createBike() {
    return new Bike(
        createBikePrice(), createBrand(), createModel(), TemporalGenerator.createYear());
  }

  private static Amount createBikePrice() {
    return createAmountGreaterThanZero();
  }

  public static String createModel() {
    return Faker.instance().hitchhikersGuideToTheGalaxy().marvinQuote();
  }

  public static String createBrand() {
    return Faker.instance().hitchhikersGuideToTheGalaxy().character();
  }
}
