package ca.ulaval.glo4003.shared.domain;

import org.apache.commons.lang3.builder.*;

public class ValueComparableObject implements Comparable<Object> {
  @Override
  public boolean equals(Object o) {
    return EqualsBuilder.reflectionEquals(this, o);
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }

  @Override
  public int compareTo(Object o) {
    return CompareToBuilder.reflectionCompare(this, o);
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
  }
}
