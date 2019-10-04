package ca.ulaval.glo4003.shared.domain;

import ca.ulaval.glo4003.shared.domain.exceptions.PeriodInvalidDateException;

public class Period extends ValueObject {
  private Date startDate;
  private Date endDate;

  public Period(Date startDate, Date endDate) {
    validateDates(startDate, endDate);
    this.startDate = startDate;
    this.endDate = endDate;
  }

  private void validateDates(Date startDate, Date endDate) {
    if (startDate.isAfter(endDate)) {
      throw new PeriodInvalidDateException();
    }
  }

  public Date getStartDate() {
    return startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public boolean isWithin(Date date) {
    if (isEdgeCase(date)) {
      return true;
    }
    return date.isAfter(startDate) && date.isBefore(endDate);
  }

  private boolean isEdgeCase(Date date) {
    return date.equals(startDate) || date.equals(endDate);
  }
}
