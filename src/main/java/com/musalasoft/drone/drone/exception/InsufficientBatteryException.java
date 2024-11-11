package com.musalasoft.drone.drone.exception;

public class InsufficientBatteryException extends RuntimeException {
  public InsufficientBatteryException(String message) {
    super(message);
  }
}
