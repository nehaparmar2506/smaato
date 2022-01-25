package com.smaato.requestprocessor.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolationException;

@ControllerAdvice(annotations = RestController.class)
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler({ConstraintViolationException.class, IllegalArgumentException.class})
  @ResponseBody
  public ResponseEntity<String> handleDataException(RuntimeException exception)
      throws JsonProcessingException {
    String message = new ObjectMapper().writeValueAsString(exception.getMessage());
    return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler({Exception.class})
  @ResponseBody
  public ResponseEntity<String> handleProcessingException(RuntimeException exception) {
    log.error(exception.getMessage());
    return new ResponseEntity<>("failed", HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
