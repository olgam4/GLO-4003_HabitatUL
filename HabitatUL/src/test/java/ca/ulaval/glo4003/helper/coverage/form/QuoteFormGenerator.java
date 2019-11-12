package ca.ulaval.glo4003.helper.coverage.form;

import ca.ulaval.glo4003.coverage.domain.form.QuoteForm;

import static ca.ulaval.glo4003.helper.coverage.form.building.BuildingGenerator.createBuilding;
import static ca.ulaval.glo4003.helper.coverage.form.civilliability.CivilLiabilityGenerator.createCivilLiability;
import static ca.ulaval.glo4003.helper.coverage.form.identity.IdentityGenerator.createIdentity;
import static ca.ulaval.glo4003.helper.coverage.form.location.LocationGenerator.createLocation;
import static ca.ulaval.glo4003.helper.coverage.form.personalproperty.PersonalPropertyGenerator.createPersonalProperty;
import static ca.ulaval.glo4003.helper.shared.TemporalGenerator.createFutureDate;

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
