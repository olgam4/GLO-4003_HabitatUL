package ca.ulaval.glo4003.coverage.infrastructure.premium.formulapart.preferentialprogram;

import ca.ulaval.glo4003.coverage.domain.premium.adjustment.MultiplicativePremiumAdjustment;
import ca.ulaval.glo4003.coverage.domain.premium.adjustment.NullPremiumAdjustment;
import ca.ulaval.glo4003.coverage.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.shared.domain.handling.InvalidArgumentException;
import ca.ulaval.glo4003.shared.domain.identity.Cycle;
import ca.ulaval.glo4003.shared.domain.identity.Degree;
import ca.ulaval.glo4003.shared.infrastructure.io.JsonFileReader;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Arrays;
import java.util.Collection;

import static ca.ulaval.glo4003.coverage.infrastructure.premium.formulapart.preferentialprogram.JsonPreferentialProgramAdjustmentProvider.FILE_PATH;
import static ca.ulaval.glo4003.helper.shared.ParameterizedTestHelper.PARAMETERIZED_TEST_TITLE;
import static ca.ulaval.glo4003.shared.domain.identity.Cycle.*;
import static ca.ulaval.glo4003.shared.domain.identity.Degree.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(Parameterized.class)
public class JsonPreferentialProgramAdjustmentProviderTest {
  private static final String BACHELOR_PREFERENTIAL_PROGRAM = "bachelor preferential program";
  private static final float BACHELOR_ADJUSTMENT_VALUE = 5f;
  private static final PremiumAdjustment BACHELOR_ADJUSTMENT =
      new MultiplicativePremiumAdjustment(-BACHELOR_ADJUSTMENT_VALUE / 100);
  private static final String MASTER_PREFERENTIAL_PROGRAM = "master preferential program";
  private static final float MASTER_ADJUSTMENT_VALUE = 10f;
  private static final PremiumAdjustment MASTER_ADJUSTMENT =
      new MultiplicativePremiumAdjustment(-MASTER_ADJUSTMENT_VALUE / 100);
  private static final String DOCTORAL_PREFERENTIAL_PROGRAM = "doctoral preferential program";
  private static final float DOCTORAL_ADJUSTMENT_VALUE = 15;
  private static final PremiumAdjustment DOCTORAL_ADJUSTMENT =
      new MultiplicativePremiumAdjustment(-DOCTORAL_ADJUSTMENT_VALUE / 100);
  private static final String STANDARD_PROGRAM = "standard program";
  private static final NullPremiumAdjustment NULL_ADJUSTMENT = new NullPremiumAdjustment();

  @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();
  @Mock private JsonFileReader jsonFileReader;

  private JsonPreferentialProgramAdjustmentProvider subject;
  private Cycle cycle;
  private Degree degree;
  private String program;
  private PremiumAdjustment expectedAdjustment;

  public JsonPreferentialProgramAdjustmentProviderTest(
      String title,
      Cycle cycle,
      Degree degree,
      String program,
      PremiumAdjustment expectedAdjustment) {
    this.cycle = cycle;
    this.degree = degree;
    this.program = program;
    this.expectedAdjustment = expectedAdjustment;
  }

  @Parameterized.Parameters(name = PARAMETERIZED_TEST_TITLE)
  public static Collection parameters() {
    return Arrays.asList(
        new Object[][] {
          {
            "without program should compute null adjustment",
            null,
            null,
            null,
            new NullPremiumAdjustment()
          },
          {
            "with bachelor preferential program should compute associated adjustment",
            FIRST_CYCLE,
            BACHELOR,
            BACHELOR_PREFERENTIAL_PROGRAM,
            BACHELOR_ADJUSTMENT
          },
          {
            "with master preferential program should compute associated adjustment",
            SECOND_CYCLE,
            MASTER,
            MASTER_PREFERENTIAL_PROGRAM,
            MASTER_ADJUSTMENT
          },
          {
            "with doctoral preferential program should compute associated adjustment",
            THIRD_CYCLE,
            DOCTORAL,
            DOCTORAL_PREFERENTIAL_PROGRAM,
            DOCTORAL_ADJUSTMENT
          },
          {
            "without normalized program should compute associated adjustment",
            FIRST_CYCLE,
            BACHELOR,
            "bachelor-?%&*-préférENTial_progRAM",
            BACHELOR_ADJUSTMENT
          },
          {
            "with bachelor standard program should compute null adjustment",
            FIRST_CYCLE,
            BACHELOR,
            STANDARD_PROGRAM,
            NULL_ADJUSTMENT
          },
          {
            "with master standard program should compute null adjustment",
            SECOND_CYCLE,
            MASTER,
            STANDARD_PROGRAM,
            NULL_ADJUSTMENT
          },
          {
            "with doctoral standard program should compute null adjustment",
            THIRD_CYCLE,
            DOCTORAL,
            STANDARD_PROGRAM,
            NULL_ADJUSTMENT
          },
        });
  }

  @Before
  public void setUp() throws InvalidArgumentException {
    when(jsonFileReader.read(FILE_PATH)).thenReturn(FakeJsonFileContent());
    subject = new JsonPreferentialProgramAdjustmentProvider(jsonFileReader);
  }

  @Test
  public void gettingAdjustment() {
    PremiumAdjustment adjustment = subject.getAdjustment(cycle, degree, program);

    assertEquals(expectedAdjustment, adjustment);
  }

  private JSONObject FakeJsonFileContent() {
    JSONObject content = new JSONObject();
    JSONArray bachelorPreferentialPrograms =
        createPreferentialPrograms(BACHELOR_PREFERENTIAL_PROGRAM, BACHELOR_ADJUSTMENT_VALUE);
    JSONObject firstCycleDegrees =
        new JSONObject().put("baccalaureat", bachelorPreferentialPrograms);
    content.put("1er", firstCycleDegrees);
    JSONArray masterPreferentialPrograms =
        createPreferentialPrograms(MASTER_PREFERENTIAL_PROGRAM, MASTER_ADJUSTMENT_VALUE);
    JSONObject secondCycleDegrees = new JSONObject().put("maitrise", masterPreferentialPrograms);
    content.put("2e", secondCycleDegrees);
    JSONArray doctoralPreferentialPrograms =
        createPreferentialPrograms(DOCTORAL_PREFERENTIAL_PROGRAM, DOCTORAL_ADJUSTMENT_VALUE);
    JSONObject thirdCycleDegrees = new JSONObject().put("doctorat", doctoralPreferentialPrograms);
    content.put("3e", thirdCycleDegrees);
    return content;
  }

  private JSONArray createPreferentialPrograms(String program, float adjustmentValue) {
    JSONObject bachelorPreferentialProgram =
        new JSONObject().put("prog", program).put("rabais", adjustmentValue);
    return new JSONArray().put(bachelorPreferentialProgram);
  }
}
