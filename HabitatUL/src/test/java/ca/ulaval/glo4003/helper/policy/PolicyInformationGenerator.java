package ca.ulaval.glo4003.helper.policy;

import ca.ulaval.glo4003.insuring.domain.policy.PolicyInformation;

import static ca.ulaval.glo4003.helper.coverage.form.building.BuildingGenerator.createBuilding;
import static ca.ulaval.glo4003.helper.coverage.form.civilliability.CivilLiabilityGenerator.createCivilLiability;
import static ca.ulaval.glo4003.helper.coverage.form.identity.IdentityGenerator.createIdentity;
import static ca.ulaval.glo4003.helper.coverage.form.location.LocationGenerator.createLocation;
import static ca.ulaval.glo4003.helper.coverage.form.personalproperty.PersonalPropertyGenerator.createPersonalProperty;
import static ca.ulaval.glo4003.helper.shared.TemporalGenerator.createFutureDate;

public class PolicyInformationGenerator {
  private PolicyInformationGenerator() {}

  public static PolicyInformation createPolicyInformation() {
    return new PolicyInformation(
        createIdentity(),
        createIdentity(),
        createLocation(),
        createFutureDate(),
        createBuilding(),
        createPersonalProperty(),
        createCivilLiability());
  }
}
