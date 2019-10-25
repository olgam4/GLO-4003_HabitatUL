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
public class ZipCodeTest {
  private static final String ZIP_CODE = Faker.instance().address().zipCode();
  private static final String FORMATTED_ZIP_CODE = Faker.instance().address().zipCode();

  @Mock private ZipCodeFormatter zipCodeFormatter;

  @Before
  public void setUp() throws InvalidArgumentException {
    when(zipCodeFormatter.format(any(String.class))).thenReturn(FORMATTED_ZIP_CODE);
  }

  @Test
  public void creatingZipCode_shouldFormatZipCodeUsingZipCodeFormatter()
      throws InvalidArgumentException {
    new ZipCode(ZIP_CODE, zipCodeFormatter);

    verify(zipCodeFormatter).format(ZIP_CODE);
  }

  @Test
  public void gettingZipCodeValue_shouldReturnFormattedZipCode() throws InvalidArgumentException {
    ZipCode zipCode = new ZipCode(ZIP_CODE, zipCodeFormatter);

    assertEquals(FORMATTED_ZIP_CODE, zipCode.getValue());
  }
}
