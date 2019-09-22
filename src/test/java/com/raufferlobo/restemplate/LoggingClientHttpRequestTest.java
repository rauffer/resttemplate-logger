package com.raufferlobo.restemplate;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;

import com.raufferlobo.restemplate.LoggingClientHttpRequest;
import com.raufferlobo.restemplate.logging.RequestLogger;

public class LoggingClientHttpRequestTest {
  
  @Test
  public void mustReturnTheHttpMethodDefinedInClientHttpRequest() {
    
    RequestLogger mockedRequestLogger = Mockito.mock(RequestLogger.class);
    ClientHttpRequest mockedClientRequest = Mockito.mock(ClientHttpRequest.class);
    Mockito.when(mockedClientRequest.getMethod()).thenReturn(HttpMethod.GET);
    
    LoggingClientHttpRequest loggingClientHttpRequest = new LoggingClientHttpRequest(mockedRequestLogger, mockedClientRequest);
    
    HttpMethod method = loggingClientHttpRequest.getMethod();
    
    Assert.assertEquals(HttpMethod.GET, method);
    
  }
  
  @Test
  public void mustReturnTheHttpMethodValueDefinedInClientHttpRequest() {
    
    RequestLogger mockedRequestLogger = Mockito.mock(RequestLogger.class);
    ClientHttpRequest mockedClientRequest = Mockito.mock(ClientHttpRequest.class);
    Mockito.when(mockedClientRequest.getMethodValue()).thenReturn("PUT");
    
    LoggingClientHttpRequest loggingClientHttpRequest = new LoggingClientHttpRequest(mockedRequestLogger, mockedClientRequest);
    
    String methodValue = loggingClientHttpRequest.getMethodValue();
    
    Assert.assertEquals("PUT", methodValue);
    
  }
  
  @Test
  public void mustReturnTheUriDefinedInClientHttpRequest() throws URISyntaxException {
    
    RequestLogger mockedRequestLogger = Mockito.mock(RequestLogger.class);
    ClientHttpRequest mockedClientRequest = Mockito.mock(ClientHttpRequest.class);
    URI targetUri = new URI("http://localhost/my-uri");
    Mockito.when(mockedClientRequest.getURI()).thenReturn(targetUri);
    
    LoggingClientHttpRequest loggingClientHttpRequest = new LoggingClientHttpRequest(mockedRequestLogger, mockedClientRequest);
    
    URI uri = loggingClientHttpRequest.getURI();
    
    Assert.assertSame(uri, targetUri);
    
  }
  
  @Test
  public void mustReturnTheHeadersDefinedInClientHttpRequest() {
    
    RequestLogger mockedRequestLogger = Mockito.mock(RequestLogger.class);
    ClientHttpRequest mockedClientRequest = Mockito.mock(ClientHttpRequest.class);
    HttpHeaders targetHeaders = new HttpHeaders();
    targetHeaders.add("my-header", "the-value");
    Mockito.when(mockedClientRequest.getHeaders()).thenReturn(targetHeaders);
    
    LoggingClientHttpRequest loggingClientHttpRequest = new LoggingClientHttpRequest(mockedRequestLogger, mockedClientRequest);
    
    HttpHeaders headers = loggingClientHttpRequest.getHeaders();
    
    Assert.assertSame(headers, targetHeaders);
    Assert.assertEquals("the-value", headers.get("my-header").get(0));
    Assert.assertEquals(1, headers.size());
    
  }
  
  @Test
  public void mustReturnTheBodyDefinedInClientHttpRequest() throws IOException {
    
    RequestLogger mockedRequestLogger = Mockito.mock(RequestLogger.class);
    ClientHttpRequest mockedClientRequest = Mockito.mock(ClientHttpRequest.class);
    
    OutputStream body = new ByteArrayOutputStream();
    body.write("the request body".getBytes());
    body.close();
    
    Mockito.when(mockedClientRequest.getBody()).thenReturn(body);
    
    LoggingClientHttpRequest loggingClientHttpRequest = new LoggingClientHttpRequest(mockedRequestLogger, mockedClientRequest);
    
    OutputStream readedBody = loggingClientHttpRequest.getBody();
    
    Assert.assertSame(readedBody, body);
    Assert.assertEquals("the request body", ((ByteArrayOutputStream)readedBody).toString());
    
  }
  
  @Test
  public void mustLogRequestInformationWhenRequestIsExecuted() throws IOException, URISyntaxException {
    
    RequestLogger mockedRequestLogger = new RequestLogger() {
      @Override
      public void log(String uri, String method, Map<String, String> headers, String body) {
        Assert.assertEquals("http://localhost/my-uri", uri);
        Assert.assertEquals("Bearer ...", headers.get("Authorization"));
        Assert.assertEquals("a not empty body", body);
      }
    };
    
    ClientHttpRequest mockedClientRequest = Mockito.mock(ClientHttpRequest.class);
    
    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Bearer ...");
    
    OutputStream body = new ByteArrayOutputStream();
    body.write("a not empty body".getBytes());
    body.close();
    
    Mockito.when(mockedClientRequest.getHeaders()).thenReturn(headers);
    Mockito.when(mockedClientRequest.getURI()).thenReturn(new URI("http://localhost/my-uri"));
    Mockito.when(mockedClientRequest.getBody()).thenReturn(body);
    
    LoggingClientHttpRequest loggingClientHttpRequest = new LoggingClientHttpRequest(mockedRequestLogger, mockedClientRequest);
    
    loggingClientHttpRequest.execute();
    
  }
  
  @Test
  public void mustLogNullBodyWhenRequestIsExecutedWithNullBody() throws IOException, URISyntaxException {
    
    RequestLogger mockedRequestLogger = new RequestLogger() {
      @Override
      public void log(String uri, String method, Map<String, String> headers, String body) {
        Assert.assertNull(body);
      }
    };
    
    ClientHttpRequest mockedClientRequest = Mockito.mock(ClientHttpRequest.class);
    
    HttpHeaders headers = new HttpHeaders();
    
    Mockito.when(mockedClientRequest.getHeaders()).thenReturn(headers);
    Mockito.when(mockedClientRequest.getURI()).thenReturn(new URI("http://localhost/my-uri"));
    Mockito.when(mockedClientRequest.getBody()).thenReturn(null);
    
    LoggingClientHttpRequest loggingClientHttpRequest = new LoggingClientHttpRequest(mockedRequestLogger, mockedClientRequest);
    
    loggingClientHttpRequest.execute();
    
  }
  
}
