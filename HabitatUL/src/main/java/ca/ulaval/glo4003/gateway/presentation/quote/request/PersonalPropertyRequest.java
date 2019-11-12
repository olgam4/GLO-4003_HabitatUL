package ca.ulaval.glo4003.gateway.presentation.quote.request;

import ca.ulaval.glo4003.coverage.domain.form.personalproperty.Animals;
import ca.ulaval.glo4003.gateway.presentation.coverage.request.BicycleRequest;
import ca.ulaval.glo4003.shared.domain.money.Amount;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

public class PersonalPropertyRequest {
  @NotNull private Amount coverageAmount;
  private Animals animals;
  @Valid private BicycleRequest bicycle;

  private PersonalPropertyRequest() {}

  public PersonalPropertyRequest(Amount coverageAmount, Animals animals, BicycleRequest bicycle) {
    this.coverageAmount = coverageAmount;
    this.animals = animals;
    this.bicycle = bicycle;
  }

  public Amount getCoverageAmount() {
    return coverageAmount;
  }

  public Optional<Animals> getAnimals() {
    return Optional.ofNullable(animals);
  }

  public Optional<BicycleRequest> getBicycle() {
    return Optional.ofNullable(bicycle);
  }
}
