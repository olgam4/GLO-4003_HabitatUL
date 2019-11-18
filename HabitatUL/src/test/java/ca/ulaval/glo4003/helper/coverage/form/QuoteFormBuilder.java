package ca.ulaval.glo4003.helper.coverage.form;

import ca.ulaval.glo4003.coverage.domain.form.QuoteForm;
import ca.ulaval.glo4003.coverage.domain.form.building.Building;
import ca.ulaval.glo4003.coverage.domain.form.civilliability.CivilLiability;
import ca.ulaval.glo4003.coverage.domain.form.identity.Identity;
import ca.ulaval.glo4003.coverage.domain.form.location.Location;
import ca.ulaval.glo4003.coverage.domain.form.personalproperty.PersonalProperty;
import ca.ulaval.glo4003.shared.domain.temporal.Date;

import static ca.ulaval.glo4003.helper.coverage.form.building.BuildingGenerator.createBuilding;
import static ca.ulaval.glo4003.helper.coverage.form.civilliability.CivilLiabilityGenerator.createCivilLiability;
import static ca.ulaval.glo4003.helper.coverage.form.identity.IdentityGenerator.createIdentity;
import static ca.ulaval.glo4003.helper.coverage.form.location.LocationGenerator.createLocation;
import static ca.ulaval.glo4003.helper.coverage.form.personalproperty.PersonalPropertyGenerator.createPersonalProperty;
import static ca.ulaval.glo4003.helper.shared.TemporalGenerator.createFutureDate;

public class QuoteFormBuilder {
  private final Identity DEFAULT_PERSONAL_INFORMATION = createIdentity();
  private final Identity DEFAULT_ADDITIONAL_INSURED = createIdentity();
  private final Location DEFAULT_LOCATION = createLocation();
  private final Date DEFAULT_EFFECTIVE_DATE = createFutureDate();
  private final Building DEFAULT_BUILDING = createBuilding();
  private final PersonalProperty DEFAULT_PERSONAL_PROPERTY = createPersonalProperty();
  private final CivilLiability DEFAULT_CIVIL_LIABILITY = createCivilLiability();

  private Identity personalInformation = DEFAULT_PERSONAL_INFORMATION;
  private Identity additionalInsured = DEFAULT_ADDITIONAL_INSURED;
  private Location location = DEFAULT_LOCATION;
  private Date effectiveDate = DEFAULT_EFFECTIVE_DATE;
  private Building building = DEFAULT_BUILDING;
  private PersonalProperty personalProperty = DEFAULT_PERSONAL_PROPERTY;
  private CivilLiability civilLiability = DEFAULT_CIVIL_LIABILITY;

  private QuoteFormBuilder() {}

  public static QuoteFormBuilder aQuoteForm() {
    return new QuoteFormBuilder();
  }

  public QuoteFormBuilder withPersonalInformation(Identity personalInformation) {
    this.personalInformation = personalInformation;
    return this;
  }

  public QuoteFormBuilder withAdditionalInsured(Identity additionalInsured) {
    this.additionalInsured = additionalInsured;
    return this;
  }

  public QuoteFormBuilder withEffectiveDate(Date effectiveDate) {
    this.effectiveDate = effectiveDate;
    return this;
  }

  public QuoteFormBuilder withBuilding(Building building) {
    this.building = building;
    return this;
  }

  public QuoteFormBuilder withPersonalProperty(PersonalProperty personalProperty) {
    this.personalProperty = personalProperty;
    return this;
  }

  public QuoteFormBuilder withCivilLiability(CivilLiability civilLiability) {
    this.civilLiability = civilLiability;
    return this;
  }

  public QuoteForm build() {
    return new QuoteForm(
        personalInformation,
        additionalInsured,
        location,
        effectiveDate,
        building,
        personalProperty,
        civilLiability);
  }
}
