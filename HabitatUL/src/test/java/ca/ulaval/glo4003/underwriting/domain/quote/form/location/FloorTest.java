package ca.ulaval.glo4003.underwriting.domain.quote.form.location;

import ca.ulaval.glo4003.shared.domain.handling.InvalidArgumentException;
import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FloorTest {
  private static final String FLOOR = Faker.instance().address().buildingNumber();
  private static final int FORMATTED_FLOOR = Faker.instance().number().randomDigit();

  @Mock private FloorFormatter floorFormatter;

  @Before
  public void setUp() throws InvalidArgumentException {
    when(floorFormatter.format(any(String.class))).thenReturn(FORMATTED_FLOOR);
  }

  @Test
  public void creatingFloor_shouldFormatFloorUsingFloorFormatter() throws InvalidArgumentException {
    new Floor(FLOOR, floorFormatter);

    verify(floorFormatter).format(FLOOR);
  }

  @Test
  public void gettingFloorValue_shouldReturnFormattedFloor() throws InvalidArgumentException {
    Floor floor = new Floor(FLOOR, floorFormatter);

    assertEquals(FORMATTED_FLOOR, floor.getValue());
  }
}
