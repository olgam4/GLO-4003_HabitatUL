package ca.ulaval.glo4003.helper.quote.form;

import ca.ulaval.glo4003.gateway.presentation.quote.request.PersonalPropertyRequest;
import ca.ulaval.glo4003.shared.domain.money.Amount;
import ca.ulaval.glo4003.underwriting.domain.quote.form.personalproperty.PersonalProperty;

import static ca.ulaval.glo4003.helper.MoneyGenerator.createAmountGreaterThanZero;
import static ca.ulaval.glo4003.helper.calculator.BikeGenerator.createBike;
import static ca.ulaval.glo4003.helper.calculator.BikeGenerator.createBikeRequest;
import static ca.ulaval.glo4003.helper.calculator.QuotePremiumInputGenerator.createAnimals;

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
