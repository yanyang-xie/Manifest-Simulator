package com.thistech.simulator.controller;

import com.thistech.simulator.bean.MSProperties;
import com.thistech.simulator.bean.ManifestContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping
public class ManifestController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(ManifestController.class);

    @Autowired
    public MSProperties msProperties;

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
