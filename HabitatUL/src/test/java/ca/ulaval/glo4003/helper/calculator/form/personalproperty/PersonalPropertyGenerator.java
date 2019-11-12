package ca.ulaval.glo4003.helper.calculator.form.personalproperty;

import ca.ulaval.glo4003.calculator.domain.form.personalproperty.PersonalProperty;
import ca.ulaval.glo4003.gateway.presentation.quote.request.PersonalPropertyRequest;
import ca.ulaval.glo4003.shared.domain.money.Amount;

import static ca.ulaval.glo4003.helper.calculator.form.personalproperty.BikeGenerator.createBike;
import static ca.ulaval.glo4003.helper.calculator.form.personalproperty.BikeGenerator.createBikeRequest;
import static ca.ulaval.glo4003.helper.calculator.premium.QuotePremiumInputGenerator.createAnimals;
import static ca.ulaval.glo4003.helper.shared.MoneyGenerator.createAmountGreaterThanZero;

public class PersonalPropertyGenerator {
  private PersonalPropertyGenerator() {}

  public static PersonalPropertyRequest createPersonalPropertyRequest() {
    return new PersonalPropertyRequest(
        createCoverageAmount(), createAnimals(), createBikeRequest());
  }

  public static PersonalProperty createPersonalProperty() {
    return new PersonalProperty(createCoverageAmount(), createAnimals(), createBike());
  }

  public static Amount createCoverageAmount() {
    return createAmountGreaterThanZero();
  }
}
