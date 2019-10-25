package ca.ulaval.glo4003.shared.domain.temporal;

import ca.ulaval.glo4003.shared.domain.ValueObject;

public class Period extends ValueObject {
  private final Date startDate;
  private final Date endDate;

  public Period(Date date1, Date date2) {
    if (date1.isBefore(date2)) {
      this.startDate = date1;
      this.endDate = date2;
    } else {
      this.startDate = date2;
      this.endDate = date1;
    }
  }

  public Date getStartDate() {
    return startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public boolean isWithin(Date date) {
    if (date.equals(startDate) || date.equals(endDate)) return true;

    return date.isAfter(startDate) && date.isBefore(endDate);
  }
}
