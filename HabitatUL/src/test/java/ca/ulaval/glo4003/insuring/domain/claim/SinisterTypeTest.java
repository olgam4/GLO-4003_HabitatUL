package ca.ulaval.glo4003.insuring.domain.claim;

import ca.ulaval.glo4003.shared.domain.handling.InvalidArgumentException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SinisterTypeTest {
  @Test
  public void gettingEnum_withKnownSinisterType_shouldReturnCorrespondingSinisterType()
      throws InvalidArgumentException {
    assertEquals(SinisterType.FIRE, SinisterType.getEnum("FIRE"));
    assertEquals(SinisterType.THEFT, SinisterType.getEnum("THEFT"));
  }

  @Test
  public void gettingEnum_shouldBeCaseInsensitive() throws InvalidArgumentException {
    assertEquals(SinisterType.FIRE, SinisterType.getEnum("fIrE"));
  }

  @Test(expected = InvalidArgumentException.class)
  public void gettingEnum_withUnknownSinisterType_shouldThrow() throws InvalidArgumentException {
    SinisterType.getEnum("UNKNOWN_SINISTER_TYPE");
  }
}
