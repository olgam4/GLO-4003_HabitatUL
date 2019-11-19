package ca.ulaval.glo4003.insuring.domain.policy.modification.modifier;

import ca.ulaval.glo4003.insuring.domain.policy.PolicyInformation;
import org.junit.Before;
import org.junit.Test;

import static ca.ulaval.glo4003.helper.policy.PolicyInformationGenerator.createPolicyInformation;
import static org.junit.Assert.assertEquals;

public class NoImpactPolicyInformationModifierTest {
  private static final PolicyInformation POLICY_INFORMATION = createPolicyInformation();

  private NoImpactPolicyInformationModifier subject;

  @Before
  public void setUp() {
    subject = new NoImpactPolicyInformationModifier();
  }

  @Test
  public void modifyingPolicyInformation_shouldProperlyUpdatePolicyInformation() {
    PolicyInformation modifiedPolicyInformation = subject.modify(POLICY_INFORMATION);

    assertEquals(POLICY_INFORMATION, modifiedPolicyInformation);
  }
}
