package com.thistech.simulator.controller;

import com.thistech.simulator.bean.MSProperties;
import com.thistech.simulator.bean.ManifestContent;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

/**
 * Created by yanyang.xie@gmail.com on 21/06/2017.
 */

@Controller
@RequestMapping
public class ManifestController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(ManifestController.class);

    @Autowired
    public MSProperties msProperties;

    @Autowired ManifestContent manifestContent;

    @RequestMapping(value = "/origin/playlists/**/{contentName:.*}", method = {RequestMethod.GET, RequestMethod.POST})
    public
    @ResponseBody
    String getManifest(HttpServletRequest request, @PathVariable("contentName") String contentName) throws Exception {
        String requestURI = request.getRequestURI();
        logger.debug("Request URI: {}, file name is: {}", requestURI, contentName);

        delay();

        String contentSeedSuffix = msProperties.getContentSeedSuffix();
        if (StringUtils.containsIgnoreCase(contentName, contentSeedSuffix)){
            return fetchMSWithContentSeedSuffix(contentName);
        }else{
            return manifestContent.fetchMSFromCache(contentName);
        }
    }

    // To generate more manifests by one content name, ms simulator support following format
    // http://localhost:8089/origin/playlists/friends_ad.m3u8@adsuffix12
    private String fetchMSWithContentSeedSuffix(String contentName) throws Exception{
        String contentSeedSuffix = msProperties.getContentSeedSuffix();
        String contentMedisSuffix = msProperties.getContentMedisSuffix();
        String adSeedSuffixTag = contentSeedSuffix + StringUtils.substringAfter(contentName, contentSeedSuffix);

        contentName = StringUtils.substringBeforeLast(contentName, contentSeedSuffix);
        String content = manifestContent.fetchMSFromCache(contentName);

        if (!StringUtils.containsIgnoreCase(content, msProperties.getContentIndexFlag())){
            // To index content, need add adSeedSuffix to each bitrate url
            content = StringUtils.replace(content, contentMedisSuffix, contentMedisSuffix + adSeedSuffixTag);
        }
        return content;
    }

    private void delay() {
        int delayTime = getRandom(msProperties.getResponseMinDelayTime(), msProperties.getResponseMaxDelayTime());
        logger.debug("Response will be delayed {}", delayTime);
        try {
            Thread.sleep(delayTime);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static int getRandom(int min, int max) {
        if (min < 0 || max <= 0) {
            return 0;
        }

        if (min >= max) {
            return max;
        }

        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        return s;
    }
}
