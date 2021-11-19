package com.yidan.tus.client;

import io.tus.java.client.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;

/**
 * 作为客户端往服务端断点续传文件
 *
 * @author Wuxuan.Chai
 * @desc 作为客户端往服务端断点续传文件
 * @created 2021/11/19 4:46 下午
 **/
public class TusFileClient {
    private final Log log = LogFactory.getLog(TusFileClient.class);

    //default 1MB
    private int size = 1024 * 1024;

    public TusFileClient(int size) {
        this.size = size;
    }

    public TusFileClient() {
    }

    public void execute(URL url, Path path, TusURLStore tusURLStore) {
        final TusClient tusClient = new TusClient();
        tusClient.setUploadCreationURL(url);
        tusClient.enableResuming(tusURLStore);
        try {
            final TusUpload tusUpload = new TusUpload(path.toFile());
            final TusExecutor tusExecutor = new TusExecutor() {
                @Override
                protected void makeAttempt() throws ProtocolException, IOException {
                    final TusUploader tusUploader = tusClient.resumeOrCreateUpload(tusUpload);
                    tusUploader.setChunkSize(size);
                    do {
                        final long totalSize = tusUpload.getSize();
                        final long offset = tusUploader.getOffset();
                        double progress = (double) offset / totalSize * 100;
                        log.debug(String.format("Upload at %06.2f%%.", progress));
                    } while (tusUploader.uploadChunk() > -1);

                    tusUploader.finish();
                    log.debug("Upload finished.");
                    log.debug(String.format("Upload available at: %s", tusUploader.getUploadURL().toString()));
                }
            };
            tusExecutor.makeAttempts();
        } catch (ProtocolException | IOException e) {
            e.printStackTrace();
        }
    }

    public void execute(URL url, Path path) {
        execute(url, path, new TusURLMemoryStore());
    }
}
