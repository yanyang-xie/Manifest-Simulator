package com.thistech.simulator.controller;

import com.thistech.simulator.bean.RestServiceError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

/**
 * Created by yanyang.xie@gmail.com on 21/06/2017.
 */

public class BaseController {
    private static Logger logger = LoggerFactory.getLogger(BaseController.class);

    @RequestMapping(value = "/status", method = {RequestMethod.GET, RequestMethod.POST})
    public
    @ResponseBody
    String status() {
        return "Running";
    }

    @RequestMapping(value = "/*")
    @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Not support this request")
    public void notSupport(HttpServletRequest req) {
        logger.error("Not support request {}", req.getRequestURL().toString());
    }

    @ResponseStatus(value= HttpStatus.BAD_REQUEST)  // 400
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public RestServiceError handleValidationException(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> errors = ex.getConstraintViolations();
        StringBuilder strBuilder = new StringBuilder();
        for (ConstraintViolation<?> violation : errors) {
            strBuilder.append(violation.getMessage() + "\n");
        }
        return RestServiceError.build(RestServiceError.Type.VALIDATION_ERROR, strBuilder.toString());
    }

    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)  // 500
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public RestServiceError handleException(Exception ex) {
        return RestServiceError.build(RestServiceError.Type.INTERNAL_SERVER_ERROR, ex.getMessage());
    }
}
