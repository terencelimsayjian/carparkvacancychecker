package com.terence.carparkvacancychecker;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.pengrad.telegrambot.model.Update;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TelegramController {
  public static final String WEBHOOK_ROUTE = "/webhook";

  @Autowired
  TelegramService telegramService;

  @Autowired
  Gson gson;

  @PostMapping(path = WEBHOOK_ROUTE, consumes = "application/json")
  void handleWebhook(@RequestBody String telegramUpdateJson) {
    Update update = null;

    try {
      update = gson.fromJson(telegramUpdateJson, Update.class);
    } catch (JsonSyntaxException e) {
      log.error("Invalid JSON body in request", telegramUpdateJson);
    }

    telegramService.sendMessage(update);
  }
}
