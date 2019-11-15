package ca.ulaval.glo4003.helper.policy;

import ca.ulaval.glo4003.coverage.domain.form.building.Building;
import ca.ulaval.glo4003.coverage.domain.form.civilliability.CivilLiability;
import ca.ulaval.glo4003.coverage.domain.form.identity.Identity;
import ca.ulaval.glo4003.coverage.domain.form.location.Location;
import ca.ulaval.glo4003.coverage.domain.form.personalproperty.PersonalProperty;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyInformation;
import ca.ulaval.glo4003.shared.domain.temporal.Date;

import static ca.ulaval.glo4003.helper.coverage.form.building.BuildingGenerator.createBuilding;
import static ca.ulaval.glo4003.helper.coverage.form.civilliability.CivilLiabilityGenerator.createCivilLiability;
import static ca.ulaval.glo4003.helper.coverage.form.identity.IdentityGenerator.createIdentity;
import static ca.ulaval.glo4003.helper.coverage.form.location.LocationGenerator.createLocation;
import static ca.ulaval.glo4003.helper.coverage.form.personalproperty.PersonalPropertyGenerator.createPersonalProperty;
import static ca.ulaval.glo4003.helper.shared.TemporalGenerator.createFutureDate;

public class PolicyInformationBuilder {
  private static final Identity DEFAULT_NAMED_INSURED = createIdentity();
  private static final Identity DEFAULT_ADDITIONAL_INSURED = createIdentity();
  private static final Location DEFAULT_LOCATION = createLocation();
  private static final Date DEFAULT_EFFECTIVE_DATE = createFutureDate();
  private static final Building DEFAULT_BUILDING = createBuilding();
  private static final PersonalProperty DEFAULT_PERSONAL_PROPERTY = createPersonalProperty();
  private static final CivilLiability DEFAULT_CIVIL_LIABILITY = createCivilLiability();

  private Identity namedInsured = DEFAULT_NAMED_INSURED;
  private Identity additionalInsured = DEFAULT_ADDITIONAL_INSURED;
  private Location location = DEFAULT_LOCATION;
  private Date effectiveDate = DEFAULT_EFFECTIVE_DATE;
  private Building building = DEFAULT_BUILDING;
  private PersonalProperty personalProperty = DEFAULT_PERSONAL_PROPERTY;
  private CivilLiability civilLiability = DEFAULT_CIVIL_LIABILITY;

  private PolicyInformationBuilder() {}

  public static PolicyInformationBuilder aPolicyInformation() {
    return new PolicyInformationBuilder();
  }

  public PolicyInformationBuilder withPersonalProperty(PersonalProperty personalProperty) {
    this.personalProperty = personalProperty;
    return this;
  }

  public PolicyInformation build() {
    return new PolicyInformation(
        namedInsured,
        additionalInsured,
        location,
        effectiveDate,
        building,
        personalProperty,
        civilLiability);
  }
}
