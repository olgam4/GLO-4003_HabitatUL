package ca.ulaval.glo4003.helper.claim;

import ca.ulaval.glo4003.coverage.application.claim.ClaimAssembler;
import ca.ulaval.glo4003.coverage.application.claim.dto.ClaimDto;
import ca.ulaval.glo4003.coverage.domain.claim.*;
import ca.ulaval.glo4003.gateway.presentation.policy.request.ClaimRequest;
import ca.ulaval.glo4003.helper.EnumSampler;
import ca.ulaval.glo4003.shared.domain.money.Amount;
import com.github.javafaker.Faker;

import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static ca.ulaval.glo4003.helper.MoneyGenerator.createAmountGreaterThan;

public class ClaimGenerator {
  private ClaimGenerator() {}

  public static ClaimRequest createClaimRequest() {
    return new ClaimRequest(createSinisterType(), createLossDeclarations());
  }

  public static ClaimDto createClaimDto() {
    return new ClaimAssembler().from(createClaim());
  }

  public static Claim createClaim() {
    return new Claim(
        createClaimId(), createClaimStatus(), createSinisterType(), createLossDeclarations());
  }

  public static ClaimId createClaimId() {
    return new ClaimId();
  }

  public static ClaimStatus createClaimStatus() {
    return EnumSampler.sample(ClaimStatus.class);
  }

  public static SinisterType createSinisterType() {
    return EnumSampler.sample(SinisterType.class);
  }

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
}
