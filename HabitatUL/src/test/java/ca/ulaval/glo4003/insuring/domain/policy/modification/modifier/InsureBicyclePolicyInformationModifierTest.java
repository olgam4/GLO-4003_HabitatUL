package ca.ulaval.glo4003.insuring.domain.policy.modification.modifier;

import ca.ulaval.glo4003.coverage.domain.form.personalproperty.Bicycle;
import ca.ulaval.glo4003.helper.policy.PolicyInformationBuilder;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyInformation;
import org.junit.Before;
import org.junit.Test;

import static ca.ulaval.glo4003.helper.coverage.form.personalproperty.BicycleGenerator.createBicycle;
import static ca.ulaval.glo4003.helper.policy.PolicyInformationGenerator.createPolicyInformation;
import static org.junit.Assert.assertEquals;

public class InsureBicyclePolicyInformationModifierTest {
  private static final Bicycle NEWLY_INSURED_BICYCLE = createBicycle();
  private static final PolicyInformation POLICY_INFORMATION = createPolicyInformation();

  private InsureBicyclePolicyInformationModifier subject;

  @Before
  public void setUp() {
    subject = new InsureBicyclePolicyInformationModifier(NEWLY_INSURED_BICYCLE);
  }

  @Test
  public void modifyingPolicyInformation_shouldProperlyUpdatePolicyInformation() {
    PolicyInformation modifiedPolicyInformation = subject.modify(POLICY_INFORMATION);

    PolicyInformation expectedPolicyInformation =
        PolicyInformationBuilder.aPolicyInformation()
            .withNamedInsured(POLICY_INFORMATION.getNamedInsuredIdentity())
            .withAdditionalInsured(POLICY_INFORMATION.getAdditionalInsuredIdentity())
            .withLocation(POLICY_INFORMATION.getLocation())
            .withBuilding(POLICY_INFORMATION.getBuilding())
            .withAnimals(POLICY_INFORMATION.getAnimals())
            .withBicycle(NEWLY_INSURED_BICYCLE)
            .build();
    assertEquals(expectedPolicyInformation, modifiedPolicyInformation);
  }
}
