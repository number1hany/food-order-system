package com.food.order.system.domain;

public abstract class BaseEntity<ID> {

  private ID id;

  public ID getId() {
    return id;
  }

  public void setId(ID id) {
    this.id = id;
  }

  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (!(object instanceof BaseEntity)) {
      return false;
    }
    if (!super.equals(object)) {
      return false;
    }
    BaseEntity<?> that = (BaseEntity<?>) object;
    return id.equals(that.id);
  }

  public int hashCode() {
    return Objects.hash(super.hashCode(), id);
  }

}