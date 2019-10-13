package ca.ulaval.glo4003.underwriting.domain.quote.form.identity;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GenderTest {
  @Test
  public void gettingEnum_withKnownGender_shouldReturnCorrespondingGender() {
    assertEquals(Gender.MALE, Gender.getEnum("MALE"));
    assertEquals(Gender.FEMALE, Gender.getEnum("FEMALE"));
  }

  @Test
  public void gettingEnum_shouldBeCaseInsensitive() {
    assertEquals(Gender.MALE, Gender.getEnum("mALe"));
  }

  @Test
  public void gettingEnum_withUnknownGender_shouldReturnDefaultValue() {
    assertEquals(Gender.OTHER, Gender.getEnum("UNKNOWN_GENDER"));
  }
}
