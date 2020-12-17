package com.terence.carparkvacancychecker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TelegramController {
  @Autowired
  TelegramService telegramService;

  @PostMapping("/telegram")
  String handleCallback() {
    telegramService.sendMessage("243642451");
    return "Hello World";
  }
}
