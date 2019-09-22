package com.raufferlobo.restemplate.logger.impl;

import java.util.Map;

import org.springframework.util.StringUtils;

/**
 * Class responsible for logging request in CURL format.
 * 
 * @author Rauffer Lobo
 *
 */
public class CurlRequestLogger extends AbstractRequestLogger {

  private static final String CURL_METHOD_COMMAND = LINE_SEPARATOR + "-X %s \\";
  private static final String URI_COMMAND = LINE_SEPARATOR + "%s \\";
  private static final String HEADER_COMMAND = LINE_SEPARATOR + "-H '%s: %s' \\";
  private static final String BODY_COMMAND = LINE_SEPARATOR + "-d '%s' \\";

  @Override
  protected String getToolName() {
    return "curl";
  }

  @Override
  protected void appendUri(StringBuilder command, String uri) {
    command.append(String.format(URI_COMMAND, uri));
  }

  @Override
  protected void appendMethod(StringBuilder command, String method) {
    command.append(String.format(CURL_METHOD_COMMAND, method));
  }

  @Override
  protected void appendBody(StringBuilder command, String body) {

    if (StringUtils.isEmpty(body)) {
      return;
    }

    command.append(String.format(BODY_COMMAND, body));

  }

  @Override
  protected void appendHeaders(StringBuilder command, Map<String, String> headers) {

    headers.entrySet().forEach(entry -> {
      command.append(String.format(HEADER_COMMAND, entry.getKey(), entry.getValue()));
    });

  }

}
