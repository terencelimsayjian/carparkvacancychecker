package com.terence.carparkvacancychecker;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
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
  }

  public void sendMessage(String chatId) {
    SendMessage request = new SendMessage(chatId, "alu");


    Resource resource = resourceLoader.getResource("classpath:static/spongebob.png");


    try {
      InputStream input = resource.getInputStream();
      byte[] bytes = input.readAllBytes();
      SendPhoto photoRequest = new SendPhoto(chatId, bytes);

      bot.execute(photoRequest);
    } catch (IOException e) {
      e.printStackTrace();
    }



//    SendResponse response = bot.execute(request);
  }

  public void getUpdates() {
    GetUpdates request = new GetUpdates();
    GetUpdatesResponse response = bot.execute(request);

    List<Update> updates = response.updates();

    updates.forEach(u -> {
      Message message = u.message();

      Long id = message.chat().id();

      sendMessage(Long.toString(id));

    });
  }


}
