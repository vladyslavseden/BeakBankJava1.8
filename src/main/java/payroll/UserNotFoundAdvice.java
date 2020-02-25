package payroll;

import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class UserNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(UserNotFoundExption.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String userNotFoundExption(UserNotFoundExption ex){
      return ex.getMessage();
    }

}
