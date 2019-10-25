package ca.ulaval.glo4003.underwriting.infrastructure.quote.price;

import ca.ulaval.glo4003.shared.infrastructure.JsonFileReader;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.MultiplicativeQuotePriceAdjustment;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.NoQuotePriceAdjustment;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.QuotePriceAdjustment;
import org.json.JSONArray;
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
  private static final String FIRST_CYCLE = "1er";
  private static final String BACHELOR = "baccalaureat";
  private static final String BACHELOR_PREFERENTIAL_PROGRAM = "bachelor preferential program";
  private static final int BACHELOR_ADJUSTMENT_VALUE = 5;
  private static final String SECOND_CYCLE = "2e";
  private static final String MASTER = "maitrise";
  private static final String MASTER_PREFERENTIAL_PROGRAM = "master preferential program";
  private static final int MASTER_ADJUSTMENT_VALUE = 10;
  private static final String THIRD_CYCLE = "3e";
  private static final String DOCTORAL = "doctorat";
  private static final String DOCTORAL_PREFERENTIAL_PROGRAM = "doctoral preferential program";
  private static final int DOCTORAL_ADJUSTMENT_VALUE = 15;
  private static final String STANDARD_PROGRAM = "standard program";

  @Mock private JsonFileReader jsonFileReader;

  private JsonPreferentialProgramAdjustmentProvider subject;

  @Before
  public void setUp() {
    when(jsonFileReader.read(FILE_PATH)).thenReturn(FakeJsonFileContent());
    subject = new JsonPreferentialProgramAdjustmentProvider(jsonFileReader);
  }

  @Test
  public void gettingAdjustment_withPreferentialProgram_shouldProvideCorrespondingAdjustment() {
    validateScenario(
        FIRST_CYCLE, BACHELOR, BACHELOR_PREFERENTIAL_PROGRAM, BACHELOR_ADJUSTMENT_VALUE);
    validateScenario(SECOND_CYCLE, MASTER, MASTER_PREFERENTIAL_PROGRAM, MASTER_ADJUSTMENT_VALUE);
    validateScenario(
        THIRD_CYCLE, DOCTORAL, DOCTORAL_PREFERENTIAL_PROGRAM, DOCTORAL_ADJUSTMENT_VALUE);
  }

  @Test
  public void gettingAdjustment_shouldBeNormalized() {
    validateScenario("1ER", BACHELOR, BACHELOR_PREFERENTIAL_PROGRAM, BACHELOR_ADJUSTMENT_VALUE);
    validateScenario(
        FIRST_CYCLE, "baCCalauréat", BACHELOR_PREFERENTIAL_PROGRAM, BACHELOR_ADJUSTMENT_VALUE);
    validateScenario(
        FIRST_CYCLE, BACHELOR, "bachelor-?%&*-préférENTial_progRAM", BACHELOR_ADJUSTMENT_VALUE);
  }

  private void validateScenario(
      String cycle, String degree, String program, Integer expectedAdjustmentValue) {
    QuotePriceAdjustment adjustment = subject.getAdjustment(cycle, degree, program);

    QuotePriceAdjustment expectedAdjustment =
        new MultiplicativeQuotePriceAdjustment(-expectedAdjustmentValue.floatValue() / 100);
    assertEquals(expectedAdjustment, adjustment);
  }

  @Test
  public void gettingAdjustment_withStandardProgram_shouldProvideNoAdjustment() {
    validateNoAdjustmentScenario(FIRST_CYCLE, BACHELOR, STANDARD_PROGRAM);
    validateNoAdjustmentScenario(SECOND_CYCLE, MASTER, STANDARD_PROGRAM);
    validateNoAdjustmentScenario(THIRD_CYCLE, DOCTORAL, STANDARD_PROGRAM);
  }

  private void validateNoAdjustmentScenario(String cycle, String degree, String program) {
    QuotePriceAdjustment adjustment = subject.getAdjustment(cycle, degree, program);

    QuotePriceAdjustment expectedAdjustment = new NoQuotePriceAdjustment();
    assertEquals(expectedAdjustment, adjustment);
  }

  private JSONObject FakeJsonFileContent() {
    JSONObject content = new JSONObject();
    JSONArray bachelorPreferentialPrograms =
        createPreferentialPrograms(BACHELOR_PREFERENTIAL_PROGRAM, BACHELOR_ADJUSTMENT_VALUE);
    JSONObject firstCycleDegrees = new JSONObject().put(BACHELOR, bachelorPreferentialPrograms);
    content.put(FIRST_CYCLE, firstCycleDegrees);
    JSONArray masterPreferentialPrograms =
        createPreferentialPrograms(MASTER_PREFERENTIAL_PROGRAM, MASTER_ADJUSTMENT_VALUE);
    JSONObject secondCycleDegrees = new JSONObject().put(MASTER, masterPreferentialPrograms);
    content.put(SECOND_CYCLE, secondCycleDegrees);
    JSONArray doctoralPreferentialPrograms =
        createPreferentialPrograms(DOCTORAL_PREFERENTIAL_PROGRAM, DOCTORAL_ADJUSTMENT_VALUE);
    JSONObject thirdCycleDegrees = new JSONObject().put(DOCTORAL, doctoralPreferentialPrograms);
    content.put(THIRD_CYCLE, thirdCycleDegrees);
    return content;
  }

  private JSONArray createPreferentialPrograms(String program, int adjustmentValue) {
    JSONObject bachelorPreferentialProgram =
        new JSONObject().put("prog", program).put("rabais", adjustmentValue);
    return new JSONArray().put(bachelorPreferentialProgram);
  }
}
