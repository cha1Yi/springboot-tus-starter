package com.yidan.tus.server.expire;

import com.yidan.tus.server.config.TusProperties;
import me.desair.tus.server.TusFileUploadService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.CommandLineRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Wuxuan.Chai
 * @desc
 * @created 2021/11/19 3:44 下午
 **/

public class TusDirectoryExpireCleaner implements CommandLineRunner {

    private final Log log = LogFactory.getLog(TusDirectoryExpireCleaner.class);


    private TusProperties tusProperties;

    private TusFileUploadService tusFileUploadService;

    public TusDirectoryExpireCleaner(TusProperties tusProperties, TusFileUploadService tusFileUploadService) {
        this.tusProperties = tusProperties;
        this.tusFileUploadService = tusFileUploadService;
    }


    //定时任务
    private static final ScheduledExecutorService SCHEDULED_EXECUTOR_SERVICE = Executors.newSingleThreadScheduledExecutor();


    @Override
    public void run(String... args) {
        final Duration expireTime = tusProperties.getExpireTime();
        log.info("tus enable clean model");
        SCHEDULED_EXECUTOR_SERVICE.schedule(() -> {
            final Path lockDir = tusProperties.getTusUploadDirectory().resolve("locks");
            if (Files.exists(lockDir)) {
                try {
                    log.info("clean up tus locks file");
                    tusFileUploadService.cleanup();
                } catch (IOException e) {
                    throw new RuntimeException("error during clean up", e);
                }
            }
        }, expireTime.getSeconds(), TimeUnit.SECONDS);
    }

}
