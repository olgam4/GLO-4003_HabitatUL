package ca.ulaval.glo4003.coverage.domain.claim;

import ca.ulaval.glo4003.helper.claim.LossDeclarationsBuilder;
import ca.ulaval.glo4003.shared.domain.money.Amount;
import org.junit.Before;
import org.junit.Test;

import static ca.ulaval.glo4003.helper.MoneyGenerator.createAmount;
import static org.junit.Assert.assertEquals;

public class LossDeclarationsTest {
  private static final LossCategory LOSS_CATEGORY = LossCategory.COMPUTER_EQUIPMENT;
  private static final Amount LOSS_AMOUNT = createAmount();
  private static final LossCategory ANOTHER_LOSS_CATEGORY = LossCategory.OTHER;
  private static final Amount ANOTHER_LOSS_AMOUNT = createAmount();
  private LossDeclarations subject;

  @Before
  public void setUp() {
    subject =
        LossDeclarationsBuilder.aLossDeclaration()
            .withLoss(LOSS_CATEGORY, LOSS_AMOUNT)
            .withLoss(ANOTHER_LOSS_CATEGORY, ANOTHER_LOSS_AMOUNT)
            .build();
  }

  @Test
  public void computingTotalLosses_shouldReturnCumulativeValueOfAllLosses() {
    Amount totalLosses = subject.computeTotalLosses();

    Amount expectedTotalLosses = LOSS_AMOUNT.add(ANOTHER_LOSS_AMOUNT);
    assertEquals(expectedTotalLosses, totalLosses);
  }
}
