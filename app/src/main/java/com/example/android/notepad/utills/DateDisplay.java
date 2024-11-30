package com.example.android.notepad.utills;



import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateDisplay {

    // 固定日期格式
    private static final String PATTERN_FULL = "yyyy年MM月dd日 HH:mm";
    // 定义一个静态的 SimpleDateFormat 对象
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(PATTERN_FULL, Locale.getDefault());
    static {
        // 设置时区为东八区（中国标准时间）
        DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("GMT+8"));
    }
    /**
     * 根据时间戳返回固定格式的日期字符串
     *
     * @param time 时间戳（毫秒）
     * @return 格式化后的日期字符串
     */
    public static String showTime(Long time) {
        if (time == null) {
            return ""; // 如果时间为空，返回空字符串
        }
        // 格式化日期并返回
        return DATE_FORMAT.format(new Date(time));
    }
}
