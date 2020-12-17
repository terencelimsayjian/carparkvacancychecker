package com.terence.carparkvacancychecker;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import com.pengrad.telegrambot.request.SetWebhook;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class TelegramService {

  // Next step - Receive message and response with image

  private TelegramBot bot;

  private ResourceLoader resourceLoader;

  public TelegramService(ResourceLoader resourceLoader, @Value("${config.telegram_bot_token}") String botToken) {
    this.resourceLoader = resourceLoader;
    bot = new TelegramBot(botToken);

    SetWebhook setWebhookRequest = new SetWebhook()
        .url("https://carparkvacancychecker-mpuwcgmk4a-as.a.run.app/webhook");

    BaseResponse response = bot.execute(setWebhookRequest);
  }

  public void sendMessage(Update telegramUpdate) {


    Resource resource = resourceLoader.getResource("classpath:static/spongebob.png");

    String chatId = Long.toString(telegramUpdate.message().chat().id());
    String name = telegramUpdate.message().from().firstName();


    try {
      InputStream input = resource.getInputStream();
      byte[] bytes = input.readAllBytes();
      SendPhoto photoRequest = new SendPhoto(chatId, bytes);
      SendMessage textMessage = new SendMessage(chatId, "alu " + name);

      bot.execute(textMessage);
      bot.execute(photoRequest);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


    public void getUpdates () {
      GetUpdates request = new GetUpdates();
      GetUpdatesResponse response = bot.execute(request);

      List<Update> updates = response.updates();

    }
}
