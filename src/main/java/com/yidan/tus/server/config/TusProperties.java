package com.yidan.tus.server.config;

import com.yidan.tus.server.utils.DurationUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

/**
 * @author Wuxuan.Chai
 * @desc Tus配置
 * @created 2021/11/18 4:54 下午
 **/
@ConfigurationProperties(prefix = "com.yidan.tus")
public class TusProperties {

    /**
     * 是否启用tus-springboot 插件
     */
    private boolean enable = true;

    /**
     * 上传的回调地址
     */
    private String uploadURI;

    /**
     * tus的切片文件保存位置
     */
    private Path tusUploadDirectory;

    /**
     * 上传成功后文件的保存位置
     */
    private Path appUploadDirectory;

    /**
     * 开启定时清理任务
     */
    private boolean enableClean = false;

    /**
     * 超时时间设置
     */
    private Duration expireTime = Duration.ZERO;

    public Duration getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = DurationUtils.ofString(expireTime);
    }

    public boolean isEnableClean() {
        return enableClean;
    }

    public void setEnableClean(boolean enableClean) {
        this.enableClean = enableClean;
    }

    public String getUploadURI() {
        return uploadURI;
    }

    public void setUploadURI(String uploadURI) {
        this.uploadURI = uploadURI;
    }

    public Path getTusUploadDirectory() {
        return tusUploadDirectory;
    }

    public void setTusUploadDirectory(String tusUploadDirectory) {
        this.tusUploadDirectory = Paths.get(tusUploadDirectory);
    }

    public Path getAppUploadDirectory() {
        return appUploadDirectory;
    }

    public void setAppUploadDirectory(String appUploadDirectory) {
        this.appUploadDirectory = Paths.get(appUploadDirectory);
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

}
