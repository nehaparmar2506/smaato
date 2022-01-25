package com.smaato.requestprocessor.kafka;

import com.smaato.requestprocessor.remote.data.EndPointCallStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;

@Component
@Slf4j
public class KafkaProducer {

  @Autowired private KafkaTemplate<Long, EndPointCallStatus> kafkaTemplate;

  @Value("${kafka.topic.name}")
  private String topicName;

  public void sendEvent(Long key, EndPointCallStatus data)
      throws ExecutionException, InterruptedException {
    ListenableFuture eventFuture = kafkaTemplate.send(topicName, key, data);
    if (eventFuture.isDone()) {
      log.info("Called end point and event sent: {}", eventFuture.get());
    }
  }
}
