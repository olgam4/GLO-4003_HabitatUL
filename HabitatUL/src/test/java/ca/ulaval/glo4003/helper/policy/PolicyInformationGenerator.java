package ca.ulaval.glo4003.helper.policy;

import ca.ulaval.glo4003.insuring.domain.policy.PolicyInformation;

import static ca.ulaval.glo4003.helper.coverage.form.building.BuildingGenerator.createBuilding;
import static ca.ulaval.glo4003.helper.coverage.form.identity.IdentityGenerator.createIdentity;
import static ca.ulaval.glo4003.helper.coverage.form.location.LocationGenerator.createLocation;
import static ca.ulaval.glo4003.helper.coverage.form.personalproperty.BicycleGenerator.createBicycle;
import static ca.ulaval.glo4003.helper.coverage.premium.QuotePremiumInputGenerator.createAnimals;

public class PolicyInformationGenerator {
  private PolicyInformationGenerator() {}

  public static PolicyInformation createPolicyInformation() {
    return new PolicyInformation(
        createIdentity(),
        createIdentity(),
        createLocation(),
        createBuilding(),
        createAnimals(),
        createBicycle());
  }
}
