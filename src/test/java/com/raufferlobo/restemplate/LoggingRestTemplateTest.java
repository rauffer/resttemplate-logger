package com.raufferlobo.restemplate;

import java.io.IOException;
import java.net.URI;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;

import com.raufferlobo.restemplate.logging.RequestLogger;
import com.raufferlobo.restemplate.logging.impl.CurlRequestLogger;

public class LoggingRestTemplateTest {

  @Test
  public void mustSetCurlRequestLoggerAsDefaultRequestLogger() {
    
    LoggingRestTemplate restTemplate = new LoggingRestTemplate();
    
    Assert.assertEquals(CurlRequestLogger.class, restTemplate.getRequestLogger().getClass());
    
  }
  
  @Test
  public void mustUseRequestLoggerSetInConstructor() {
    
    RequestLogger mockedRequestLogger = Mockito.mock(RequestLogger.class);
    
    LoggingRestTemplate restTemplate = new LoggingRestTemplate(mockedRequestLogger);
    
    Assert.assertSame(mockedRequestLogger, restTemplate.getRequestLogger());
    
  }
  
  @Test
  public void mustCreateLoggingClientHttpRequestWhenCreatingRequest() throws IOException {
    
    LoggingRestTemplate restTemplate = new LoggingRestTemplate();
    
    ClientHttpRequest request = restTemplate.createRequest(URI.create("http://localhost"), HttpMethod.GET);
    
    Assert.assertTrue(request instanceof LoggingClientHttpRequest);
    
  }
  
  @Test
  public void mustOverrideRequestLoggerWhenCallingSetRequestLogger() {
    
    LoggingRestTemplate restTemplate = new LoggingRestTemplate();

    Assert.assertTrue( restTemplate.getRequestLogger() instanceof CurlRequestLogger);
    
    RequestLogger mockedRequestLogger = Mockito.mock(RequestLogger.class);
    
    restTemplate.setRequestLogger(mockedRequestLogger);
    
    Assert.assertSame(mockedRequestLogger, restTemplate.getRequestLogger());
    
  }
  
}
