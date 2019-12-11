package ca.ulaval.glo4003.shared.domain.identity;

import ca.ulaval.glo4003.shared.domain.handling.InvalidArgumentException;
import org.junit.Test;

import static ca.ulaval.glo4003.shared.domain.identity.Degree.*;
import static org.junit.Assert.assertEquals;

public class DegreeTest {
  @Test
  public void gettingEnum_withKnownCycle_shouldReturnCorrespondingDegree()
      throws InvalidArgumentException {
    assertEquals(BACHELOR, Degree.getEnum("baccalaureat"));
    assertEquals(MASTER, Degree.getEnum("maitrise"));
    assertEquals(DOCTORAL, Degree.getEnum("doctorat"));
  }

  @Test
  public void gettingEnum_shouldBeCaseInsensitive() throws InvalidArgumentException {
    assertEquals(BACHELOR, Degree.getEnum("BACCAlaureat"));
  }

  @Test(expected = InvalidArgumentException.class)
  public void gettingEnum_withUnknownDegree_shouldThrow() throws InvalidArgumentException {
    Degree.getEnum("UNKNOWN_DEGREE");
  }
}
