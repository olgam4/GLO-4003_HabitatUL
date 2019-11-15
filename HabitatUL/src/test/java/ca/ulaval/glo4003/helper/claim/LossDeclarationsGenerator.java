package ca.ulaval.glo4003.helper.claim;

import ca.ulaval.glo4003.helper.shared.EnumSampler;
import ca.ulaval.glo4003.insuring.domain.claim.LossCategory;
import ca.ulaval.glo4003.insuring.domain.claim.LossDeclarations;
import ca.ulaval.glo4003.shared.domain.money.Amount;
import com.github.javafaker.Faker;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static ca.ulaval.glo4003.helper.shared.MoneyGenerator.createAmountGreaterThan;
import static ca.ulaval.glo4003.insuring.domain.claim.LossCategory.BICYCLE;

public class LossDeclarationsGenerator {
  private LossDeclarationsGenerator() {}

  public static LossDeclarations createLossDeclarations() {
    HashMap<LossCategory, Amount> lossDeclarationsMap =
        IntStream.range(0, Faker.instance().number().randomDigitNotZero())
            .boxed()
            .collect(
                Collectors.toMap(
                    i -> createLossCategory(),
                    i -> createAmountGreaterThan(Amount.ZERO),
                    (a, b) -> b,
                    HashMap::new));
    return new LossDeclarations(lossDeclarationsMap);
  }

  public static LossCategory createLossCategory() {
    return EnumSampler.sample(LossCategory.class);
  }

  public static LossCategory createPersonalPropertyLossCategory() {
    return createLossCategory(Arrays.asList(BICYCLE));
  }

  public static LossCategory createLossCategory(List<LossCategory> exceptions) {
    return EnumSampler.sample(LossCategory.class, exceptions);
  }
}
