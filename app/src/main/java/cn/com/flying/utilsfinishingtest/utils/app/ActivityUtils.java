package cn.com.flying.utilsfinishingtest.utils.app;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;

import java.util.List;

/**
 * isActivityExists               : 判断 Activity 是否存在
 * startActivity                  : 启动 Activity
 * startActivityForResult         : 启动 Activity 为返回结果
 * startActivities                : 启动多个 Activity
 * startHomeActivity              : 回到桌面
 * getActivityList                : 获取 Activity 栈链表
 * getLauncherActivity            : 获取启动项 Activity
 * getTopActivity                 : 获取栈顶 Activity
 * isActivityExistsInStack        : 判断 Activity 是否存在栈中
 * finishActivity                 : 结束 Activity
 * finishToActivity               : 结束到指定 Activity
 * finishOtherActivities          : 结束所有其他类型的 Activity
 * finishAllActivities            : 结束所有 Activity
 * finishAllActivitiesExceptNewest: 结束除最新之外的所有 Activity
 */
public class ActivityUtils {

    private ActivityUtils() {
        throw new UnsupportedOperationException("Can't instantiate...");
    }

    /**
     * 判断是否存在Activity
     *
     * @param context     上下文
     * @param packageName 包名
     * @param className   activity全路径类名
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isActivityExists(Context context, String packageName, String className) {
        Intent intent = new Intent();
        intent.setClassName(packageName, className);
        return !(context.getPackageManager().resolveActivity(intent, 0) == null ||
                intent.resolveActivity(context.getPackageManager()) == null ||
                context.getPackageManager().queryIntentActivities(intent, 0).size() == 0);
    }

    /**
     * 打开Activity
     *
     * @param context     上下文
     * @param packageName 包名
     * @param className   全类名
     */
    public static void launchActivity(Context context, String packageName, String className) {
        launchActivity(context, packageName, className, null);
    }

    /**
     * 打开Activity
     *
     * @param context     上下文
     * @param packageName 包名
     * @param className   全类名
     * @param bundle      bundle
     */
    public static void launchActivity(Context context, String packageName, String className, Bundle bundle) {
        context.startActivity(IntentUtils.getComponentIntent(packageName, className, bundle));
    }

    /**
     * 获取launcher activity
     *
     * @param context     上下文
     * @param packageName 包名
     * @return launcher activity
     */
    public static String getLauncherActivity(Context context, String packageName) {
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> infos = pm.queryIntentActivities(intent, 0);
        for (ResolveInfo info : infos) {
            if (info.activityInfo.packageName.equals(packageName)) {
                return info.activityInfo.name;
            }
        }
        return "no " + packageName;
    }
}
