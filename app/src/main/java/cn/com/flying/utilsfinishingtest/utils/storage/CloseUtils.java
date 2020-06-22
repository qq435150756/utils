package cn.com.flying.utilsfinishingtest.utils.storage;

import java.io.Closeable;
import java.io.IOException;
import java.net.Socket;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/10/9
 *     desc  : 关闭相关工具类
 * </pre>
 */
public class CloseUtils {

    private CloseUtils() {
        throw new UnsupportedOperationException("Can't instantiate...");
    }

    /**
     * 关闭IO
     *
     * @param closeables closeable
     */
    public static void closeIO(Closeable... closeables) {
        if (closeables == null) return;
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 安静关闭IO
     *
     * @param closeables closeable
     */
    public static void closeIOQuietly(Closeable... closeables) {
        if (closeables == null) return;
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException ignored) {
                }
            }
        }
    }
}
