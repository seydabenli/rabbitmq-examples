package com.example.direct.exchange.config;

public enum HttpStatus {
  OK(200, "The request has succeeded.", MessageType.INFO),
  BAD_REQUEST(400, "Bad Request", MessageType.WARN),
  UNAUTHORIZED(401, "Unauthorized", MessageType.ERROR),
  FORBIDDEN(403, "Forbidden", MessageType.ERROR),
  NOT_FOUND(404, "Not Found", MessageType.ERROR),
  INTERNAL_SERVER_ERROR(500, "Internal Server Error", MessageType.ERROR);

  private final int val;
  private final String reasonPhrase;
  private final MessageType messageType;


  HttpStatus(int val, String reasonPhrase, MessageType messageType) {
    this.val = val;
    this.reasonPhrase = reasonPhrase;
    this.messageType = messageType;
  }

  public static HttpStatus of(int statusCode) {
    for (HttpStatus status : values()) {
      if (status.val == statusCode) {
        return status;
      }
    }
    return HttpStatus.BAD_REQUEST;
  }

  public int getVal() {
    return val;
  }

  public String getReasonPhrase() {
    return reasonPhrase;
  }

  public String getMessageType() {
    return messageType.getValText();
  }
}
