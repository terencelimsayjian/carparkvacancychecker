package com.terence.carparkvacancychecker;

import com.pengrad.telegrambot.model.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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


  @PostMapping("/webhook")
  void handleWebhook(Update telegramUpdate) {
    telegramService.sendMessage("243642451");
  }
}
