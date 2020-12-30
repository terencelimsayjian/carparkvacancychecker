package com.terence.carparkvacancychecker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CarparkvacancycheckerApplication {

  public static void main(String[] args) {
    SpringApplication.run(CarparkvacancycheckerApplication.class, args);
  }

}
