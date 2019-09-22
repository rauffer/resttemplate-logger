package com.raufferlobo.restemplate;

import java.io.IOException;
import java.net.URI;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;

import com.raufferlobo.restemplate.logger.RequestLogger;
import com.raufferlobo.restemplate.logger.impl.CurlRequestLogger;

public class RestTemplateLoggerTest {

  @Test
  public void mustSetCurlRequestLoggerAsDefaultRequestLogger() {
    
    RestTemplateLogger restTemplate = new RestTemplateLogger();
    
    Assert.assertEquals(CurlRequestLogger.class, restTemplate.getRequestLogger().getClass());
    
  }
  
  @Test
  public void mustUseRequestLoggerSetInConstructor() {
    
    RequestLogger mockedRequestLogger = Mockito.mock(RequestLogger.class);
    
    RestTemplateLogger restTemplate = new RestTemplateLogger(mockedRequestLogger);
    
    Assert.assertSame(mockedRequestLogger, restTemplate.getRequestLogger());
    
  }
  
  @Test
  public void mustCreateLoggingClientHttpRequestWhenCreatingRequest() throws IOException {
    
    RestTemplateLogger restTemplate = new RestTemplateLogger();
    
    ClientHttpRequest request = restTemplate.createRequest(URI.create("http://localhost"), HttpMethod.GET);
    
    Assert.assertTrue(request instanceof ClientHttpRequestLogger);
    
  }
  
  @Test
  public void mustOverrideRequestLoggerWhenCallingSetRequestLogger() {
    
    RestTemplateLogger restTemplate = new RestTemplateLogger();

    Assert.assertTrue( restTemplate.getRequestLogger() instanceof CurlRequestLogger);
    
    RequestLogger mockedRequestLogger = Mockito.mock(RequestLogger.class);
    
    restTemplate.setRequestLogger(mockedRequestLogger);
    
    Assert.assertSame(mockedRequestLogger, restTemplate.getRequestLogger());
    
  }
  
}
