package com.dummy.dummyendpoint.controller;

import com.dummy.dummyendpoint.model.DataToForward;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dummy/")
public class DummyController {

  @PostMapping(value = "statistics", consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.OK)
  public @ResponseBody String getStatistics(@RequestBody DataToForward dataToForward) {
    return "true";
  }
}
