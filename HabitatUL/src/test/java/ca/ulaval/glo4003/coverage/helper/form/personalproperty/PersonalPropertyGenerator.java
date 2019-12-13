package ca.ulaval.glo4003.coverage.helper.form.personalproperty;

import ca.ulaval.glo4003.coverage.domain.form.personalproperty.PersonalProperty;
import ca.ulaval.glo4003.gateway.presentation.coverage.request.PersonalPropertyRequest;
import ca.ulaval.glo4003.shared.domain.money.Amount;

import static ca.ulaval.glo4003.coverage.helper.form.personalproperty.BicycleGenerator.createBicycle;
import static ca.ulaval.glo4003.coverage.helper.form.personalproperty.BicycleGenerator.createBicycleRequest;
import static ca.ulaval.glo4003.coverage.helper.premium.QuotePremiumInputGenerator.createAnimals;
import static ca.ulaval.glo4003.shared.helper.MoneyGenerator.createAmountGreaterThanZero;

public class PersonalPropertyGenerator {
  private PersonalPropertyGenerator() {}

  public static PersonalPropertyRequest createPersonalPropertyRequest() {
    return new PersonalPropertyRequest(
        createCoverageAmount(), createAnimals(), createBicycleRequest());
  }

  public static PersonalProperty createPersonalProperty() {
    return new PersonalProperty(createCoverageAmount(), createAnimals(), createBicycle());
  }

  public static Amount createCoverageAmount() {
    return createAmountGreaterThanZero();
  }
}
