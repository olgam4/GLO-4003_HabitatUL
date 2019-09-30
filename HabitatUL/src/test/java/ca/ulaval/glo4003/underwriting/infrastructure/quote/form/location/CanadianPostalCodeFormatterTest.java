package ca.ulaval.glo4003.underwriting.infrastructure.quote.form.location;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.underwriting.domain.quote.form.location.InvalidPostalCodeException;

import static org.junit.Assert.assertEquals;

public class CanadianPostalCodeFormatterTest {
  private static final String VALID_POSTAL_CODE = "g3A 0g4";
  private static final String INVALID_POSTAL_CODE = "invalid postal code";

  private CanadianPostalCodeFormatter subject;

  @Before
  public void setUp() {
    subject = new CanadianPostalCodeFormatter();
  }

  @Test
  public void formattingPostalCode_withValidPostalCode_shouldFormatPostalCode() {
    String observed = subject.format(VALID_POSTAL_CODE);

    String expected = "G3A0G4";
    assertEquals(expected, observed);
  }

  @Test(expected = InvalidPostalCodeException.class)
  public void formattingPostalCode_withInvalidPostalCode_shouldThrow() {
    subject.format(INVALID_POSTAL_CODE);
  }
}
