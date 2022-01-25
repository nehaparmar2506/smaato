package com.smaato.requestprocessor.remote.data;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Optional;

@Data
@AllArgsConstructor(staticName = "of")
public class EndPointCallStatus {
    Long instant ;
    String endpoint ;
    Integer uniqueRequestsCount;
    Optional<String> status;
}
