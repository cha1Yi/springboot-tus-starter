package com.yidan.tus.server.config;

import com.yidan.tus.server.TusFileUploadResolver;
import com.yidan.tus.server.expire.TusDirectoryExpireCleaner;
import me.desair.tus.server.TusFileUploadService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Wuxuan.Chai
 * @desc tus配置类
 * @created 2021/11/18 4:33 下午
 **/
@Configuration
@EnableAutoConfiguration
@EnableConfigurationProperties(TusProperties.class)
@ConditionalOnProperty(prefix = "com.yidan.tus", name = "enable", havingValue = "true", matchIfMissing = true)
public class TusConfiguration {

    @Bean
    public TusFileUploadService tusFileUploadService(TusProperties tusProperties) {
        final TusFileUploadService tusFileUploadService = new TusFileUploadService();
        tusFileUploadService.withStoragePath(tusProperties.getTusUploadDirectory().toString());
        tusFileUploadService.withUploadURI(tusProperties.getUploadURI());
        if (tusProperties.isEnableClean()) {
            tusFileUploadService.withUploadExpirationPeriod(tusProperties.getExpireTime().toMillis());
        }
        return tusFileUploadService;
    }


    @Bean
    public TusFileUploadResolver tusFileUploadResolver(TusFileUploadService tusFileUploadService, TusProperties tusProperties) {
        return new TusFileUploadResolver(tusFileUploadService, tusProperties);
    }

    @Bean
    @ConditionalOnProperty(prefix = "com.yidan.tus", name = "enable-clean", havingValue = "true")
    public TusDirectoryExpireCleaner tusDirectoryExpireCleaner(TusProperties tusProperties, TusFileUploadService tusFileUploadService) {
        return new TusDirectoryExpireCleaner(tusProperties, tusFileUploadService);
    }

}
