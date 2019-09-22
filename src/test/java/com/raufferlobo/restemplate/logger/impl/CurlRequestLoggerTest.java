package com.raufferlobo.restemplate.logger.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.raufferlobo.restemplate.logger.impl.CurlRequestLogger;

public class CurlRequestLoggerTest {

  @Test
  public void toolNameMustBeCurl() {
    String toolName = new CurlRequestLogger().getToolName();
    Assert.assertEquals("curl", toolName);
  }
  
  @Test
  public void mustAppendUriInCurlFormat() {
    StringBuilder command = new StringBuilder();
    new CurlRequestLogger().appendUri(command, "http://localhost/my-app");
    Assert.assertEquals(System.lineSeparator() + "http://localhost/my-app \\", command.toString());
  }
  
  @Test
  public void mustAppendMethodInCurlFormat() {
    
    StringBuilder getCommand = new StringBuilder();
    new CurlRequestLogger().appendMethod(getCommand, "GET");
    
    StringBuilder postCommand = new StringBuilder();
    new CurlRequestLogger().appendMethod(postCommand, "POST");
    
    Assert.assertEquals(System.lineSeparator() + "-X GET \\", getCommand.toString());
    Assert.assertEquals(System.lineSeparator() + "-X POST \\", postCommand.toString());
    
  }
  
  @Test
  public void mustAppendBodyInCurlFormat() {
    StringBuilder command = new StringBuilder();
    new CurlRequestLogger().appendBody(command, "{}");
    Assert.assertEquals(System.lineSeparator() + "-d '{}' \\", command.toString());
  }
  
  @Test
  public void mustNotAppendBodyIfItIsEmpty() {
    StringBuilder command = new StringBuilder();
    new CurlRequestLogger().appendBody(command, null);
    new CurlRequestLogger().appendBody(command, "");
    Assert.assertEquals(0,command.length());
  }
  
  @Test
  public void mustAppendHeaderInCurlFormat() {
    StringBuilder command = new StringBuilder();
    Map<String, String> headers = new LinkedHashMap<>();
    headers.put("Content-Type", "application/json");
    headers.put("x-custom-id", "1234567890");
    new CurlRequestLogger().appendHeaders(command, headers);
    Assert.assertEquals(
        System.lineSeparator() + "-H 'Content-Type: application/json' \\" +
        System.lineSeparator() + "-H 'x-custom-id: 1234567890' \\", command.toString());
  }
  
}

