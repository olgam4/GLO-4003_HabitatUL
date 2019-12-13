package ca.ulaval.glo4003.coverage.helper.form;

import ca.ulaval.glo4003.coverage.domain.form.QuoteForm;

import static ca.ulaval.glo4003.coverage.helper.form.building.BuildingGenerator.createBuilding;
import static ca.ulaval.glo4003.coverage.helper.form.civilliability.CivilLiabilityGenerator.createCivilLiability;
import static ca.ulaval.glo4003.coverage.helper.form.identity.IdentityGenerator.createIdentity;
import static ca.ulaval.glo4003.coverage.helper.form.location.LocationGenerator.createLocation;
import static ca.ulaval.glo4003.coverage.helper.form.personalproperty.PersonalPropertyGenerator.createPersonalProperty;
import static ca.ulaval.glo4003.shared.helper.TemporalGenerator.createFutureDate;

public class QuoteFormGenerator {
  private QuoteFormGenerator() {}

  public static QuoteForm createQuoteForm() {
    return new QuoteForm(
        createIdentity(),
        createIdentity(),
        createLocation(),
        createFutureDate(),
        createBuilding(),
        createPersonalProperty(),
        createCivilLiability());
  }
}
