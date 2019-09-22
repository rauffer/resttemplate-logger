package com.raufferlobo.restemplate;

import java.io.IOException;
import java.net.URI;

import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.web.client.RestTemplate;

import com.raufferlobo.restemplate.logging.RequestLogger;
import com.raufferlobo.restemplate.logging.impl.CurlRequestLogger;

public class LoggingRestTemplate extends RestTemplate {

  private RequestLogger requestLogger;

  public LoggingRestTemplate() {
      this(new CurlRequestLogger());
  }

  public LoggingRestTemplate(RequestLogger requestLogger) {
      this.requestLogger = requestLogger;
  }

  @Override
  protected ClientHttpRequest createRequest(URI url, HttpMethod method) throws IOException {
    ClientHttpRequest request = super.createRequest(url, method);
    return new LoggingClientHttpRequest(requestLogger, request);
  }

  public RequestLogger getRequestLogger() {
    return requestLogger;
  }

  public void setRequestLogger(RequestLogger requestLogger) {
    this.requestLogger = requestLogger;
  }

}
