package com.terence.carparkvacancychecker;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.DeleteWebhook;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import com.pengrad.telegrambot.request.SetWebhook;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
@Slf4j
public class TelegramService {
  private TelegramBot bot;
  private final CameraClient cameraClient;
  private final ResourceLoader resourceLoader;

  public TelegramService(ResourceLoader resourceLoader, @Value("${config.telegram_bot_token}") String botToken, CameraClient cameraClient) {
    this.resourceLoader = resourceLoader;
    this.cameraClient = cameraClient;
    bot = new TelegramBot(botToken);

    SetWebhook setWebhookRequest = new SetWebhook()
        .url("https://carparkvacancychecker-mpuwcgmk4a-as.a.run.app/webhook");

    BaseResponse response = bot.execute(setWebhookRequest);
  }

  public void sendMessage(Update telegramUpdate) {
    Resource resource = resourceLoader.getResource("classpath:static/spongebob.png");

    byte[] capturedImage = new byte[0];
    try {
      capturedImage = cameraClient.capture();
    } catch (Exception e) {
      log.error("Error encountered while capturing image", e);
    }

    String chatId = String.valueOf(telegramUpdate.message().chat().id());
    String name = telegramUpdate.message().chat().firstName();

    log.info(chatId);
    log.info(name);

    try {
      InputStream input = resource.getInputStream();
      byte[] bytes = input.readAllBytes();
      SendPhoto photoRequest = new SendPhoto(chatId, capturedImage);
      SendMessage textMessage = new SendMessage(chatId, "alu " + name);

      bot.execute(textMessage);
      bot.execute(photoRequest);
    } catch (IOException e) {
      log.error("Error while reading input stream", e);
    }
  }

  public void getUpdates () {
    GetUpdates request = new GetUpdates();
    GetUpdatesResponse response = bot.execute(request);

    List<Update> updates = response.updates();

    System.out.println(updates);
  }
}
