package com.lfk.justwetools.Utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

/**
 * Created by liufengkai on 15/10/19.
 */
public class AlarmUtil {

    private static String CODE = "intent_code";

    /**
     * 开启时钟控制
     *
     * @param context
     * @return
     */
    public static AlarmManager getAlarmManager(Context context) {
        return (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    /**
     * 指定时间后进行更新
     * 注意: Receiver 记得在注册
     *
     * @param context
     */
    public static void sendUpdateBroadcast(Context context, int time, String code) {
        AlarmManager am = getAlarmManager(context);
        // 60秒后将产生广播,触发UpdateReceiver的执行,这个方法才是真正的更新数据的操作主要代码
        Intent i = new Intent(CODE);
        i.putExtra("code", code);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, i, 0);
        am.set(AlarmManager.RTC, System.currentTimeMillis() + time * 1000, pendingIntent);
    }

    /**
     * 取消定时执行
     *
     * @param context
     */
    public static void cancelUpdateBroadcast(Context context) {
        AlarmManager am = getAlarmManager(context);
        Intent i = new Intent(CODE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, i, 0);
        am.cancel(pendingIntent);
    }
}
