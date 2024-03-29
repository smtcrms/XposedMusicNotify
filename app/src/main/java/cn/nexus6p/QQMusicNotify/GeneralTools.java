package cn.nexus6p.QQMusicNotify;

import android.app.AndroidAppHelper;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import de.robv.android.xposed.XposedBridge;

final public class GeneralTools {

    public static Context getContext () {
        return AndroidAppHelper.currentApplication().getApplicationContext();
    }

    public static Context getMoudleContext (Context context) {
        Context moudleContext = null;
        try {
            moudleContext = context.createPackageContext(BuildConfig.APPLICATION_ID, Context.CONTEXT_INCLUDE_CODE | Context.CONTEXT_IGNORE_SECURITY);
        } catch (PackageManager.NameNotFoundException e) {
            XposedBridge.log(e);
        }
        return moudleContext;
    }

    public static Context getMoudleContext () {
        return getMoudleContext(getContext());
    }

    public static Notification  buildMusicNotificationWithoutAction (Context context, int iconID, CharSequence titleString, CharSequence textString, boolean statue, RemoteViews remoteViews, PendingIntent contentIntent, String channelID, PendingIntent deleteIntent) {
        if (Build.VERSION.SDK_INT >= 26 && channelID!=null) {
            Notification.Builder builder = new Notification.Builder(context,channelID)
                    .setSmallIcon(iconID)
                    .setContentTitle(titleString)
                    .setContentText(textString)
                    .setCategory(NotificationCompat.CATEGORY_STATUS)
                    .setVisibility(Notification.VISIBILITY_PUBLIC)
                    .setOngoing(statue)
                    .setCustomContentView(remoteViews)
                    .setCustomBigContentView(remoteViews)
                    .setContentIntent(contentIntent)
                    .setDeleteIntent(deleteIntent);
            return builder.build();
        }
        NotificationCompat.Builder builder= new NotificationCompat.Builder(context)
                .setSmallIcon(iconID)
                .setContentTitle(titleString)
                .setContentText(textString)
                .setCategory(NotificationCompat.CATEGORY_STATUS)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setOngoing(statue)
                .setPriority(Notification.PRIORITY_MAX)
                .setContent(remoteViews)
                .setCustomBigContentView(remoteViews)
                .setCustomContentView(remoteViews)
                .setContentIntent(contentIntent)
                .setDeleteIntent(deleteIntent);
        return builder.build();
    }

    public static Notification buildMusicNotificationWithoutAction (Context context, int iconID, CharSequence titleString, CharSequence textString, boolean statue, RemoteViews remoteViews, PendingIntent contentIntent) {
        return GeneralTools.buildMusicNotificationWithoutAction(context,iconID,titleString,textString,statue,remoteViews,contentIntent,null,null);
    }

    public static JSONArray getSupportPackages (Context context) {
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(getAssetsString("packages.json",context));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    static boolean isStringInJSONArray (String string,JSONArray jsonArray) {
        for (int i=0;i<jsonArray.length();i++) {
            try {
                if (jsonArray.getJSONObject(i).getString("app").equals(string)) return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } return false;
    }

    private static String getAssetsString(String fileName, Context context) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    context.getAssets().open(fileName), StandardCharsets.UTF_8) );
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

}
