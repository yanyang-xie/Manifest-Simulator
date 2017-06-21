package com.thistech.simulator.bean;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Created by xieyanyang on 20/06/2017.
 */
public class ManifestContent {
    public static HashMap<String, String> cachedFiles = new HashMap<>();
    private static Logger logger = LoggerFactory.getLogger(ManifestContent.class);

    @Value("${response.url.redundancy}")
    private static String responseURLRedundancy;

    public static void initResponseFiles() {
        logger.info("Init manifest contents for folder /content");
        Collection<File> fileList = FileUtils.listFiles(new File("content"), null, false);
        for (File file : fileList) {
            getResponseByFileName(file.getName());
        }
    }

    public static String getResponseByFileName(String fileName) {
        String response = cachedFiles.get(fileName);
        if (response == null) {
            try {
                response = FileUtils.readFileToString(new File("content/" + fileName));
                if (response != null) {
                    logger.debug("Read response from content/{}:\r\n{}", fileName, response);
                    if (StringUtils.isNoneEmpty(responseURLRedundancy)) {
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

    public static String enlarge_bitrate_response(String response_string) {
        if (StringUtils.isEmpty(response_string) || StringUtils.containsIgnoreCase(response_string, "#EXT-X-STREAM-INF")) {
            return response_string;
        }

        List<String> contents = new ArrayList<>();
        for (String line : response_string.split("\n")) {
            if (StringUtils.startsWith(line, "#")) {
                contents.add(line);
            } else {
                contents.add(responseURLRedundancy + line);
            }
        }
        return StringUtils.join(contents, "\n");
    }

}
