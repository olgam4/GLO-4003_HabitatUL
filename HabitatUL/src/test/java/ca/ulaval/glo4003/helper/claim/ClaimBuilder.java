package ca.ulaval.glo4003.helper.claim;

import ca.ulaval.glo4003.helper.shared.AuthorityGenerator;
import ca.ulaval.glo4003.insuring.domain.claim.*;
import ca.ulaval.glo4003.shared.domain.authority.AuthorityNumber;
import ca.ulaval.glo4003.shared.domain.temporal.Date;

import static ca.ulaval.glo4003.helper.claim.ClaimGenerator.*;
import static ca.ulaval.glo4003.helper.shared.TemporalGenerator.createDate;

public class ClaimBuilder {
  private final ClaimId DEFAULT_CLAIM_ID = createClaimId();
  private final Date DEFAULT_DECLARATION_DATE = createDate();
  private final ClaimStatus DEFAULT_CLAIM_STATUS = createClaimStatus();
  private final AuthorityNumber DEFAULT_AUTHORITY_NUMBER =
      AuthorityGenerator.createAuthorityNumber();
  private final SinisterType DEFAULT_SINISTER_TYPE = createSinisterType();
  private final LossDeclarations DEFAULT_LOSS_DECLARATIONS =
      LossDeclarationsGenerator.createLossDeclarations();

  private ClaimId claimId = DEFAULT_CLAIM_ID;
  private Date declarationDate = DEFAULT_DECLARATION_DATE;
  private ClaimStatus claimStatus = DEFAULT_CLAIM_STATUS;
  private AuthorityNumber authorityNumber = DEFAULT_AUTHORITY_NUMBER;
  private SinisterType sinisterType = DEFAULT_SINISTER_TYPE;
  private LossDeclarations lossDeclarations = DEFAULT_LOSS_DECLARATIONS;

  private ClaimBuilder() {}

  public static ClaimBuilder aClaim() {
    return new ClaimBuilder();
  }

  public ClaimBuilder withClaimId(ClaimId claimId) {
    this.claimId = claimId;
    return this;
  }

  public ClaimBuilder withDeclarationDate(Date date) {
    this.declarationDate = date;
    return this;
  }

  public ClaimBuilder withStatus(ClaimStatus claimStatus) {
    this.claimStatus = claimStatus;
    return this;
  }

  public ClaimBuilder withAuthorityNumber(AuthorityNumber authorityNumber) {
    this.authorityNumber = authorityNumber;
    return this;
  }

  public ClaimBuilder withSinisterType(SinisterType type) {
    this.sinisterType = type;
    return this;
  }

  public ClaimBuilder withLossDeclarations(LossDeclarations lossDeclarations) {
    this.lossDeclarations = lossDeclarations;
    return this;
  }

  public Claim build() {
    return new Claim(
        claimId, declarationDate, claimStatus, authorityNumber, sinisterType, lossDeclarations);
  }
}
