package com.example.direct.exchange.config;

public enum MessageType { 
  INFO(1, "info"),
  WARN(2, "warn"),
  ERROR(3, "error");

  private int val;
  private String valText;

  MessageType(int val, String valText) {
    this.val = val;
    this.valText = valText;
  }

  public static MessageType of(int val) {
    for (MessageType inam : values()) {
      if (inam.getVal() == val) {
        return inam;
      }
    }
    return INFO;
  }

  public int getVal() {
    return val;
  }

  public String getValText() {
    return valText;
  }
}
