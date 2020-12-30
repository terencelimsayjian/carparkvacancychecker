package com.terence.carparkvacancychecker;

import lombok.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "CameraClient", url = "${config.camera_service_url}")
public interface CameraClient {
  @RequestMapping(method = RequestMethod.GET, value = "/capture", produces = MediaType.IMAGE_JPEG_VALUE)
  byte[] capture();
}
