package ca.ulaval.glo4003.underwriting.infrastructure.quote.form.location;

import ca.ulaval.glo4003.shared.domain.handling.InvalidArgumentException;
import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UsCanadianConventionFloorFormatterTest {
  private static final String INVALID_FLOOR = "INVALID_FLOOR";
  private UsCanadianConventionFloorFormatter subject;

  @Before
  public void setUp() {
    subject = new UsCanadianConventionFloorFormatter();
  }

  @Test
  public void formattingFloor_withValidFloor_shouldFormatFloor() throws InvalidArgumentException {
    int higherStories = Faker.instance().number().numberBetween(4, 100);
    String higherStoriesRepresentation = String.format("%sTH", higherStories);
    assertCorrespondingFormattedFloor(higherStories, higherStoriesRepresentation);
    assertCorrespondingFormattedFloor(2, "2ND");
    assertCorrespondingFormattedFloor(1, "1ST");
    assertCorrespondingFormattedFloor(1, "L");
    assertCorrespondingFormattedFloor(1, "G");
    assertCorrespondingFormattedFloor(1, "RC");
    assertCorrespondingFormattedFloor(0, "LL");
    int lowerStories = Faker.instance().number().numberBetween(-100, -1);
    String lowerStoriesRepresentation = String.format("B%s", Math.abs(lowerStories));
    assertCorrespondingFormattedFloor(lowerStories, lowerStoriesRepresentation);
  }

  @Test(expected = InvalidArgumentException.class)
  public void formattingFloor_withInvalidFloor_shouldThrow() throws InvalidArgumentException {
    subject.format(INVALID_FLOOR);
  }

  @Test(expected = InvalidArgumentException.class)
  public void formattingFloor_withInvalidHigherFloor_shouldThrow() throws InvalidArgumentException {
    subject.format("1TH");
  }

  @Test(expected = InvalidArgumentException.class)
  public void formattingFloor_withoutNumberedHigherFloor_shouldThrow()
      throws InvalidArgumentException {
    subject.format("INVALID-TH");
  }

  @Test(expected = InvalidArgumentException.class)
  public void formattingFloor_withInvalidLowerFloor_shouldThrow() throws InvalidArgumentException {
    subject.format("B0");
  }

  @Test(expected = InvalidArgumentException.class)
  public void formattingFloor_withoutNumberedLowerFloor_shouldThrow()
      throws InvalidArgumentException {
    subject.format("B-INVALID");
  }

  private void assertCorrespondingFormattedFloor(int expectedFloor, String floor)
      throws InvalidArgumentException {
    assertEquals(expectedFloor, subject.format(floor));
  }
}
