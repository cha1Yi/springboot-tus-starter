package com.yidan.tus.server.utils;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Wuxuan.Chai
 * @desc
 * @created 2021/11/19 11:25 上午
 **/
public final class DurationUtils {
    public static Duration ofString(String str) {
        final String expireTimeRegx = "^([1-9][0-9]{0,4})((?i)(HOURS|DAYS|MINUTES))";
        final Pattern compile = Pattern.compile(expireTimeRegx);
        final Matcher matcher = compile.matcher(str);
        if (matcher.matches()) {
            final String num = matcher.group(1);
            final String timeUnit = matcher.group(2);
            final ChronoUnit chronoUnit = ChronoUnit.valueOf(timeUnit.toUpperCase());
            return Duration.of(Long.parseLong(num), chronoUnit);
        }
        throw new IllegalArgumentException("不合法日期配置："+str);
    }
}
