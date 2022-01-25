package com.smaato.requestprocessor.remote;

import com.smaato.requestprocessor.data.UniqueRequestsPerMinute;
import com.smaato.requestprocessor.kafka.KafkaProducer;
import com.smaato.requestprocessor.remote.data.DataToForward;
import com.smaato.requestprocessor.remote.data.EndPointCallStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Component
@Slf4j
public class EndPointForwarderClient {

  private final RestTemplate restTemplate = new RestTemplate();
  @Autowired private KafkaProducer kafkaProducer;

  @Async("asyncExecutor")
  public void callEndpointAndSendEvent(
      String endPoint, Long currentMinute, UniqueRequestsPerMinute requestCount)
      throws ExecutionException, InterruptedException {

    int uniqueRequestsCount = requestCount.getIds().size();
    HttpEntity<DataToForward> request = new HttpEntity<>(DataToForward.of(uniqueRequestsCount));

    EndPointCallStatus eventDataToForward =
        prepareEndpointCallStatus(requestCount, endPoint, uniqueRequestsCount);

    try {
      ResponseEntity<String> result = restTemplate.postForEntity(endPoint, request, String.class);
      eventDataToForward.setStatus(Optional.of(result.getStatusCode().toString()));

    } catch (HttpClientErrorException exception) {
      eventDataToForward.setStatus(Optional.of(exception.getStatusCode().toString()));
    }
    catch (Exception exception) {
      eventDataToForward.setStatus(Optional.of(exception.getMessage()));
    }
    kafkaProducer.sendEvent(currentMinute, eventDataToForward);
  }

  private EndPointCallStatus prepareEndpointCallStatus(
      UniqueRequestsPerMinute requestCount, String endPoint, int uniqueRequestsCount) {
    return EndPointCallStatus.of(
        requestCount.getTime(), endPoint, uniqueRequestsCount, Optional.empty());
  }
}
