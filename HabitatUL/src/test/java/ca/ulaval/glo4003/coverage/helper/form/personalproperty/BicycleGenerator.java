package ca.ulaval.glo4003.coverage.helper.form.personalproperty;

import ca.ulaval.glo4003.coverage.domain.form.personalproperty.Bicycle;
import ca.ulaval.glo4003.gateway.presentation.coverage.request.BicycleRequest;
import ca.ulaval.glo4003.shared.domain.money.Amount;
import com.github.javafaker.Faker;

import static ca.ulaval.glo4003.shared.helper.MoneyGenerator.createAmountGreaterThanZero;
import static ca.ulaval.glo4003.shared.helper.TemporalGenerator.createYear;

public class BicycleGenerator {
  private BicycleGenerator() {}

  public static BicycleRequest createBicycleRequest() {
    return new BicycleRequest(createBicyclePrice(), createBrand(), createModel(), createYear());
  }

  public static Bicycle createBicycle() {
    return new Bicycle(createBicyclePrice(), createBrand(), createModel(), createYear());
  }

  public static Amount createBicyclePrice() {
    return createAmountGreaterThanZero();
  }

  public static String createModel() {
    return Faker.instance().hitchhikersGuideToTheGalaxy().marvinQuote();
  }

  public static String createBrand() {
    return Faker.instance().hitchhikersGuideToTheGalaxy().character();
  }
}
