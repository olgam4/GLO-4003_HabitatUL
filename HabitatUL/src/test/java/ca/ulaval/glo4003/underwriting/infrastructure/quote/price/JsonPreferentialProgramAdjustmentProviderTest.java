package ca.ulaval.glo4003.underwriting.infrastructure.quote.price;

import ca.ulaval.glo4003.shared.infrastructure.JsonFileReader;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.MultiplicativeQuotePriceAdjustment;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.NoQuotePriceAdjustment;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.QuotePriceAdjustment;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static ca.ulaval.glo4003.underwriting.infrastructure.quote.price.JsonPreferentialProgramAdjustmentProvider.FILE_PATH;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class JsonPreferentialProgramAdjustmentProviderTest {
  private static final String PREFERENTIAL_PROGRAM = "PREFERENTIAL_PROGRAM";
  private static final double PREFERENTIAL_PROGRAM_ADJUSTMENT_FACTOR = 0.15;
  private static final String STANDARD_PROGRAM = "STANDARD_PROGRAM";

  @Mock private JsonFileReader jsonFileReader;

  private JsonPreferentialProgramAdjustmentProvider subject;

  @Before
  public void setUp() {
    when(jsonFileReader.read(FILE_PATH)).thenReturn(FakeJsonFileContent());
    subject = new JsonPreferentialProgramAdjustmentProvider(jsonFileReader);
  }

  @Test
  public void gettingAdjustment_withPreferentialProgram_shouldProvideCorrespondingAdjustment() {
    QuotePriceAdjustment adjustment = subject.getAdjustment(PREFERENTIAL_PROGRAM);

    QuotePriceAdjustment expectedAdjustment =
        new MultiplicativeQuotePriceAdjustment(PREFERENTIAL_PROGRAM_ADJUSTMENT_FACTOR);
    assertEquals(expectedAdjustment, adjustment);
  }

  @Test
  public void gettingAdjustment_withStandardProgram_shouldProvideNoAdjustment() {
    QuotePriceAdjustment adjustment = subject.getAdjustment(STANDARD_PROGRAM);

    QuotePriceAdjustment expectedAdjustment = new NoQuotePriceAdjustment();
    assertEquals(expectedAdjustment, adjustment);
  }

  private JSONObject FakeJsonFileContent() {
    JSONObject content = new JSONObject();
    content.put(PREFERENTIAL_PROGRAM, PREFERENTIAL_PROGRAM_ADJUSTMENT_FACTOR);
    return content;
  }
}
