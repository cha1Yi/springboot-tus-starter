package com.yidan.tus.server;

import com.yidan.tus.server.config.TusProperties;
import me.desair.tus.server.TusFileUploadService;
import me.desair.tus.server.exception.TusException;
import me.desair.tus.server.upload.UploadInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * @author Wuxuan.Chai
 * @desc 断点续传的逻辑
 * @created 2021/11/19 11:42 上午
 **/
public class TusFileUploadResolver {

    private final TusFileUploadService tusFileUploadService;

    private final TusProperties tusProperties;

    public TusFileUploadResolver(TusFileUploadService tusFileUploadService, TusProperties tusProperties) {
        this.tusFileUploadService = tusFileUploadService;
        this.tusProperties = tusProperties;
    }

    public void upload(HttpServletRequest request, HttpServletResponse response) {
        try {
            tusFileUploadService.process(request, response);
            final String uploadURI = request.getRequestURI();
            final UploadInfo uploadInfo = tusFileUploadService.getUploadInfo(uploadURI);
            if (uploadInfo != null && !uploadInfo.isUploadInProgress()) {
                final InputStream uploadedBytes = tusFileUploadService.getUploadedBytes(uploadURI);
                final String appUploadDirectory = tusProperties.getAppUploadDirectory().toString();
                final Path outPut = Paths.get(appUploadDirectory).resolve(uploadInfo.getFileName());
                Files.copy(uploadedBytes, outPut, StandardCopyOption.REPLACE_EXISTING);
                tusFileUploadService.deleteUpload(uploadURI);
                uploadedBytes.close();
            }
        } catch (TusException | IOException e) {
            throw new RuntimeException("上传失败，原因：" + e.getMessage(), e);
        }
    }
}
