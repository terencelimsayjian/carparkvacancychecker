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
    log.info("Seting up webhook request: " + response.toString());
  }

  public void sendMessage(Update telegramUpdate) {
    String chatId = String.valueOf(telegramUpdate.message().chat().id());
    String name = telegramUpdate.message().chat().firstName();
    SendMessage textMessage = new SendMessage(chatId, "Request received, " + name);
    bot.execute(textMessage);

    try {
      byte[]  capturedImage = cameraClient.capture();
      SendPhoto photoRequest = new SendPhoto(chatId, capturedImage);
      bot.execute(photoRequest);
    } catch (Exception e) {
      log.error("Error encountered while capturing image", e);

      SendMessage errorMessage = new SendMessage(chatId, "Sorry, something went wrong.");
      bot.execute(errorMessage);
    }
  }

  public void getUpdates () {
    GetUpdates request = new GetUpdates();
    GetUpdatesResponse response = bot.execute(request);

    List<Update> updates = response.updates();

    System.out.println(updates);
  }
}
