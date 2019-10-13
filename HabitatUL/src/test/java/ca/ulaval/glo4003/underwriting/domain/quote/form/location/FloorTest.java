package ca.ulaval.glo4003.underwriting.domain.quote.form.location;

import ca.ulaval.glo4003.shared.domain.InvalidArgumentException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FloorTest {
  private Floor subject;

  @Test
  public void parsingFloor_withGroundFloorValue_shouldIndicateProperFloorNumber()
      throws InvalidArgumentException {
    subject = new Floor("RC");

    assertEquals(0, subject.getFloorNumber());
  }

  @Test
  public void parsingFloor_withFirstFloorValue_shouldIndicateProperFloorNumber()
      throws InvalidArgumentException {
    subject = new Floor("1ST");

    assertEquals(1, subject.getFloorNumber());
  }

  @Test
  public void parsingFloor_withSecondFloorValue_shouldIndicateProperFloorNumber()
      throws InvalidArgumentException {
    subject = new Floor("2ND");

    assertEquals(2, subject.getFloorNumber());
  }

  @Test
  public void parsingFloor_withThirdFloorValue_shouldIndicateProperFloorNumber()
      throws InvalidArgumentException {
    subject = new Floor("3RD");

    assertEquals(3, subject.getFloorNumber());
  }

  @Test
  public void parsingFloor_withSubsequentFloorValue_shouldIndicateProperFloorNumber()
      throws InvalidArgumentException {
    int floorNumber = 571;

    subject = new Floor(String.format("%sTH", floorNumber));

    assertEquals(floorNumber, subject.getFloorNumber());
  }

  @Test
  public void parsingFloor_withBasementFloorValue_shouldIndicateProperFloorNumber()
      throws InvalidArgumentException {
    int floorNumber = 221;

    subject = new Floor(String.format("SS%s", floorNumber));

    assertEquals(-floorNumber, subject.getFloorNumber());
  }

  @Test(expected = InvalidArgumentException.class)
  public void parsingFloor_withInvalidFloor_shouldThrow() throws InvalidArgumentException {
    new Floor("invalid floor");
  }

  @Test(expected = InvalidArgumentException.class)
  public void parsingFloor_withBasementNumberInferiorTo1() throws InvalidArgumentException {
    new Floor("SS0");
  }

  @Test(expected = InvalidArgumentException.class)
  public void parsingFloor_withSubsequentFloorNumberInferiorTo3_shouldThrow()
      throws InvalidArgumentException {
    new Floor("2TH");
  }
}
