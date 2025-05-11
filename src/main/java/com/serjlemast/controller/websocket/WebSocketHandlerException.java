package com.serjlemast.controller.websocket;

public class WebSocketHandlerException extends RuntimeException {

  public WebSocketHandlerException(String errorMessage, Exception e) {
    super(errorMessage, e);
  }
}
