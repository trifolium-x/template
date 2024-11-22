package com.example.template.services.common.configuration;

import cn.hutool.core.collection.CollUtil;
import com.example.template.common.helper.exception.AppBaseException;
import com.example.template.common.helper.exception.NoAuthException;
import com.example.template.common.helper.exception.TokenInvalidException;
import com.example.template.common.response.ResponseResult;
import com.example.template.common.response.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * boot web相关配置
 *
 * @author trifolium
 * @since 2017/7/16
 */
@Slf4j
public class BaseWebMvcConfig implements WebMvcConfigurer {

    /**
     * 自定义异常处理
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ResponseResult<Object>> exceptionHandler(Exception exception) {

        String message;
        if (exception instanceof AppBaseException) {

            message = exception.getMessage();

            if (exception instanceof TokenInvalidException) {
                return new ResponseEntity<>(ResponseResult.failure(message), HttpStatus.OK);
            }
            if (exception instanceof NoAuthException) {

                return new ResponseEntity<>(ResponseResult.failure(message), HttpStatus.OK);
            }

            return new ResponseEntity<>(ResponseResult.failure(message), HttpStatus.OK);
        }
        // 下面是对参数校验异常的处理
        message = validateExceptionProcess(exception);
        if (message != null) {
            log.debug(message);
            return new ResponseEntity<>(ResponseResult.failure(message), HttpStatus.OK);
        }

        log.error("result_code: {}, msg: {}", exception.getMessage(), ResultCode.EXCEPTION, exception);

        return new ResponseEntity<>(ResponseResult.error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String validateExceptionProcess(Exception exception) {
        String message = null;
        if (exception instanceof MethodArgumentNotValidException ex) {
            List<ObjectError> errors = ex.getBindingResult().getAllErrors();

            if (!CollectionUtils.isEmpty(errors)) {
                message = CollUtil.join(errors.stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .collect(Collectors.toList()), ",");
            }
            return message;
        }

        if (exception instanceof BindException bindE) {
            List<ObjectError> errors = bindE.getAllErrors();

            if (!CollectionUtils.isEmpty(errors)) {
                message = CollUtil.join(errors.stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .collect(Collectors.toList()), ",");
            }

            return message;
        }

        if (exception instanceof ConstraintViolationException conVE) {

            return CollUtil.join(conVE.getConstraintViolations().stream()
                    .map(ConstraintViolation::getMessage).collect(Collectors.toList()), ",");
        }

        if (exception instanceof MissingServletRequestParameterException msrpe) {
            return "parameters are missing:" + msrpe.getParameterName();
        }

        return null;
    }

}