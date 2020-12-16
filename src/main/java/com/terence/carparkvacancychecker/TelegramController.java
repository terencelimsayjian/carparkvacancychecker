package com.terence.carparkvacancychecker;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TelegramController {

  @PostMapping("/telegram")
  String handleCallback() {
    return "Hello World";
  }
}
