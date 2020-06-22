package cn.com.flying.utilsfinishingtest.utils.app;


import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cn.com.flying.utilsfinishingtest.utils.common.Utils;
import cn.com.flying.utilsfinishingtest.utils.storage.CloseUtils;
import cn.com.flying.utilsfinishingtest.utils.storage.FileUtils;

/**
 * getConfig                : 获取 log 配置
 * Config.setLogSwitch      : 设置 log 总开关
 * Config.setConsoleSwitch  : 设置 log 控制台开关
 * Config.setGlobalTag      : 设置 log 全局 tag
 * Config.setLogHeadSwitch  : 设置 log 头部信息开关
 * Config.setLog2FileSwitch : 设置 log 文件开关
 * Config.setDir            : 设置 log 文件存储目录
 * Config.setFilePrefix     : 设置 log 文件前缀
 * Config.setBorderSwitch   : 设置 log 边框开关
 * Config.setSingleTagSwitch: 设置 log 单一 tag 开关（为美化 AS 3.1 的 Logcat）
 * Config.setConsoleFilter  : 设置 log 控制台过滤器
 * Config.setFileFilter     : 设置 log 文件过滤器
 * Config.setStackDeep      : 设置 log 栈深度
 * Config.setStackOffset    : 设置 log 栈偏移
 * Config.setSaveDays       : 设置 log 可保留天数
 * Config.addFormatter      : 新增 log 格式化器
 * log                      : 自定义 tag 的 type 日志
 * v                        : tag 为类名的 Verbose 日志
 * vTag                     : 自定义 tag 的 Verbose 日志
 * d                        : tag 为类名的 Debug 日志
 * dTag                     : 自定义 tag 的 Debug 日志
 * i                        : tag 为类名的 Info 日志
 * iTag                     : 自定义 tag 的 Info 日志
 * w                        : tag 为类名的 Warn 日志
 * wTag                     : 自定义 tag 的 Warn 日志
 * e                        : tag 为类名的 Error 日志
 * eTag                     : 自定义 tag 的 Error 日志
 * a                        : tag 为类名的 Assert 日志
 * aTag                     : 自定义 tag 的 Assert 日志
 * file                     : log 到文件
 * json                     : log 字符串之 json
 * xml                      : log 字符串之 xml
 */
public class LogUtils {
    private LogUtils() {
        throw new UnsupportedOperationException("Can't instantiate...");
    }

    private static boolean logSwitch = true;
    private static boolean log2FileSwitch = false;
    private static char logFilter = 'v';
    private static String tag = "TAG";
    private static String dir = null;

    /**
     * 初始化函数
     * <p>与{@link #getBuilder()}两者选其一</p>
     *
     * @param logSwitch      日志总开关
     * @param log2FileSwitch 日志写入文件开关，设为true需添加权限 {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>}
     * @param logFilter      输入日志类型有{@code v, d, i, w, e}<br>v代表输出所有信息，w则只输出警告...
     * @param tag            标签
     */
    public static void init(boolean logSwitch, boolean log2FileSwitch, char logFilter, String tag) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            dir = Utils.getContext().getExternalCacheDir().getPath() + File.separator;
        } else {
            dir = Utils.getContext().getCacheDir().getPath() + File.separator;
        }
        logSwitch = logSwitch;
        log2FileSwitch = log2FileSwitch;
        logFilter = logFilter;
        tag = tag;
    }

    /**
     * 获取LogUtils建造者
     * <p>与{@link #init(boolean, boolean, char, String)}两者选其一</p>
     *
     * @return Builder对象
     */
    public static Builder getBuilder() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            dir = Utils.getContext().getExternalCacheDir().getPath() + File.separator + "log" + File.separator;
        } else {
            dir = Utils.getContext().getCacheDir().getPath() + File.separator + "log" + File.separator;
        }
        return new Builder();
    }

    public static class Builder {

        private boolean logSwitch = true;
        private boolean log2FileSwitch = false;
        private char logFilter = 'v';
        private String tag = "TAG";

        public Builder setLogSwitch(boolean logSwitch) {
            this.logSwitch = logSwitch;
            return this;
        }

        public Builder setLog2FileSwitch(boolean log2FileSwitch) {
            this.log2FileSwitch = log2FileSwitch;
            return this;
        }

        public Builder setLogFilter(char logFilter) {
            this.logFilter = logFilter;
            return this;
        }

        public Builder setTag(String tag) {
            this.tag = tag;
            return this;
        }

        public void create() {
            logSwitch = logSwitch;
            log2FileSwitch = log2FileSwitch;
            logFilter = logFilter;
            tag = tag;
        }
    }

    /**
     * Verbose日志
     *
     * @param msg 消息
     */
    public static void v(Object msg) {
        v(tag, msg);
    }

    /**
     * Verbose日志
     *
     * @param tag 标签
     * @param msg 消息
     */
    public static void v(String tag, Object msg) {
        v(tag, msg, null);
    }

    /**
     * Verbose日志
     *
     * @param tag 标签
     * @param msg 消息
     * @param tr  异常
     */
    public static void v(String tag, Object msg, Throwable tr) {
        log(tag, msg.toString(), tr, 'v');
    }

    /**
     * Debug日志
     *
     * @param msg 消息
     */
    public static void d(Object msg) {
        d(tag, msg);
    }

    /**
     * Debug日志
     *
     * @param tag 标签
     * @param msg 消息
     */
    public static void d(String tag, Object msg) {// 调试信息
        d(tag, msg, null);
    }

    /**
     * Debug日志
     *
     * @param tag 标签
     * @param msg 消息
     * @param tr  异常
     */
    public static void d(String tag, Object msg, Throwable tr) {
        log(tag, msg.toString(), tr, 'd');
    }

    /**
     * Info日志
     *
     * @param msg 消息
     */
    public static void i(Object msg) {
        i(tag, msg);
    }

    /**
     * Info日志
     *
     * @param tag 标签
     * @param msg 消息
     */
    public static void i(String tag, Object msg) {
        i(tag, msg, null);
    }

    /**
     * Info日志
     *
     * @param tag 标签
     * @param msg 消息
     * @param tr  异常
     */
    public static void i(String tag, Object msg, Throwable tr) {
        log(tag, msg.toString(), tr, 'i');
    }

    /**
     * Warn日志
     *
     * @param msg 消息
     */
    public static void w(Object msg) {
        w(tag, msg);
    }

    /**
     * Warn日志
     *
     * @param tag 标签
     * @param msg 消息
     */
    public static void w(String tag, Object msg) {
        w(tag, msg, null);
    }

    /**
     * Warn日志
     *
     * @param tag 标签
     * @param msg 消息
     * @param tr  异常
     */
    public static void w(String tag, Object msg, Throwable tr) {
        log(tag, msg.toString(), tr, 'w');
    }

    /**
     * Error日志
     *
     * @param msg 消息
     */
    public static void e(Object msg) {
        e(tag, msg);
    }

    /**
     * Error日志
     *
     * @param tag 标签
     * @param msg 消息
     */
    public static void e(String tag, Object msg) {
        e(tag, msg, null);
    }

    /**
     * Error日志
     *
     * @param tag 标签
     * @param msg 消息
     * @param tr  异常
     */
    public static void e(String tag, Object msg, Throwable tr) {
        log(tag, msg.toString(), tr, 'e');
    }

    /**
     * 根据tag, msg和等级，输出日志
     *
     * @param tag  标签
     * @param msg  消息
     * @param tr   异常
     * @param type 日志类型
     */
    private static void log(String tag, String msg, Throwable tr, char type) {
        if (logSwitch) {
            if ('e' == type && ('e' == logFilter || 'v' == logFilter)) {
                Log.e(tag, msg, tr);
            } else if ('w' == type && ('w' == logFilter || 'v' == logFilter)) {
                Log.w(tag, msg, tr);
            } else if ('d' == type && ('d' == logFilter || 'v' == logFilter)) {
                Log.d(tag, msg, tr);
            } else if ('i' == type && ('d' == logFilter || 'v' == logFilter)) {
                Log.i(tag, msg, tr);
            }
            if (log2FileSwitch) {
                log2File(type, tag, msg + '\n' + Log.getStackTraceString(tr));
            }
        }
    }

    /**
     * 打开日志文件并写入日志
     *
     * @param type    日志类型
     * @param tag     标签
     * @param content 内容
     **/
    private synchronized static void log2File(final char type, final String tag, final String content) {
        if (content == null) {
            return;
        }
        Date now = new Date();
        String date = new SimpleDateFormat("MM-dd", Locale.getDefault()).format(now);
        final String fullPath = dir + date + ".txt";
        if (!FileUtils.createOrExistsFile(fullPath)) {
            return;
        }
        String time = new SimpleDateFormat("MM-dd HH:mm:ss.SSS", Locale.getDefault()).format(now);
        final String dateLogContent = time + ":" + type + ":" + tag + ":" + content + '\n';
        new Thread(new Runnable() {
            @Override
            public void run() {
                BufferedWriter bw = null;
                try {
                    bw = new BufferedWriter(new FileWriter(fullPath, true));
                    bw.write(dateLogContent);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    CloseUtils.closeIO(bw);
                }
            }
        }).start();
    }
}
