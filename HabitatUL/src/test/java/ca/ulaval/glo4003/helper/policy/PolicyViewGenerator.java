package ca.ulaval.glo4003.helper.policy;

import ca.ulaval.glo4003.insuring.domain.policy.historic.PolicyView;
import ca.ulaval.glo4003.shared.domain.temporal.Date;
import ca.ulaval.glo4003.shared.domain.temporal.Period;
import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static ca.ulaval.glo4003.helper.coverage.coverage.CoverageDetailsGenerator.createCoverageDetails;
import static ca.ulaval.glo4003.helper.coverage.premium.PremiumDetailsGenerator.createPremiumDetails;
import static ca.ulaval.glo4003.helper.policy.PolicyInformationGenerator.createPolicyInformation;
import static ca.ulaval.glo4003.helper.shared.TemporalGenerator.createDateBefore;
import static ca.ulaval.glo4003.helper.shared.TemporalGenerator.createPeriod;

public class PolicyViewGenerator {
  private PolicyViewGenerator() {}

  public static List<PolicyView> createPreviousPolicyViews(Period period) {
    List<PolicyView> list = new ArrayList<>();
    Period nextPeriod = new Period(period.getStartDate(), period.getEndDate());
    for (int i = 0; i < Faker.instance().number().randomDigitNotZero(); i++) {
      PolicyView previousPolicyView = createPreviousPolicyView(nextPeriod);
      nextPeriod = previousPolicyView.getCoveragePeriod();
      list.add(previousPolicyView);
    }
    return list;
  }

  public static PolicyView createPreviousPolicyView(Period period) {
    return new PolicyView(
        createPreviousCoveragePeriod(period),
        createPolicyInformation(),
        createCoverageDetails(),
        createPremiumDetails());
  }

  public static Set<PolicyView> createPolicyViews() {
    return IntStream.range(0, Faker.instance().number().randomDigitNotZero())
        .mapToObj(i -> createPolicyView())
        .collect(Collectors.toSet());
  }

  public static PolicyView createPolicyView() {
    return new PolicyView(
        createCoveragePeriod(),
        createPolicyInformation(),
        createCoverageDetails(),
        createPremiumDetails());
  }

  public static Period createCoveragePeriod() {
    return createPeriod();
  }

  public static Period createPreviousCoveragePeriod(Period period) {
    Date endDate = period.getStartDate();
    Date startDate = createDateBefore(endDate);
    return new Period(startDate, endDate);
  }
}
