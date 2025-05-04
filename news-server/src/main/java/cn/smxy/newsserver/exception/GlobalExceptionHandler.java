package cn.smxy.newsserver.exception;

import cn.smxy.newsserver.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@CrossOrigin
public class GlobalExceptionHandler {
    @ExceptionHandler(ServiceException.class)

    @ResponseBody
    public Result handle(ServiceException se){
        return Result.error(se.getCode(),se.getMessage());
    }
}
