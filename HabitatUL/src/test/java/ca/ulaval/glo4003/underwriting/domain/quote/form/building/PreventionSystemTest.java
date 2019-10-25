package ca.ulaval.glo4003.underwriting.domain.quote.form.building;

import ca.ulaval.glo4003.shared.domain.handling.InvalidArgumentException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PreventionSystemTest {
  @Test
  public void gettingEnum_withKnownPreventionSystem_shouldReturnCorrespondingPreventionSystem()
      throws InvalidArgumentException {
    assertEquals(PreventionSystem.CENTRAL_ALARM, PreventionSystem.getEnum("CENTRAL_ALARM"));
    assertEquals(PreventionSystem.SPRINKLER, PreventionSystem.getEnum("SPRINKLER"));
  }

  @Test
  public void gettingEnum_shouldBeCaseInsensitive() throws InvalidArgumentException {
    assertEquals(PreventionSystem.CENTRAL_ALARM, PreventionSystem.getEnum("cenTRal_ALArm"));
  }

  @Test(expected = InvalidArgumentException.class)
  public void gettingEnum_withUnknownPreventionSystem_shouldThrow()
      throws InvalidArgumentException {
    PreventionSystem.getEnum("UNKNOWN_PREVENTION_SYSTEM");
  }
}
