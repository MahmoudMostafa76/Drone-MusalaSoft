package com.musalasoft.drone.drone.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.musalasoft.drone.drone.model.dto.ErrorResponse;


@RestControllerAdvice
public class DroneExceptionAdvice {
  @ExceptionHandler(DroneOverweightException.class)
  public ResponseEntity<ErrorResponse> handleDroneOverweightException(DroneOverweightException ex) {
    ErrorResponse errorResponse =
        new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }

  @ExceptionHandler(InsufficientBatteryException.class)
  public ResponseEntity<ErrorResponse> handleInsufficientBatteryException(
      InsufficientBatteryException ex) {
    ErrorResponse errorResponse =
        new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }

  @ExceptionHandler(DroneNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleDroneNotFoundException(DroneNotFoundException ex) {
    ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
  }
}
