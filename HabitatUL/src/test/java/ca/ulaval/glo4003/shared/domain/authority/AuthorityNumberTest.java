package ca.ulaval.glo4003.shared.domain.authority;

import org.junit.Before;
import org.junit.Test;

import static ca.ulaval.glo4003.helper.shared.AuthorityGenerator.createAuthorityNumber;
import static ca.ulaval.glo4003.shared.domain.authority.AuthorityNumber.UNFILLED_AUTHORITY_NUMBER;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AuthorityNumberTest {
  private AuthorityNumber subject;

  @Before
  public void setUp() {
    subject = createAuthorityNumber();
  }

  @Test
  public void checkingIfAuthorityNumberIsFilled_withFilledAuthorityNumber_shouldBeTrue() {
    assertTrue(subject.isFilled());
  }

  @Test
  public void checkingIfAuthorityNumberIsFilled_withUnfilledAuthorityNumber_shouldBeFalse() {
    subject = UNFILLED_AUTHORITY_NUMBER;

    assertFalse(subject.isFilled());
  }
}
