package com.raufferlobo.restemplate.logging.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.raufferlobo.restemplate.logging.RequestLogger;

public abstract class AbstractRequestLogger implements RequestLogger {

  private static final Logger LOGGER = LoggerFactory.getLogger(RequestLogger.class);
  protected static final String LINE_SEPARATOR = System.lineSeparator();

  public void log(String uri, String method, Map<String, String> headers, String body) {

    StringBuilder command = new StringBuilder();

    command.append(getToolName());
    appendMethod(command, method);
    appendUri(command, uri);
    appendHeaders(command, headers);
    appendBody(command, body);

    LOGGER.debug(command.toString());

  }

  protected abstract String getToolName();

  protected abstract void appendUri(StringBuilder command, String uri);

  protected abstract void appendMethod(StringBuilder command, String method);

  protected abstract void appendBody(StringBuilder command, String body);

  protected abstract void appendHeaders(StringBuilder command, Map<String, String> headers);

}
