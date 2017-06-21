package com.thistech.simulator.controller;

import com.thistech.simulator.bean.ManifestContent;
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

@Controller
@RequestMapping
public class ManifestController extends BaseController{
    private static Logger logger = LoggerFactory.getLogger(ManifestController.class);

    @RequestMapping(value = "/origin/playlists/**/{contentName:.*}", method = {RequestMethod.GET, RequestMethod.POST})
    public
    @ResponseBody
    String getManifest(HttpServletRequest request, @PathVariable("contentName") String contentName) throws Exception {
        String requestURI = request.getRequestURI();
        logger.debug("Request URI: {}, file name is: {}", requestURI, contentName);

        if (ManifestContent.cachedFiles.containsKey(contentName)) {
            logger.debug("Found content by content name {}", contentName);
            return ManifestContent.cachedFiles.get(contentName);
        } else {
            throw new Exception(String.format("not found content by name %s", contentName));
        }
    }
}
