package com.yidan;

import com.yidan.tus.client.TusFileClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.nio.file.Paths;

/**
 * @author Wuxuan.Chai
 * @desc
 * @created 2021/11/19 5:02 下午
 **/
public class TusFileClientTest {
    @Test
    public void executeTest() {
        Assertions.assertDoesNotThrow(() -> {
            final TusFileClient tusFileClient = new TusFileClient();
            final String uploadFilePath = "/Users/wuxuan.chai/Documents/小老鼠已然成精.mp4";
            tusFileClient.execute(new URL("http://localhost:8080/file/v1/upload/"), Paths.get(uploadFilePath));
        });
    }
}
