package ca.ulaval.glo4003.insuring.domain.claim;

import ca.ulaval.glo4003.insuring.helper.claim.LossDeclarationsBuilder;
import ca.ulaval.glo4003.shared.domain.money.Amount;
import ca.ulaval.glo4003.shared.helper.MoneyGenerator;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static ca.ulaval.glo4003.insuring.domain.claim.LossCategory.BICYCLE;
import static ca.ulaval.glo4003.insuring.helper.claim.LossDeclarationsGenerator.createLossCategory;
import static ca.ulaval.glo4003.insuring.helper.claim.LossDeclarationsGenerator.createPersonalPropertyLossCategory;
import static ca.ulaval.glo4003.shared.helper.MoneyGenerator.createAmount;
import static org.junit.Assert.*;

public class LossDeclarationsTest {
  private static final LossCategory PERSONAL_PROPERTY_LOSS_CATEGORY =
      createPersonalPropertyLossCategory();
  private static final Amount LOSS_AMOUNT = createAmount();
  private static final LossCategory ANOTHER_LOSS_CATEGORY =
      createLossCategory(Arrays.asList(BICYCLE, PERSONAL_PROPERTY_LOSS_CATEGORY));
  private static final Amount ANOTHER_LOSS_AMOUNT = createAmount();
  private static final LossCategory EXCLUDED_LOSS_CATEGORY =
      createLossCategory(Arrays.asList(PERSONAL_PROPERTY_LOSS_CATEGORY, ANOTHER_LOSS_CATEGORY));

  private LossDeclarations subject;

  @Before
  public void setUp() {
    subject =
        LossDeclarationsBuilder.aLossDeclaration()
            .withLoss(PERSONAL_PROPERTY_LOSS_CATEGORY, LOSS_AMOUNT)
            .withLoss(ANOTHER_LOSS_CATEGORY, ANOTHER_LOSS_AMOUNT)
            .build();
  }

  @Test
  public void checkingIfLossDeclarationsIsEmpty_withEmptyLossDeclaration_shouldBeTrue() {
    subject = LossDeclarationsBuilder.aLossDeclaration().build();

    assertTrue(subject.isEmpty());
  }

  @Test
  public void checkingIfLossDeclarationsIsEmpty_withNotEmptyLossDeclaration_shouldBeFalse() {
    assertFalse(subject.isEmpty());
  }

  @Test
  public void
      checkingIfLossDeclarationsIncludesLossCategory_withIncludedLossCategory_shouldBeTrue() {
    assertTrue(subject.includes(PERSONAL_PROPERTY_LOSS_CATEGORY));
  }

  @Test
  public void
      checkingIfLossDeclarationsIncludesLossCategory_withExcludedLossCategory_shouldBeFalse() {
    assertFalse(subject.includes(EXCLUDED_LOSS_CATEGORY));
  }

  @Test
  public void gettingLossAmount_withIncludedLossCategory_shouldReturnCorrespondingAmount() {
    Amount lossAmount = subject.getLossAmount(PERSONAL_PROPERTY_LOSS_CATEGORY);

    assertEquals(LOSS_AMOUNT, lossAmount);
  }

  @Test
  public void gettingLossAmount_withExcludedLossCategory_shouldReturnNullAmount() {
    Amount lossAmount = subject.getLossAmount(EXCLUDED_LOSS_CATEGORY);

    assertEquals(Amount.ZERO, lossAmount);
  }

  @Test
  public void computingPersonalPropertyLosses_shouldReturnCumulativeValueOfAllAssociatedLosses() {
    subject =
        LossDeclarationsBuilder.aLossDeclaration()
            .withLoss(PERSONAL_PROPERTY_LOSS_CATEGORY, LOSS_AMOUNT)
            .withLoss(ANOTHER_LOSS_CATEGORY, ANOTHER_LOSS_AMOUNT)
            .withLoss(BICYCLE, MoneyGenerator.createAmountGreaterThanZero())
            .build();

    Amount personalPropertyLosses = subject.computePersonalPropertyLosses();

    Amount expectedLosses = LOSS_AMOUNT.add(ANOTHER_LOSS_AMOUNT);
    assertEquals(expectedLosses, personalPropertyLosses);
  }
}
