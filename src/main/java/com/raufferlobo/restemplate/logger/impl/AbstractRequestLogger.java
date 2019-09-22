package com.raufferlobo.restemplate.logger.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.raufferlobo.restemplate.logger.RequestLogger;

public abstract class AbstractRequestLogger implements RequestLogger {

  private static final Logger LOGGER = LoggerFactory.getLogger(RequestLogger.class);
  protected static final String LINE_SEPARATOR = System.lineSeparator();
  private static final String CURL_COMMAND_START = LINE_SEPARATOR + "%s \\";

  public void log(String uri, String method, Map<String, String> headers, String body) {

    StringBuilder command = new StringBuilder();

    command.append(String.format(CURL_COMMAND_START, getToolName()));
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
