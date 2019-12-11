package ca.ulaval.glo4003.shared.domain.identity;

import ca.ulaval.glo4003.shared.domain.handling.InvalidArgumentException;
import org.junit.Test;

import static ca.ulaval.glo4003.shared.domain.identity.Cycle.*;
import static org.junit.Assert.assertEquals;

public class CycleTest {
  @Test
  public void gettingEnum_withKnownCycle_shouldReturnCorrespondingCycle()
      throws InvalidArgumentException {
    assertEquals(FIRST_CYCLE, Cycle.getEnum("1er"));
    assertEquals(SECOND_CYCLE, Cycle.getEnum("2e"));
    assertEquals(THIRD_CYCLE, Cycle.getEnum("3e"));
  }

  @Test
  public void gettingEnum_shouldBeCaseInsensitive() throws InvalidArgumentException {
    assertEquals(FIRST_CYCLE, Cycle.getEnum("1ER"));
  }

  @Test(expected = InvalidArgumentException.class)
  public void gettingEnum_withUnknownCycle_shouldThrow() throws InvalidArgumentException {
    Cycle.getEnum("UNKNOWN_CYCLE");
  }
}
