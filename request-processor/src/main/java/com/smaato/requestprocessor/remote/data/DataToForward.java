package com.smaato.requestprocessor.remote.data;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor(staticName = "of")
public class DataToForward implements Serializable {
  Integer uniqueRequestsCount;
}
