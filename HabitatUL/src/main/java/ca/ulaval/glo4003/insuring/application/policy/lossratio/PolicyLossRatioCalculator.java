package ca.ulaval.glo4003.insuring.application.policy.lossratio;

import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.insuring.domain.claim.Claim;
import ca.ulaval.glo4003.insuring.domain.claim.ClaimId;
import ca.ulaval.glo4003.insuring.domain.claim.ClaimRepository;
import ca.ulaval.glo4003.insuring.domain.policy.Policy;
import ca.ulaval.glo4003.insuring.domain.policy.lossratio.LossRatio;
import ca.ulaval.glo4003.shared.domain.money.Amount;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static ca.ulaval.glo4003.coverage.domain.coverage.CoverageCategory.PERSONAL_PROPERTY;
import static ca.ulaval.glo4003.insuring.domain.claim.ClaimStatus.*;

public class PolicyLossRatioCalculator {
  private ClaimRepository claimRepository;

  public PolicyLossRatioCalculator() {
    this(ServiceLocator.resolve(ClaimRepository.class));
  }

  public PolicyLossRatioCalculator(ClaimRepository claimRepository) {
    this.claimRepository = claimRepository;
  }

  public LossRatio computeLossRatioWithAdditionalClaim(Policy policy, Claim claim) {
    List<Claim> claims = fetchClaimsToBeConsidered(policy);
    claims.add(claim);
    return computePersonalPropertyLossRatio(policy, claims);
  }

  public List<Claim> listNotYetAcceptedClaimsExceedingMaximumLossRatio(
      Policy policy, LossRatio maximumLossRatio) {
    List<Claim> claims = fetchClaimsToBeConsidered(policy);
    LossRatio policyLossRatio = computePersonalPropertyLossRatio(policy, claims);
    return listNotYetAcceptedClaimsExceedingMaximumLossRatio(
        maximumLossRatio, policyLossRatio, claims);
  }

  private List<Claim> fetchClaimsToBeConsidered(Policy policy) {
    return filterClaimsToBeConsidered(policy, fetchClaims(policy.getClaims()));
  }

  private List<Claim> fetchClaims(List<ClaimId> claimIds) {
    List<Claim> claims = new ArrayList<>();
    claimIds.forEach(claimId -> claimRepository.findById(claimId).ifPresent(claims::add));
    return claims;
  }

  private List<Claim> filterClaimsToBeConsidered(Policy policy, List<Claim> claims) {
    return claims.stream()
        .filter(x -> isClaimToBeConsidered(policy, x))
        .collect(Collectors.toList());
  }

  private boolean isClaimToBeConsidered(Policy policy, Claim claim) {
    return !claim.getStatus().equals(EXPIRED)
        && policy.getCoveragePeriod().isWithin(claim.getDeclarationDate());
  }

  private LossRatio computePersonalPropertyLossRatio(Policy policy, List<Claim> claims) {
    Amount personalPropertyCoverageAmount =
        policy.getCoverageDetails().getCoverageAmount(PERSONAL_PROPERTY);
    Amount totalPersonalPropertyLosses = computeTotalPersonalPropertyLosses(claims);
    return new LossRatio(personalPropertyCoverageAmount, totalPersonalPropertyLosses);
  }

  private Amount computeTotalPersonalPropertyLosses(List<Claim> claims) {
    return claims.stream()
        .map(Claim::computePersonalPropertyLosses)
        .reduce(Amount.ZERO, Amount::add);
  }

  private List<Claim> listNotYetAcceptedClaimsExceedingMaximumLossRatio(
      LossRatio maximumLossRatio, LossRatio policyLossRatio, List<Claim> claims) {
    return policyLossRatio.isGreaterThan(maximumLossRatio)
        ? claims.stream().filter(this::isNotYetAcceptedClaim).collect(Collectors.toList())
        : Collections.emptyList();
  }

  private boolean isNotYetAcceptedClaim(Claim claim) {
    return Arrays.asList(RECEIVED, UNDER_ANALYSIS).contains(claim.getStatus());
  }
}
