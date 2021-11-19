package com.yidan.utils;

import com.yidan.tus.server.utils.DurationUtils;
import org.junit.jupiter.api.Test;

import java.time.Duration;

/**
 * @author Wuxuan.Chai
 * @desc
 * @created 2021/11/18 5:33 下午
 **/

public class DurationParseTest {

    @Test
    public void string2Duration() {
        final Duration duration = DurationUtils.ofString("1days");
        final long seconds = duration.getSeconds();
        System.out.println(seconds);
        assert seconds > 0;
    }
}
