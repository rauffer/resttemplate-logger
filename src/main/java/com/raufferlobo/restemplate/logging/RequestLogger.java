package com.raufferlobo.restemplate.logging;

import java.util.Map;

/**
 * Interface that defines the request logger contract.
 * 
 * @author Rauffer Lobo
 *
 */
public interface RequestLogger {
  
  /**
   * Logs the request information.
   * 
   * @param uri - the URI
   * @param method - the request method
   * @param headers - the request headers
   * @param body - the request body
   */
  void log(String uri, String method,  Map<String, String> headers, String body);

}