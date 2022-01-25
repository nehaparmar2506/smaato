package com.smaato.listener.responsestatus;

import com.smaato.listener.util.FileUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Component
@Slf4j
@AllArgsConstructor
public class ResponseStatusListener {

  @Autowired
  private final FileUtil fileUtil;

  @KafkaListener(topics = "response-status")
  public void listener(@Payload String eventData)
      throws IOException {
        fileUtil.writeToLogFile(eventData);
    }

}
