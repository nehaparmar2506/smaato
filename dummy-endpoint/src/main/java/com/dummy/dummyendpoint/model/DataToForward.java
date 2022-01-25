package com.dummy.dummyendpoint.model;

import java.io.Serializable;

public class DataToForward implements Serializable {
  private static final long serialVersionUID = -1209349149574168174L;
  Integer uniqueRequestsCount;

  public Integer getUniqueRequestsCount() {
    return uniqueRequestsCount;
  }

  public void setUniqueRequestsCount(Integer uniqueRequestsCount) {
    this.uniqueRequestsCount = uniqueRequestsCount;
  }
}
