package com.terence.carparkvacancychecker;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TelegramServiceTest {

  @Autowired
  TelegramService telegramService;

  @Test
  void name() {
    telegramService.getUpdates();
    assertTrue(true);
  }

}
