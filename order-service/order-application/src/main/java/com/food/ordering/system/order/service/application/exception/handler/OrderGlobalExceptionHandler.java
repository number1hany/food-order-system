package com.food.ordering.system.order.service.application.exception.handler;

import com.food.ordering.system.application.handler.ErrorDTO;
import com.food.ordering.system.application.handler.GlobalExceptionHandler;
import com.food.ordering.system.order.service.domain.exception.OrderDomainException;
import com.food.ordering.system.order.service.domain.exception.OrderNotFoundException;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@Slf4j
@ControllerAdvice
public class OrderGlobalExceptionHandler extends GlobalExceptionHandler {

  @ResponseBody
  @ExceptionHandler(value = {OrderDomainException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorDTO handleException(OrderDomainException orderDomainException) {
    log.error(orderDomainException.getMessage(), orderDomainException);
    return ErrorDTO.builder()
        .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
        .message(orderDomainException.getMessage())
        .build();
  }

  @ResponseBody
  @ExceptionHandler(value = {OrderDomainException.class})
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorDTO handleException(OrderNotFoundException orderNotFoundException) {
    log.error(orderNotFoundException.getMessage(), orderNotFoundException);
    return ErrorDTO.builder()
        .code(HttpStatus.NOT_FOUND.getReasonPhrase())
        .message(orderNotFoundException.getMessage())
        .build();
  }

  @ResponseBody
  @ExceptionHandler(value = {ValidationException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorDTO handleException(ValidationException validationException) {
    ErrorDTO errorDTO;
    if (validationException instanceof ConstraintViolationException) {
      String violations = extractViolationsFromException((ConstraintViolationException) validationException);
      log.error(violations, validationException);
      errorDTO = ErrorDTO.builder()
          .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
          .message(violations)
          .build();
    } else {
      String exceptionMessage = validationException.getMessage();
      log.error(exceptionMessage, validationException);
      errorDTO = ErrorDTO.builder()
          .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
          .message(exceptionMessage)
          .build();
    }
    return errorDTO;
  }

  private String extractViolationsFromException(ConstraintViolationException validationException) {
    return validationException.getConstraintViolations()
        .stream()
        .map(ConstraintViolation::getMessage)
        .collect(Collectors.joining("--"));
  }
}
