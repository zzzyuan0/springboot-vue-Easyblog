package cn.zzzyuan.common.exception;

import cn.zzzyuan.common.lang.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 *  @ ControllerAdvice注解有这个注解的类中的方法的某些注解会应用到所有的Controller里，其中就包括@ExceptionHandler注解。
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 带有@ResponseStatus注解的异常类会被ResponseStatusExceptionResolver 解析。可以实现自定义的一些异常,同时在页面上进行显示。
     *  @ ExceptionHandler(value = RuntimeException.class) 捕获 RuntimeException 异常
     *  RuntimeException是那些可能在 Java 虚拟机正常运行期间抛出的异常的超类。
     *  可能在执行方法期间抛出但未被捕获的RuntimeException 的任何子类都无需在 throws 子句中进行声明。它是Exception的子类。
      */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = RuntimeException.class)
    public Result handler(RuntimeException e){
        log.error("运行时异常:----------",e);
        return Result.fail(e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = ShiroException.class)
    public Result handler(ShiroException e){
        log.error("运行时异常:----------",e);
        return Result.fail(401,e.getMessage(),null);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result handler(MethodArgumentNotValidException e){
        log.error("实体校验时异常:----------",e);
        BindingResult bindingResult = e.getBindingResult();
        ObjectError objectError = bindingResult.getAllErrors().stream().findFirst().orElse(null);
        return Result.fail(objectError != null ? objectError.getDefaultMessage() : null);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = IllegalArgumentException.class)
    public Result handler(IllegalArgumentException e){
        log.error("Assert异常:----------",e);

        return Result.fail(e.getMessage());
    }
}
