package ca.ulaval.glo4003.gateway.presentation.quote.request;

import ca.ulaval.glo4003.shared.domain.money.Amount;
import ca.ulaval.glo4003.underwriting.domain.quote.form.personalproperty.Animals;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public class PersonalPropertyRequest {
  @NotNull private Amount coverageAmount;
  private Animals animals;

  private PersonalPropertyRequest() {}

  public PersonalPropertyRequest(Amount coverageAmount, Animals animals) {
    this.coverageAmount = coverageAmount;
    this.animals = animals;
  }

  public Amount getCoverageAmount() {
    return coverageAmount;
  }

  public Optional<Animals> getAnimals() {
    return Optional.ofNullable(animals);
  }
}
