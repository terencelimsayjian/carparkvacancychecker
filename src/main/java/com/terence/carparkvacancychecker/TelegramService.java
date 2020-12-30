package com.terence.carparkvacancychecker;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import com.pengrad.telegrambot.request.SetWebhook;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.terence.carparkvacancychecker.TelegramController.WEBHOOK_ROUTE;

@Service
@Slf4j
public class TelegramService {
  private TelegramBot bot;
  private final CameraClient cameraClient;

  public TelegramService(
      @Value("${config.telegram_bot_token}") String botToken,
      @Value("${config.base_url}") String baseUrl,
      CameraClient cameraClient) {
    this.cameraClient = cameraClient;
    bot = new TelegramBot(botToken);

    SetWebhook setWebhookRequest = new SetWebhook()
        .url(baseUrl + WEBHOOK_ROUTE);

    BaseResponse response = bot.execute(setWebhookRequest);

    log.info("Set up webhook request: " + response.isOk());
    log.info(response.toString());
    log.info(response.description());
    log.info("Error code: " + response.errorCode());
  }

  public void sendMessage(Update telegramUpdate) {
    byte[] capturedImage = new byte[0];
    try {
      capturedImage = cameraClient.capture();
    } catch (Exception e) {
      log.error("Error encountered while capturing image", e);
    }

    String chatId = String.valueOf(telegramUpdate.message().chat().id());
    String name = telegramUpdate.message().chat().firstName();

    SendPhoto photoRequest = new SendPhoto(chatId, capturedImage);
    SendMessage textMessage = new SendMessage(chatId, "alu " + name);

    bot.execute(textMessage);
    bot.execute(photoRequest);
  }

  public void getUpdates () {
    GetUpdates request = new GetUpdates();
    GetUpdatesResponse response = bot.execute(request);

    List<Update> updates = response.updates();

    System.out.println(updates);
  }
}
