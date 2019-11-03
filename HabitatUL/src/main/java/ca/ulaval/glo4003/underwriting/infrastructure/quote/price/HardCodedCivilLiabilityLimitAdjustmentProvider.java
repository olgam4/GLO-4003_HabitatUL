package ca.ulaval.glo4003.underwriting.infrastructure.quote.price;

import ca.ulaval.glo4003.underwriting.domain.quote.form.civilliability.CivilLiabilityLimit;
import ca.ulaval.glo4003.underwriting.domain.quote.price.CivilLiabilityLimitAdjustmentProvider;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.MultiplicativeQuotePriceAdjustment;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.NullQuotePriceAdjustment;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.QuotePriceAdjustment;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

public class HardCodedCivilLiabilityLimitAdjustmentProvider
    implements CivilLiabilityLimitAdjustmentProvider {
  private static final Map<CivilLiabilityLimit, Float> LOOKUP_MAP =
      new EnumMap<>(CivilLiabilityLimit.class);

  static {
    LOOKUP_MAP.put(CivilLiabilityLimit.TWO_MILLION, 0.25f);
  }

  @Override
  public QuotePriceAdjustment getAdjustment(CivilLiabilityLimit civilLiabilityLimit) {
    return Optional.ofNullable(LOOKUP_MAP.get(civilLiabilityLimit))
        .map(x -> (QuotePriceAdjustment) new MultiplicativeQuotePriceAdjustment(x))
        .orElse(new NullQuotePriceAdjustment());
  }
}
