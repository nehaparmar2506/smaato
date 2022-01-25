package com.smaato.requestprocessor.remote;

import com.smaato.requestprocessor.data.UniqueRequestsPerMinute;
import com.smaato.requestprocessor.kafka.KafkaProducer;
import com.smaato.requestprocessor.remote.data.EndPointCallStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Component
@Slf4j
public class RemoteCaller {

  private final RestTemplate restTemplate = new RestTemplate();
  @Autowired private KafkaProducer kafkaProducer;

  @Async("asyncExecutor")
  public void callEndpoint(
      String endPoint, Long currentMinute, UniqueRequestsPerMinute requestCount)
          throws ExecutionException, InterruptedException, URISyntaxException {

    EndPointCallStatus dataToForward =
        EndPointCallStatus.of(
            requestCount.getTime(), endPoint, requestCount.getIds().size(), Optional.empty());
    URI url = new URI(endPoint + "?" + dataToForward.getUniqueRequestsCount());
    ResponseEntity<String> result =
        restTemplate.getForEntity( url, String.class);

    dataToForward.setStatus(Optional.of(result.getStatusCode().toString()));
    kafkaProducer.sendEvent(currentMinute, dataToForward);
  }



}
