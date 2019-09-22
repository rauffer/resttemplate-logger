package com.raufferlobo.restemplate;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;

import com.raufferlobo.restemplate.logger.RequestLogger;

public class ClientHttpRequestLogger implements ClientHttpRequest {

  private RequestLogger requestLogger;
  private ClientHttpRequest clientHttpRequest;

  public ClientHttpRequestLogger(RequestLogger requestLogger, ClientHttpRequest clientHttpRequest) {
    this.requestLogger = requestLogger;
    this.clientHttpRequest = clientHttpRequest;
  }

  @Override
  public String getMethodValue() {
    return clientHttpRequest.getMethodValue();
  }
  
  @Override
  public HttpMethod getMethod() {
    return clientHttpRequest.getMethod();
  }

  @Override
  public URI getURI() {
    return clientHttpRequest.getURI();
  }

  @Override
  public HttpHeaders getHeaders() {
    return clientHttpRequest.getHeaders();
  }

  @Override
  public OutputStream getBody() throws IOException {
    return clientHttpRequest.getBody();
  }

  @Override
  public ClientHttpResponse execute() throws IOException {
    log();
    return clientHttpRequest.execute();
  }

  private void log() throws IOException {
    Map<String, String> headers = getHeaders().toSingleValueMap();
    String uri = getURI().toString();
    String body = getBodyAsString();
    requestLogger.log(uri, getMethodValue(), headers, body);
  }

  private String getBodyAsString() throws IOException {

    OutputStream body = getBody();

    if(body == null) {
      return null;
    }
    
    if (body.getClass().isAssignableFrom(ByteArrayOutputStream.class)) {
      byte[] bytes = ((ByteArrayOutputStream) body).toByteArray();
      return new String(bytes, Charset.forName("UTF-8"));
    }
    
    // TODO: create generic implementation for all outpustream.
    
    throw new IllegalArgumentException(body.getClass().getName() + " not supported.");

  }

}
