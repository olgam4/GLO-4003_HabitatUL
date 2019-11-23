package ca.ulaval.glo4003.shared.domain.authority;

import ca.ulaval.glo4003.shared.domain.handling.InvalidArgumentException;
import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static ca.ulaval.glo4003.helper.shared.ParameterizedTestHelper.PARAMETERIZED_TEST_TITLE;

@RunWith(Enclosed.class)
public class SPVQAuthorityNumberFormatterTest {
  private static final String VALUE_NOT_RESPECTING_FORMAT = Faker.instance().internet().uuid();

  public static class ValidTestCase extends TestCase {
    private static String VALID_FORMAT = "QUE101010001";

    public ValidTestCase() {
      super(VALID_FORMAT);
    }

    @Test
    public void formatting() throws InvalidArgumentException {
      super.formattingAuthorityNumber();
    }
  }

  @RunWith(Parameterized.class)
  public static class InvalidFormats extends TestCase {
    public InvalidFormats(String title, String authorityNumber) {
      super(authorityNumber);
    }

    @Parameterized.Parameters(name = PARAMETERIZED_TEST_TITLE)
    public static Collection parameters() {
      return Arrays.asList(
          new Object[][] {
            {"with value not respecting format should throw", VALUE_NOT_RESPECTING_FORMAT},
            {"with value not respecting length should throw", "aaaaaaaaaaaaaaaa"},
            {"with value not respecting prefix should throw", "ONT010101001"},
            {"with value having invalid date should throw", "QUE999999001"},
            {"with value having impossible date should throw", "QUE010229001"},
            {"with value having invalid counter should throw", "QUE010101a01"}
          });
    }

    @Test(expected = InvalidArgumentException.class)
    public void formattingAnInvalidAuthorityNumber_shouldThrow() throws InvalidArgumentException {
      super.formattingAuthorityNumber();
    }
  }

  public abstract static class TestCase {
    private String authorityNumber;

    private SpvqAuthorityNumberFormatter subject;

    public TestCase(String authorityNumber) {
      this.authorityNumber = authorityNumber;
    }

    @Before
    public void setUp() {
      subject = new SpvqAuthorityNumberFormatter();
    }

    public void formattingAuthorityNumber() throws InvalidArgumentException {
      subject.format(authorityNumber);
    }
  }
}
