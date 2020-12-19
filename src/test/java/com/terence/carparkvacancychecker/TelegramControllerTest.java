package com.terence.carparkvacancychecker;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest
class TelegramControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private TelegramService telegramService;

  @Test
  void shouldMapJsonToTelegramUpdate() throws Exception {
    String requestBody = "{\n" +
                         "    \"update_id\":646911460,\n" +
                         "    \"message\":{\n" +
                         "        \"message_id\":93,\n" +
                         "        \"from\":{\n" +
                         "            \"id\":10000,\n" +
                         "            \"is_bot\":false,\n" +
                         "            \"first_name\":\"Jiayu\",\n" +
                         "            \"username\":\"jiayu\",\n" +
                         "            \"language_code\":\"en-US\"\n" +
                         "        },\n" +
                         "        \"chat\":{\n" +
                         "            \"id\":10000,\n" +
                         "            \"first_name\":\"Jiayu\",\n" +
                         "            \"username\":\"jiayu\",\n" +
                         "            \"type\":\"private\"\n" +
                         "        },\n" +
                         "        \"date\":1509641174,\n" +
                         "        \"text\":\"eevee\"\n" +
                         "    }\n" +
                         "}";


    MockHttpServletRequestBuilder requestBuilder = post("/webhook")
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestBody);

    mockMvc
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk());


  }
}
