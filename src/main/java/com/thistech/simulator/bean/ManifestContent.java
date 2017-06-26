package com.thistech.simulator.bean;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Created by yanyang.xie@gmail.com on 21/06/2017.
 */
@Component
public class ManifestContent {
    public static HashMap<String, String> cachedFiles = new HashMap<>();
    private static Logger logger = LoggerFactory.getLogger(ManifestContent.class);

    @Autowired
    public MSProperties msProperties;

    public void initResponseFiles() {
        logger.info("Init manifest contents for folder /content");
        Collection<File> fileList = FileUtils.listFiles(new File("content"), null, false);
        fileList.forEach(file -> getResponseByFileName(file.getName()));
        /**
        for (File file : fileList) {
            getResponseByFileName(file.getName());
        }*/
    }

    private String getResponseByFileName(String fileName) {
        String response = cachedFiles.get(fileName);
        if (response == null) {
            try {
                response = FileUtils.readFileToString(new File("content/" + fileName));
                if (response != null) {
                    logger.debug("Read response from content/{}:\r\n{}", fileName, response);
                    if (StringUtils.isNoneEmpty(msProperties.getResponseUrlRedundancy())) {
                        response = enlarge_bitrate_response(response);
                        logger.debug("Enlarge bitrate response by redundancy string, enlarged response is:\n{}", response);
                    }

                    cachedFiles.put(fileName, response);
                }
            } catch (IOException e) {
                logger.error("Failed to read file: content/" + fileName);
            }
        }

        if (response == null) {
            response = "";
        }
        return response;
    }

    private String enlarge_bitrate_response(String responseString) {
        if (StringUtils.isEmpty(responseString) || StringUtils.containsIgnoreCase(responseString, "#EXT-X-STREAM-INF")) {
            return responseString;
        }

        List<String> contents = new ArrayList<>();
        for (String line : responseString.split("\n")) {
            if (StringUtils.startsWith(line, "#")) {
                contents.add(line);
            } else {
                contents.add(msProperties.getResponseUrlRedundancy() + line);
            }
        }
        return StringUtils.join(contents, "\n");
    }

    public String fetchMSFromCache(String contentName) throws Exception{
         if (cachedFiles.containsKey(contentName)){
             return cachedFiles.get(contentName);
         }else{
             logger.error("Not found content by name {}", contentName);
             throw new Exception(String.format("Not found content by name %s", contentName));
         }
    }

}
