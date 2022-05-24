package skuc.io.skuciocore.exceptions;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;

public class ErrorObject {
  private String message;
  private Object payload;
  private HttpStatus code;
  private ZonedDateTime timestamp;

  public ErrorObject() {
  }

  public ErrorObject(String message, Object payload, HttpStatus code) {
    this.message = message;
    this.payload = payload;
    this.code = code;
    this.timestamp = ZonedDateTime.now(ZoneId.of("Z"));
  }

  public ErrorObject(String message, HttpStatus code) {
    this(message, null, code);
  }

  public String getMessage() {
    return this.message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Object getPayload() {
    return this.payload;
  }

  public void setPayload(Object payload) {
    this.payload = payload;
  }

  public HttpStatus getCode() {
    return this.code;
  }

  public void setCode(HttpStatus code) {
    this.code = code;
  }

  public ZonedDateTime getTimestamp() {
    return this.timestamp;
  }

  public void setTimestamp(ZonedDateTime timestamp) {
    this.timestamp = timestamp;
  }

}