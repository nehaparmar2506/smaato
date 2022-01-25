package com.smaato.requestprocessor.controller;

import com.smaato.requestprocessor.service.RequestProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/smaato")
public class AcceptAndForwardController {

    @Autowired
    RequestProcessor requestProcessor;

  @GetMapping("/accept")
  public String processRequest(
      @RequestParam @NotNull Integer id, @RequestParam(required = false) String endPoint)
      throws Exception {

        return requestProcessor.processRequest(id, endPoint);
    }




}
