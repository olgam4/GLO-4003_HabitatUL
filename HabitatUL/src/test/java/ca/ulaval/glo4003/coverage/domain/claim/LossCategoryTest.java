package ca.ulaval.glo4003.coverage.domain.claim;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LossCategoryTest {
  @Test
  public void gettingEnum_withKnownLossCategory_shouldReturnCorrespondingLossCategory() {
    assertEquals(LossCategory.BICYCLE, LossCategory.getEnum("BICYCLE"));
    assertEquals(LossCategory.CLOTHES, LossCategory.getEnum("CLOTHES"));
    assertEquals(LossCategory.COMPUTER_EQUIPMENT, LossCategory.getEnum("COMPUTER_EQUIPMENT"));
    assertEquals(LossCategory.ELECTRONICS, LossCategory.getEnum("ELECTRONICS"));
    assertEquals(
        LossCategory.FURNITURE_AND_HOUSEHOLD, LossCategory.getEnum("FURNITURE_AND_HOUSEHOLD"));
  }

  @Test
  public void gettingEnum_shouldBeCaseInsensitive() {
    assertEquals(LossCategory.BICYCLE, LossCategory.getEnum("biCYCle"));
  }

  @Test
  public void gettingEnum_withUnknownLossCategory_shouldReturnDefaultValue() {
    assertEquals(LossCategory.OTHER, LossCategory.getEnum("UNKNOWN_LOSS_CATEGORY"));
  }
}
