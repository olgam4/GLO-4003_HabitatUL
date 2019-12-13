package ca.ulaval.glo4003.insuring.helper.policy;

import ca.ulaval.glo4003.insuring.domain.policy.PolicyInformation;

import static ca.ulaval.glo4003.coverage.helper.form.building.BuildingGenerator.createBuilding;
import static ca.ulaval.glo4003.coverage.helper.form.identity.IdentityGenerator.createIdentity;
import static ca.ulaval.glo4003.coverage.helper.form.location.LocationGenerator.createLocation;
import static ca.ulaval.glo4003.coverage.helper.form.personalproperty.BicycleGenerator.createBicycle;
import static ca.ulaval.glo4003.coverage.helper.premium.QuotePremiumInputGenerator.createAnimals;

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
