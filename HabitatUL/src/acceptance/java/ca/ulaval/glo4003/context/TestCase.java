package ca.ulaval.glo4003.context;

import ca.ulaval.glo4003.helper.quote.form.*;
import ca.ulaval.glo4003.shared.domain.temporal.Date;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteFormDto;
import com.github.javafaker.Faker;

import java.time.LocalDate;

public class TestCase {
  public static QuoteFormDto getValidQuoteFormDto() {
    return new QuoteFormDto(
        IdentityGenerator.createIdentity(),
        IdentityGenerator.createIdentity(),
        LocationGenerator.createLocation(),
        Date.from(LocalDate.now()),
        BuildingBuilder.aBuilding()
            .withNumberOfUnits(Faker.instance().number().numberBetween(4, 50))
            .build(),
        PersonalPropertyGenerator.createPersonalProperty(),
        CivilLiabilityGenerator.createCivilLiability());
  }
}
