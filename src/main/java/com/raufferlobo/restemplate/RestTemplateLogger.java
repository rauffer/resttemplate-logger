package com.raufferlobo.restemplate;

import java.io.IOException;
import java.net.URI;

import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.web.client.RestTemplate;

import com.raufferlobo.restemplate.logger.RequestLogger;
import com.raufferlobo.restemplate.logger.impl.CurlRequestLogger;

public class RestTemplateLogger extends RestTemplate {

  private RequestLogger requestLogger;

  public RestTemplateLogger() {
      this(new CurlRequestLogger());
  }

  public RestTemplateLogger(RequestLogger requestLogger) {
      this.requestLogger = requestLogger;
  }

  @Override
  protected ClientHttpRequest createRequest(URI url, HttpMethod method) throws IOException {
    ClientHttpRequest request = super.createRequest(url, method);
    return new ClientHttpRequestLogger(requestLogger, request);
  }

  public RequestLogger getRequestLogger() {
    return requestLogger;
  }

  public void setRequestLogger(RequestLogger requestLogger) {
    this.requestLogger = requestLogger;
  }

}
