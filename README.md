# TUS 结合 Spring-Boot 实现断点续传

最近在做文件断点续传的springboot实现，前端使用的TUS插件，于是整理一个SpringBoot插件便于开发使用。

## Java Client 实现

使用场景，当业务需要往一个支持TUS协议的断点续传服务发送文件上传请求时。可以使用如下的方式实现断点续传。

```java
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
```

## Java Server 实现

使用场景，当需要开发一个支持断点续传的文件服务时，可以实现如下代码，支持TUS客户端传递文件。

添加依赖

```java

@RestController
@RequestMapping("/upload")
@SpringBootApplication
public class TusApplication {

    @Resource
    private TusFileUploadResolver tusFileUploadResolver;

    public static void main(String[] args) {
        SpringApplication.run(TusApplication.class, args);
    }

    /**
     * 上传接口
     * @param request 请求
     * @param response 响应
     */
    @RequestMapping(value = {"/", "/**"}, method = {RequestMethod.POST, RequestMethod.PATCH, RequestMethod.HEAD, RequestMethod.DELETE, RequestMethod.GET})
    public void upload(HttpServletRequest request, HttpServletResponse response) {
        tusFileUploadResolver.upload(request, response);
    }
}
```

相关配置

```properties
server.servlet.context-path=/file/v1
# 上传接口
com.yidan.tus.upload-URI=${server.servlet.context-path}/upload/
# tus的chunk文件存储位置
com.yidan.tus.tus-upload-directory=/Users/wuxuan.chai/Documents/GitHub/springboot-tus-starter/src/test/resources/file/tus
# 文件最终上传的位置
com.yidan.tus.app-upload-directory=/Users/wuxuan.chai/Documents/GitHub/springboot-tus-starter/src/test/resources/file
# 是否开启定时清理tus的lock文件，默认false
com.yidan.tus.enable-clean=true
# 定时周期
com.yidan.tus.expire-time=1MINUTES
```