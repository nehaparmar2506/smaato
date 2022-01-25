package com.smaato.requestprocessor.service;

import com.smaato.requestprocessor.data.UniqueRequestsPerMinute;
import com.smaato.requestprocessor.remote.RemoteCaller;
import com.smaato.requestprocessor.repository.RedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutionException;

@Service
public class RequestProcessor {


    @Autowired
    RedisRepository redisRepository;

    @Autowired
    RemoteCaller caller ;

  public String processRequest(Integer id, String endPoint)
          throws ExecutionException, InterruptedException, URISyntaxException {

        Long currentMinute = getCurrentMinute();
    Optional<UniqueRequestsPerMinute> optRequestsPerMin = redisRepository.findById(currentMinute);

    UniqueRequestsPerMinute uniqueRequestsPerMinute;

    if (optRequestsPerMin.isPresent()) {
      uniqueRequestsPerMinute = optRequestsPerMin.get();
      Set<Integer> existingRequestTrackIds = uniqueRequestsPerMinute.getIds();
            existingRequestTrackIds.add(id);
        }
        else{
            Set<Integer> requestIds = new HashSet<>();
            requestIds.add(id);
      uniqueRequestsPerMinute = new UniqueRequestsPerMinute(currentMinute, requestIds);
        }

    redisRepository.save(uniqueRequestsPerMinute);

    if (endPoint != null) caller.callEndpoint(endPoint, currentMinute, uniqueRequestsPerMinute);

        return "OK";
    }

    private long getCurrentMinute() {
        return Instant.now().truncatedTo(ChronoUnit.MINUTES).toEpochMilli();
    }



}
