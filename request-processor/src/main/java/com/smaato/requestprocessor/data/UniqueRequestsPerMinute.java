package com.smaato.requestprocessor.data;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.Set;

@RedisHash("uniqueRequestsPerMinute")
@Data
@AllArgsConstructor
public class UniqueRequestsPerMinute {

    @Id
    Long time;
    Set<Integer> ids;

}
