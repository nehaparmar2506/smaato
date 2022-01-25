/*
 * Copyright 2012-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *	  https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.smaato.logger.service;

import com.smaato.logger.data.UniqueRequestsPerMinute;
import com.smaato.logger.repository.RedisRepository;
import com.smaato.logger.util.FileUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Set;

@Component
@Slf4j
@AllArgsConstructor
public class RequestCountLogger {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

  @Autowired private final RedisRepository redisRepository;

  @Autowired private final FileUtil fileUtil;

    @Scheduled(fixedDelay = 60000)
    public void reportCurrentTime() throws IOException {

        long instant = Instant.now().truncatedTo(ChronoUnit.MINUTES).minus(1, ChronoUnit.MINUTES).toEpochMilli();

    Optional<UniqueRequestsPerMinute> optionalUniqueRequestsPerMinute =
        redisRepository.findById(instant);
    if (optionalUniqueRequestsPerMinute.isPresent()) {
      UniqueRequestsPerMinute uniqueRequestsPerMinute = optionalUniqueRequestsPerMinute.get();
      Set<Integer> uniqueRequestsPerMinuteIds = uniqueRequestsPerMinute.getIds();
      fileUtil.writeToLogFile(
          uniqueRequestsPerMinuteIds.size()
              + " unique requests accepted at "
              + Instant.ofEpochMilli(instant));
        }
        else{
            log.info("nothing found at " + Instant.ofEpochMilli(instant));
        }
    }

}