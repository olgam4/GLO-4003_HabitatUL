package ca.ulaval.glo4003.insuring.domain.claim;

import ca.ulaval.glo4003.helper.shared.TemporalGenerator;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import ca.ulaval.glo4003.shared.domain.temporal.Date;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Clock;
import java.util.Arrays;

import static ca.ulaval.glo4003.helper.claim.ClaimGenerator.createSinisterType;
import static ca.ulaval.glo4003.helper.claim.LossDeclarationsGenerator.createLossDeclarations;
import static ca.ulaval.glo4003.helper.shared.EnumSampler.sample;
import static ca.ulaval.glo4003.insuring.domain.claim.ClaimStatus.RECEIVED;
import static ca.ulaval.glo4003.insuring.domain.claim.ClaimStatus.UNDER_ANALYSIS;
import static ca.ulaval.glo4003.insuring.domain.claim.SinisterType.FIRE;
import static ca.ulaval.glo4003.shared.domain.authority.AuthorityNumber.UNFILLED_AUTHORITY_NUMBER;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ClaimFactoryTest {
  private static final SinisterType SINISTER_TYPE = createSinisterType();
  private static final LossDeclarations LOSS_DECLARATIONS = createLossDeclarations();

  @Mock private ClockProvider clockProvider;

  private Clock clock;
  private ClaimFactory subject;

  @Before
  public void setUp() {
    clock = TemporalGenerator.getClockProvider().getClock();
    when(clockProvider.getClock()).thenReturn(clock);
    subject = new ClaimFactory(clockProvider);
  }

  @Test
  public void creatingClaim_shouldComputeDeclarationDate() {
    Claim claim = subject.create(SINISTER_TYPE, LOSS_DECLARATIONS);

    assertEquals(Date.now(clock), claim.getDeclarationDate());
  }

  @Test
  public void creatingClaim_withFireSinisterType_shouldCreateUnderAnalysisClaim() {
    Claim claim = subject.create(FIRE, LOSS_DECLARATIONS);

    assertEquals(UNDER_ANALYSIS, claim.getStatus());
  }

  @Test
  public void creatingClaim_withSinisterTypeDifferentThanFire_shouldCreateReceivedClaim() {
    SinisterType sinisterTypeDifferentThanFire = sample(SinisterType.class, Arrays.asList(FIRE));
    Claim claim = subject.create(sinisterTypeDifferentThanFire, LOSS_DECLARATIONS);

    assertEquals(RECEIVED, claim.getStatus());
  }

  @Test
  public void creatingClaim_shouldCreateClaimWithoutAuthorityNumber() {
    Claim claim = subject.create(SINISTER_TYPE, LOSS_DECLARATIONS);

    assertEquals(UNFILLED_AUTHORITY_NUMBER, claim.getAuthorityNumber());
  }
}
