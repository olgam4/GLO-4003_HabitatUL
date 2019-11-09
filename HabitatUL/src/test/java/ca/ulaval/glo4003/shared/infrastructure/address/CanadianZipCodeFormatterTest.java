package ca.ulaval.glo4003.shared.infrastructure.address;

import ca.ulaval.glo4003.shared.domain.handling.InvalidArgumentException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CanadianZipCodeFormatterTest {
  private static final String ZIP_CODE = "g3A 0g4";
  private static final String FORMATTED_ZIP_CODE = "G3A0G4";
  private static final String INVALID_ZIP_CODE = "INVALID_ZIP_CODE";

  private CanadianZipCodeFormatter subject;

  @Before
  public void setUp() {
    subject = new CanadianZipCodeFormatter();
  }

  @Test
  public void formattingZipCode_withValidZipCode_shouldFormatZipCode()
      throws InvalidArgumentException {
    String observed = subject.format(ZIP_CODE);

    assertEquals(FORMATTED_ZIP_CODE, observed);
  }

  @Test(expected = InvalidArgumentException.class)
  public void formattingZipCode_withInvalidZipCode_shouldThrow() throws InvalidArgumentException {
    subject.format(INVALID_ZIP_CODE);
  }
}
